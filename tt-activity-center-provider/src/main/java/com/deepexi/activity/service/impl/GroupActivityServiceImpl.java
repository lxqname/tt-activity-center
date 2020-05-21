package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.*;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.enums.ActivityStatusEnum;
import com.deepexi.activity.enums.ActivityTypeEnum;
import com.deepexi.activity.enums.ConnectTypeEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityMapper;
import com.deepexi.activity.service.ActivityFloorService;
import com.deepexi.activity.service.ActivityService;
import com.deepexi.activity.service.GroupActivityService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.util.DateUtils;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Service(version = "${demo.service.version}")
public class GroupActivityServiceImpl implements GroupActivityService {

    private static final Logger logger = LoggerFactory.getLogger(GroupActivityServiceImpl.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityFloorService activityFloorService;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;


    /**
     * 新增
     *
     * @param createDto 数据
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(GroupActivityCreateDto createDto) {
        // 校验参数
        this.validateInfo(createDto);
        // 保存活动数据
        Activity activity = new Activity();
        activity.setType(ActivityTypeEnum.GROUP_ACTIVITY.getState());
        activity.setName(createDto.getName());
        activity.setStartTime(createDto.getStartTime());
        activity.setEndTime(createDto.getEndTime());
        activity.setTrailTime(BigDecimal.ZERO);
        //初始化活动状态
        activityService.initializeActivityStatus(activity);
        activity.setStatus(createDto.getStatus());
        int activityInsertResult = activityMapper.insert(activity);
        if (activityInsertResult <= 0) {
            logger.error("GroupActivityServiceImpl.create：插入活动数据失败");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.DATABASE_ERROR, "操作失败"));
        }

        activityFloorService.create(activity.getId(), createDto.getList());

        return null;
    }

    /**
     * 更新
     *
     * @param pk        活动主键ID
     * @param createDto 数据
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(String pk, GroupActivityCreateDto createDto) {
        Activity activity = activityMapper.selectById(pk);
        if (null == activity) {
            logger.error("GroupActivityServiceImpl.update：查询活动数据失败，主键ID为[{}]", pk);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "获取活动数据失败，请联系管理员！"));
        }
        if (!Objects.equals(ActivityTypeEnum.GROUP_ACTIVITY.getState(), activity.getType())) {
            logger.error("GroupActivityServiceImpl.update：活动[{}]的活动类型为[{}]，不可进行活动集编辑", pk, activity.getType());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "当前活动类型不为活动集"));
        }
        if (Objects.equals(ActivityStatusEnum.PENDING_START.getState(), activity.getStatus())
                || Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            //原活动状态为待生效或进行中状态，支持变更栏目图片、栏目链接以及栏目次序。
            List<ActivityFloorCreateDto> floorCreateDtoList = createDto.getList();
            // 校验参数合法性
            this.validateFloorInfo(floorCreateDtoList);
            Integer count = activityFloorService.countByActivityId(activity.getId());
            if (!Objects.equals(count, floorCreateDtoList.size())) {
                logger.error("GroupActivityServiceImpl.update：原栏目数为[{}]，入参栏目数为[{}]，活动集编辑不允许变更栏目数", count, floorCreateDtoList.size());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动集编辑不允许变更栏目数"));
            }
            // 删除旧的活动楼层
            activityFloorService.deleteByActivityId(activity.getId());

            // 新增新的活动楼层
            activityFloorService.create(activity.getId(), createDto.getList());
        } else if (Objects.equals(ActivityStatusEnum.DRAFT.getState(), activity.getStatus())
                || Objects.equals(ActivityStatusEnum.REJECT.getState(), activity.getStatus())) {
            //原活动状态为草稿或驳回状态，允许编辑所有项
            this.updateAll(activity, createDto);
        } else {
            // 不允许编辑
            logger.error("GroupActivityServiceImpl.update：活动[{}]的状态为[{}]，不允许编辑", activity.getId(), ActivityStatusEnum.getMsgByState(activity.getStatus()));
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.DATABASE_ERROR, "活动当前状态为" + ActivityStatusEnum.getMsgByState(activity.getStatus()) + "，不允许编辑"));
        }
        return true;
    }

    private void updateAll(Activity activity, GroupActivityCreateDto createDto) {
        // 校验参数
        this.validateInfo(createDto);
        activity.setStatus(createDto.getStatus());
        activity.setName(createDto.getName());
        activity.setStartTime(createDto.getStartTime());
        activity.setEndTime(createDto.getEndTime());
        //初始化活动状态
        activityService.initializeActivityStatus(activity);
        int updateActivityResult = activityMapper.updateById(activity);
        if (updateActivityResult != 1) {
            logger.error("GroupActivityServiceImpl.updateAll：更新活动数据失败");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.DATABASE_ERROR, "操作失败"));
        }

        // 删除旧的活动楼层
        activityFloorService.deleteByActivityId(activity.getId());

        // 新增新的活动楼层
        activityFloorService.create(activity.getId(), createDto.getList());
    }

    /**
     * 批量删除
     *
     * @param activityIdList 活动ID列表
     * @return Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String... activityIdList) {
        List<String> lstId = Arrays.stream(activityIdList).collect(Collectors.toList());
        this.checkDeleteValidity(activityIdList);
        int result = activityMapper.deleteByIds(lstId, accountService.getLoginAccountIdByToken(appRuntimeEnv.getToken()));
        return result > 0;
    }

    /**
     * 检查数据删除的合法性
     *
     * @param activityIdList 主键ID列表
     */
    private void checkDeleteValidity(String... activityIdList) {
        for (String activityId : activityIdList) {
            Activity activity = activityMapper.selectById(activityId);
            if (activity == null) {
                logger.error("GroupActivityServiceImpl.checkDeleteValidity：查询不到活动信息[{}]", activityId);
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "删除失败"));
            }
            if (!Objects.equals(ActivityStatusEnum.DRAFT.getState(), activity.getStatus())
                    && !Objects.equals(ActivityStatusEnum.REJECT.getState(), activity.getStatus())) {
                //只能删除草稿或已驳回活动
                logger.error("GroupActivityServiceImpl.checkDeleteValidity：[{}]活动当前状态[{}]不允许删除", activityId, activity.getStatus());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动当前状态不允许删除"));
            }
        }
    }

    /**
     * 分页查询
     *
     * @param queryDto 查询DTO
     * @param page     页码
     * @param size     页大小
     * @return PageBean
     */
    @Override
    public PageBean<GroupActivityListDto> findPage(ActivityQueryDto queryDto, Integer page, Integer size) {
        //处理前端日期传参
        queryDto.setCreateTimeLeftRange(DateUtils.getDateFromString(queryDto.getCreateTimeLeftRangeStr()));
        queryDto.setCreateTimeRightRange(DateUtils.getDateFromString(queryDto.getCreateTimeRightRangeStr()));
        queryDto.setType(ActivityTypeEnum.GROUP_ACTIVITY.getState());

        //查询活动
        PageBean<ActivityListDto> activityPageBean = activityService.findPage(queryDto, page, size);
        List<ActivityListDto> activityList = activityPageBean.getContent();
        //处理
        List resultList = new ArrayList();
        PageBean resultPage = new PageBean();
        for (ActivityListDto activityListDto : activityList) {
            GroupActivityListDto dto = new GroupActivityListDto();
            BeanUtils.copyProperties(activityListDto, dto);
            // 将创建人账号ID转为名称
            String name = accountService.getUsernameById(activityListDto.getCreatedBy());
            dto.setCreatedBy(StringUtil.isEmpty(name) ? "" : name);
            // 获取栏目数目
            dto.setFloorNum(activityFloorService.countByActivityId(activityListDto.getId()));
            resultList.add(dto);
        }
        resultPage.setContent(resultList);

        return resultPage;
    }

    /**
     * 详情
     *
     * @param pk 活动主键ID
     * @return GroupActivityDto
     */
    @Override
    public GroupActivityDto detail(String pk) {
        Activity activity = activityMapper.selectById(pk);
        if (null == activity) {
            logger.error("GroupActivityServiceImpl.detail：查询活动数据失败，主键ID为[{}]", pk);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "获取活动数据失败，请联系管理员！"));
        }
        GroupActivityDto result = new GroupActivityDto();
        BeanUtils.copyProperties(activity, result);
        if (Objects.equals(ActivityTypeEnum.GROUP_ACTIVITY.getState(), activity.getType())) {
            // 获取活动集明细
            List<ActivityFloorDto> activityFloorDtoList = activityFloorService.findActivityFloorDtoList(activity.getId());
            result.setList(activityFloorDtoList);
        }
        return result;
    }

    /**
     * 验证活动集创建数据合法性
     *
     * @param dto 数据
     */
    private void validateInfo(GroupActivityCreateDto dto) {
        if (null == dto) {
            logger.error("GroupActivityServiceImpl.validateInfo：入参为null");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，入参为null"));
        }
        if (StringUtil.isBlank(dto.getName())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动集名称"));
        }
        final int nameLength = 20;
        if (dto.getName().length() > nameLength) {
            logger.error("GroupActivityServiceImpl.validateInfo：活动集名称入参为[{}]", dto.getName());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动集名称最多可输入" + nameLength + "个字符"));
        }
        if (null == dto.getStartTime() || dto.getStartTime().before(new Date())
                || null == dto.getEndTime() || dto.getEndTime().before(dto.getStartTime())) {
            logger.error("GroupActivityServiceImpl.validateInfo：活动开始时间[{}],活动结束时间[{}]", dto.getStartTime(), dto.getEndTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入正确的活动时间"));
        }
        this.validateFloorInfo(dto.getList());
    }

    /**
     * 验证活动集明细数据合法性
     *
     * @param list 数据
     */
    private void validateFloorInfo(List<ActivityFloorCreateDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动集明细"));
        }
        int floorMaxSize = 6;
        if (list.size() > floorMaxSize) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "最多支持" + floorMaxSize + "级栏目设置"));
        }
        for (ActivityFloorCreateDto activityFloorCreateDto : list) {
            if (StringUtil.isBlank(activityFloorCreateDto.getName())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入栏目标题"));
            }
            int floorNameLength = 20;
            if (activityFloorCreateDto.getName().length() > floorNameLength) {
                logger.error("GroupActivityServiceImpl.validateInfo：栏目标题入参为[{}]", activityFloorCreateDto.getName());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "栏目标题最多可输入" + floorNameLength + "个字符"));
            }
            if (StringUtil.isBlank(activityFloorCreateDto.getImgUrl())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入栏目图片"));
            }
            if (Objects.equals(ConnectTypeEnum.ITEM.getState(), activityFloorCreateDto.getType())) {
                if (StringUtils.isBlank(activityFloorCreateDto.getTypeParam())) {
                    throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择关联商品"));
                }
            } else if (Objects.equals(ConnectTypeEnum.ACTIVITY.getState(), activityFloorCreateDto.getType())) {
                if (StringUtils.isBlank(activityFloorCreateDto.getTypeParam())) {
                    throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择关联活动"));
                }
            } else if (Objects.equals(ConnectTypeEnum.DIV_URL.getState(), activityFloorCreateDto.getType())) {
                if (StringUtils.isBlank(activityFloorCreateDto.getTypeParam())) {
                    throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入链接URL"));
                }
            } else if (!Objects.equals(ConnectTypeEnum.NONE.getState(), activityFloorCreateDto.getType())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择关联内容"));
            }
        }
    }
}
