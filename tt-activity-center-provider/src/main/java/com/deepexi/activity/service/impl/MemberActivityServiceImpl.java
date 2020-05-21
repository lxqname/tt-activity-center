package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityAwardDto;
import com.deepexi.activity.domain.dto.BoostRecordDto;
import com.deepexi.activity.domain.dto.MemberActivityDto;
import com.deepexi.activity.domain.dto.MemberActivityQueryDto;
import com.deepexi.activity.domain.dto.MemberActivityStatusCountDto;
import com.deepexi.activity.domain.dto.MemberAwardCountDto;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.domain.eo.MemberActivity;
import com.deepexi.activity.enums.ActivityTypeEnum;
import com.deepexi.activity.enums.MemberActivityStatusEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.MemberActivityMapper;
import com.deepexi.activity.service.ActivityCouponRelationService;
import com.deepexi.activity.service.ActivityService;
import com.deepexi.activity.service.BoostRecordService;
import com.deepexi.activity.service.MemberActivityService;
import com.deepexi.activity.service.MemberService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.activity.utils.PageBeanUtils;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.util.DateUtils;
import com.deepexi.member.domain.eo.Member;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 会员活动服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class MemberActivityServiceImpl implements MemberActivityService {

    private static final Logger logger = LoggerFactory.getLogger(MemberActivityServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private MemberActivityMapper memberActivityMapper;

    @Resource
    private BoostRecordService boostRecordService;

    @Resource
    private ActivityCouponRelationService activityCouponRelationService;

    @Resource
    private ActivityService activityService;

    @Resource
    private MemberService memberService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    /**
     * 数据入库
     *
     * @param memberActivity 数据
     */
    private void insert(MemberActivity memberActivity) {
        if (memberActivity == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员活动保存数据为null!"));
        }
        int insertResult = memberActivityMapper.insert(memberActivity);
        if (insertResult != 1) {
            logger.error("MemberActivityServiceImpl.insert：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    @Override
    public String create(String memberId, Activity activity, String shareChannelId) {
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        if (activity == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动数据为null"));
        }
        MemberActivity memberActivity = new MemberActivity();
        memberActivity.setActivityId(activity.getId());
        memberActivity.setActivityType(activity.getType());
        memberActivity.setActivityValidTime(activity.getValidTime());
        memberActivity.setMemberId(memberId);
        memberActivity.setBoostNum(0);
        if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activity.getType())) {
            // 领券活动
            memberActivity.setStatus(MemberActivityStatusEnum.SUCCESS.getState());
            memberActivity.setFinishTime(new Date());
        } else {
            memberActivity.setStatus(MemberActivityStatusEnum.PROCESSING.getState());
        }
        memberActivity.setShareChannelId(shareChannelId);
        this.insert(memberActivity);
        return memberActivity.getId();
    }

    @Override
    public String create(String memberId, String activityId, Integer activityType, boolean finish) {
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        MemberActivity memberActivity = new MemberActivity();
        memberActivity.setActivityId(activityId);
        memberActivity.setActivityType(activityType);
        memberActivity.setMemberId(memberId);
        if (finish) {
            memberActivity.setStatus(MemberActivityStatusEnum.SUCCESS.getState());
            memberActivity.setFinishTime(new Date());
        } else {
            memberActivity.setStatus(MemberActivityStatusEnum.PROCESSING.getState());
        }
        this.insert(memberActivity);
        return memberActivity.getId();
    }

    @Override
    public MemberActivity selectById(String id, boolean throwException) {
        MemberActivity memberActivity = memberActivityMapper.selectById(id);
        if (memberActivity == null && throwException) {
            logger.error("MemberActivityServiceImpl.selectById：查询会员活动数据失败，ID为[{}]", id);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询会员活动数据失败"));
        }
        return memberActivity;
    }

    @Override
    public Integer count(String activityId, String memberId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        MemberActivityQueryDto queryDto = new MemberActivityQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setActivityId(activityId);
        queryDto.setMemberId(memberId);
        return memberActivityMapper.count(queryDto);
    }

    @Override
    public MemberActivity checkMemberActivityEffectiveness(String id) {
        MemberActivity memberActivity = this.selectById(id, true);
        if (Objects.equals(MemberActivityStatusEnum.SUCCESS.getState(), memberActivity.getStatus())) {
            // 会员活动已成功
            logger.error("MemberActivityServiceImpl.checkMemberActivityEffectiveness：会员活动[{}]当前状态为[{}]",
                    memberActivity.getId(), MemberActivityStatusEnum.SUCCESS.getMsg());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "会员活动已成功"));
        } else if (Objects.equals(MemberActivityStatusEnum.FAIL.getState(), memberActivity.getStatus())) {
            // 会员活动已失败
            logger.error("MemberActivityServiceImpl.checkMemberActivityEffectiveness：会员活动[{}]当前状态为[{}]",
                    memberActivity.getId(), MemberActivityStatusEnum.FAIL.getMsg());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "会员活动已失败"));
        }
        Date now = new Date();
        if (now.after(DateUtils.addHour(memberActivity.getCreatedAt(), memberActivity.getActivityValidTime()))) {
            // 不在有效期内
            logger.error("MemberActivityServiceImpl.checkMemberActivityEffectiveness：会员活动[{}]开始时间[{}]距今[{}]已超过有效时间[{}]",
                    memberActivity.getId(), memberActivity.getCreatedAt(), now, memberActivity.getActivityValidTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "会员活动已结束"));
        }
        return memberActivity;
    }

    @Override
    public Integer addBoostNum(String id, Integer num) {
        if (StringUtils.isBlank(id)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，主键ID为null"));
        }
        if (num == null || num <= 0) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "数量参数异常"));
        }
        int updateResult = memberActivityMapper.addBoostNum(id, num, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (updateResult != 1) {
            logger.error("MemberActivityServiceImpl.addBoostNum：增加助力人数操作失败，addBoostNum方法返回值为[{}]，主键ID为[{}]，数量为[{}]",
                    updateResult, id, num);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        MemberActivity memberActivity = this.selectById(id, true);
        return memberActivity.getBoostNum();
    }

    @Override
    public Integer subBoostNum(String id, Integer num) {
        if (StringUtils.isBlank(id)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，主键ID为null"));
        }
        if (num == null || num <= 0) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "数量参数异常"));
        }
        int updateResult = memberActivityMapper.subBoostNum(id, num, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (updateResult != 1) {
            logger.error("MemberActivityServiceImpl.subBoostNum：减少助力人数操作失败，subBoostNum方法返回值为[{}]，主键ID为[{}]，数量为[{}]",
                    updateResult, id, num);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        MemberActivity memberActivity = this.selectById(id, true);
        return memberActivity.getBoostNum();
    }

    @Override
    public MemberActivityDto detail(String id) {
        MemberActivity participantActivity = this.selectById(id, true);
        MemberActivityDto response = new MemberActivityDto();
        response.copy(participantActivity);
        return response;
    }

    @Override
    public MemberActivityDto participateDetail(String activityId, String memberId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        MemberActivityQueryDto queryDto = new MemberActivityQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setActivityId(activityId);
        queryDto.setMemberId(memberId);
        List<MemberActivityDto> list = memberActivityMapper.findList(queryDto);
        if (CollectionUtil.isEmpty(list)) {
            logger.info("MemberActivityServiceImpl.participateDetail：查询不到参与信息，会员ID[{}]，活动ID[{}]", memberId, activityId);
            return null;
        }
        if (list.size() > 1) {
            logger.error("MemberActivityServiceImpl.participateDetail：查询到[{}]条参与信息，会员ID[{}]，活动ID[{}]", list.size(), memberId, activityId);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        MemberActivityDto response = list.get(0);
        response.setCurrentTime(new Date());
        return response;
    }

    @Override
    public MemberActivityDto boostDetail(String memberId) {
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        // 查询助力记录
        BoostRecordDto boostRecordDto = boostRecordService.queryByBoostMemberId(memberId);
        if (boostRecordDto == null) {
            return null;
        }
        // 发起者会员活动ID
        String participantActivityId = boostRecordDto.getMemberActivityId();
        MemberActivity participantActivity = this.selectById(participantActivityId, true);
        MemberActivityDto response = new MemberActivityDto();
        response.copy(participantActivity);
        return response;
    }

    @Override
    public List<String> end() {
        // 查询过期会员活动列表
        List<MemberActivity> expiredList = memberActivityMapper.selectOvertimeList();
        if (CollectionUtil.isEmpty(expiredList)) {
            // 没有需要结束的会员活动
            logger.info("MemberActivityServiceImpl.end：查询不到已超过有效期的会员活动");
            return Lists.newArrayList();
        }
        List<String> memberActivityIdList = new ArrayList<>(expiredList.size());
        for (MemberActivity memberActivity : expiredList) {
            appRuntimeEnv.setTenantId(memberActivity.getTenantCode());
            // 结束,各个会员活动结束互不影响
            try {
                if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), memberActivity.getActivityType())) {
                    Boolean result = this.end(memberActivity);
                    if (result) {
                        memberActivityIdList.add(memberActivity.getId());
                    }
                }
            } catch (Exception e) {
                logger.error("MemberActivityServiceImpl.end：会员活动结束异常，错误信息：", e);
            }
        }
        return memberActivityIdList;
    }

    @Override
    public void endByActivityId(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        // 查询特定活动的进行中会员活动
        MemberActivityQueryDto queryDto = new MemberActivityQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setStatus(MemberActivityStatusEnum.PROCESSING.getState());
        queryDto.setActivityId(activityId);
        List<MemberActivity> list = memberActivityMapper.findEoList(queryDto);
        if (CollectionUtil.isEmpty(list)) {
            logger.info("MemberActivityServiceImpl.endByActivityId：没有进行中的会员活动需要停止，活动ID为[{}]", activityId);
            return;
        }
        for (MemberActivity memberActivity : list) {
            if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), memberActivity.getActivityType())) {
                this.end(memberActivity);
            }
        }
    }

    /**
     * 结束会员活动
     *
     * @param memberActivity 会员活动数据
     * @return Boolean
     */
    private Boolean end(MemberActivity memberActivity) {
        if (memberActivity == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常"));
        }
        // 查询活动奖品列表
        List<ActivityAwardDto> awardList = activityCouponRelationService.getAwardList(memberActivity.getActivityId());
        if (CollectionUtil.isEmpty(awardList)) {
            logger.error("MemberActivityServiceImpl.end：查询活动奖品数据为null，活动ID为[{}]", memberActivity.getActivityId());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询活动奖品数据失败"));
        }
        ActivityAwardDto firstAwardDto = awardList.get(0);
        int updateMemberActivityResult = 0;
        if (memberActivity.getBoostNum() >= firstAwardDto.getCompleteNum()) {
            // 已达第一阶梯奖励，成功
            updateMemberActivityResult = memberActivityMapper.endToSuccess(memberActivity.getId(), BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        } else {
            // 没有达到阶梯奖励，失败
            updateMemberActivityResult = memberActivityMapper.endToFail(memberActivity.getId(), BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        }
        if (updateMemberActivityResult != 1) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "更新会员活动状态失败"));
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean manualEnd(String id) {
        // 查询会员活动
        MemberActivity memberActivity = this.selectById(id, true);
        if (new Date().before(DateUtils.addHour(memberActivity.getCreatedAt(), memberActivity.getActivityValidTime()))) {
            // 会员活动还在有效期内，不允许手动结束
            logger.info("MemberActivityServiceImpl.manualEnd：会员活动[{}]开始时间[{}]距今未超过有效时间[{}]，不允许手动结束",
                    memberActivity.getId(), memberActivity.getCreatedAt(), memberActivity.getActivityValidTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "会员活动还在进行中，不允许手动结束"));
        }
        if (!Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), memberActivity.getActivityType())) {
            logger.error("MemberActivityServiceImpl.manualEnd：会员活动类型不是助力活动,会员活动ID为[{}]，类型为[{}]",
                    memberActivity.getId(), memberActivity.getActivityType());
            return Boolean.FALSE;
        }
        return this.end(memberActivity);
    }

    @Override
    public void endToSuccess(String id) {
        int updateResult = memberActivityMapper.endToSuccess(id, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (updateResult != 1) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "更新会员活动状态失败"));
        }
    }

    @Override
    public List<MemberActivityDto> findListWithOnlyEo(MemberActivityQueryDto query) {
        query.setTenantCode(appRuntimeEnv.getTenantId());
        return memberActivityMapper.findList(query);
    }

    /**
     * 分页查询-仅包含MemberActivity数据
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    private PageBean<MemberActivityDto> findPageWithOnlyEo(MemberActivityQueryDto query, Integer page, Integer size) {
        query.setTenantCode(appRuntimeEnv.getTenantId());
        PageHelper.startPage(page, size);
        List<MemberActivityDto> list = memberActivityMapper.findList(query);
        return new PageBean<>(list);
    }

    @Override
    public PageBean<MemberActivityDto> findPage(MemberActivityQueryDto query, Integer page, Integer size) {
        Map<String, Member> memberMap = Maps.newHashMap();
        if (StringUtils.isNotBlank(query.getMemberMobile())) {
            // 会员手机号查询
            memberMap = memberService.findAllByMobile(query.getMemberMobile());
            if (CollectionUtil.isEmpty(memberMap)) {
                return PageBeanUtils.getDefaultPageBean();
            }
            query.setMemberIdList(Lists.newArrayList(memberMap.keySet()));
        }
        PageBean<MemberActivityDto> pageBean = this.findPageWithOnlyEo(query, page, size);
        List<MemberActivityDto> list = pageBean.getContent();
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        for (MemberActivityDto dto : list) {
            Member member = memberMap.get(dto.getMemberId());
            if (member == null) {
                member = memberService.getMemberById(dto.getMemberId());
                memberMap.put(member.getId(), member);
            }
            dto.setMemberMobile(member.getMobile());
        }
        return pageBean;
    }

    @Override
    public PageBean<MemberActivityDto> findPageForH5(List<Integer> statusList, Integer page, Integer size) {
        // 查询会员信息
        String loginMemberId = memberService.getLoginMemberId();
        // 查询助力记录
        BoostRecordDto boostRecordDto = boostRecordService.queryByBoostMemberId(loginMemberId);

        MemberActivityQueryDto query = new MemberActivityQueryDto();
        query.setActivityType(ActivityTypeEnum.BOOST_ACTIVITY.getState());
        query.setMemberId(loginMemberId);
        query.setStatusList(statusList);
        if (boostRecordDto != null) {
            // 将我助力过的活动也加入分页
            query.setBoostMemberActivityId(boostRecordDto.getMemberActivityId());
        }
        PageBean<MemberActivityDto> pageBean = this.findPageWithOnlyEo(query, page, size);
        List<MemberActivityDto> list = pageBean.getContent();
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        list.parallelStream().forEach(dto -> {
            Activity activity = activityService.selectById(dto.getActivityId(), true);
            dto.setActivityName(activity.getName());
            dto.setActivityEndTime(activity.getEndTime());
            dto.setActivityThumbnailUrl(activity.getThumbnailUrl());
            dto.setActivityStatus(activity.getStatus());
            // 查询未完成的最低阶梯奖励
            ActivityAwardDto awardDto = activityCouponRelationService.queryLowestUnCompleteRelation(dto.getActivityId(), dto.getBoostNum());
            if (awardDto != null) {
                dto.setMissBoostNum(awardDto.getCompleteNum() - dto.getBoostNum());
                dto.setAwardId(awardDto.getTypeId());
                dto.setAwardName(activityCouponRelationService.queryTypeName(awardDto.getType(), awardDto.getTypeId()));
            } else {
                dto.setMissBoostNum(0);
            }
            if (Objects.equals(loginMemberId, dto.getMemberId())) {
                dto.setMyself(true);
            }
        });
        return pageBean;
    }

    @Override
    public PageBean<MemberAwardCountDto> countAward(String activityId, String memberMobile, Integer page, Integer size) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        Map<String, Member> memberMap = Maps.newHashMap();
        if (StringUtils.isNotBlank(memberMobile)) {
            // 会员手机号查询
            memberMap = memberService.findAllByMobile(memberMobile);
            if (CollectionUtil.isEmpty(memberMap)) {
                return PageBeanUtils.getDefaultPageBean();
            }
        }
        PageHelper.startPage(page, size);
        List<MemberAwardCountDto> list = memberActivityMapper.countAward(appRuntimeEnv.getTenantId(), activityId, Lists.newArrayList(memberMap.keySet()));
        PageBean<MemberAwardCountDto> pageBean = new PageBean<>(list);
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        for (MemberAwardCountDto dto : list) {
            Member member = memberMap.get(dto.getMemberId());
            if (member == null) {
                member = memberService.getMemberById(dto.getMemberId());
            }
            dto.setMemberMobile(member.getMobile());
            // 已领取 = 待核销 + 已核销 + 已过期
            dto.setReceiveNum(dto.getPendingUseNum() + dto.getUsedNum() + dto.getExpiredNum());
        }
        return pageBean;
    }

    @Override
    public Integer countMember(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        return memberActivityMapper.countMember(appRuntimeEnv.getTenantId(), activityId);
    }

    @Override
    public MemberActivityStatusCountDto countByStatus(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        MemberActivityStatusCountDto response = new MemberActivityStatusCountDto();
        MemberActivityQueryDto queryDto = new MemberActivityQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setActivityId(activityId);
        queryDto.setStatus(MemberActivityStatusEnum.SUCCESS.getState());
        response.setSuccessNum(memberActivityMapper.count(queryDto));
        queryDto.setStatus(MemberActivityStatusEnum.PROCESSING.getState());
        response.setProcessingNum(memberActivityMapper.count(queryDto));
        queryDto.setStatus(MemberActivityStatusEnum.FAIL.getState());
        response.setFailNum(memberActivityMapper.count(queryDto));
        return response;
    }

    @Override
    public Integer countByMemberActivityQueryDto(MemberActivityQueryDto query) {
        return memberActivityMapper.countByMemberActivityQueryDto(query);
    }
}