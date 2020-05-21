package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.activity.domain.dto.ActivityFloorCreateDto;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.enums.ConnectTypeEnum;
import com.deepexi.activity.mapper.ActivityMapper;
import com.deepexi.activity.service.ActivityFloorService;
import com.deepexi.activity.domain.eo.ActivityFloor;
import com.deepexi.activity.domain.dto.ActivityFloorDto;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityFloorMapper;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.product.service.ProductService;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.deepexi.util.BeanPowerHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Component
@Service(version = "${demo.service.version}")
public class ActivityFloorServiceImpl implements ActivityFloorService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityFloorServiceImpl.class);

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Reference(version = "${demo.service.version}", check = false)
    private ProductService productService;

    @Autowired
    private ActivityFloorMapper activityFloorMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public PageBean findPage(ActivityFloorDto eo, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ActivityFloor> list = activityFloorMapper.findList(eo);
        return new PageBean<>(list);
    }

    @Override
    public List<ActivityFloor> findAll(ActivityFloorDto eo) {
        List<ActivityFloor> list = activityFloorMapper.findList(eo);
        return list;
    }

    @Override
    public ActivityFloor detail(String pk) {
        return activityFloorMapper.selectById(pk);
    }

    @Override
    public Boolean create(ActivityFloorDto eo) {
        int result = activityFloorMapper.insert(BeanPowerHelper.mapPartOverrider(eo, ActivityFloor.class));
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(String activityId, List<ActivityFloorCreateDto> list) {
        int sort = 0;
        for (ActivityFloorCreateDto createDto : list) {
            ActivityFloor activityFloor = new ActivityFloor();
            BeanUtils.copyProperties(createDto, activityFloor);
            BaseFieldUtil.fill(activityFloor, accountService, appRuntimeEnv.getTenantId(), appRuntimeEnv.getToken(), new Date());
            activityFloor.setActivityId(activityId);
            activityFloor.setSort(sort++);
            Integer insert = activityFloorMapper.insert(activityFloor);
            if (insert <= 0) {
                logger.error("ActivityFloorServiceImpl.create：插入活动数据失败");
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.DATABASE_ERROR, "操作失败"));
            }
        }
        return true;
    }

    @Override
    public Boolean update(String pk, ActivityFloorDto eo) {
        eo.setId(pk);
        int result = activityFloorMapper.updateById(BeanPowerHelper.mapPartOverrider(eo, ActivityFloor.class));
        return result > 0;
    }

    @Override
    public Boolean delete(String... pk) {
        int result = activityFloorMapper.deleteByIds(pk);
        return result > 0;
    }

    @Override
    public Integer deleteByActivityId(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            logger.info("ActivityFloorServiceImpl.deleteByActivityId：入参活动ID为null");
            return 0;
        }
        return activityFloorMapper.deleteByActivityId(appRuntimeEnv.getTenantId(), activityId, accountService.getLoginAccountIdByToken(appRuntimeEnv.getToken()));
    }

    @Override
    public Integer countByActivityId(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            logger.info("ActivityFloorServiceImpl.countByActivityId：入参活动ID为null");
            return 0;
        }
        return activityFloorMapper.countByActivityId(appRuntimeEnv.getTenantId(), activityId);
    }

    /**
     * 根据活动ID查询详细信息列表
     *
     * @param activityId 活动ID
     * @return List
     */
    @Override
    public List<ActivityFloorDto> findActivityFloorDtoList(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            logger.info("ActivityFloorServiceImpl.findActivityFloorDtoList：入参活动ID为null");
            return Lists.newArrayList();
        }
        List<ActivityFloor> activityFloorList = activityFloorMapper.findByActivityId(appRuntimeEnv.getTenantId(), activityId);
        if (CollectionUtil.isEmpty(activityFloorList)) {
            logger.info("ActivityFloorServiceImpl.findActivityFloorDtoList：根据活动ID查询列表为null，活动ID为[{}],租户为[{}]",
                    activityId, appRuntimeEnv.getTenantId());
            return Lists.newArrayList();
        }
        List<ActivityFloorDto> response = new ArrayList<>(activityFloorList.size());
        for (ActivityFloor activityFloor : activityFloorList) {
            ActivityFloorDto activityFloorDto = new ActivityFloorDto();
            BeanUtils.copyProperties(activityFloor, activityFloorDto);
            if (Objects.equals(ConnectTypeEnum.ITEM.getState(), activityFloor.getType())) {
                // 查询关联商品信息
                List<String> idList = Arrays.asList(activityFloor.getTypeParam().split(","));
                List productDto  = productService.findPopupPage(idList);
                if(CollectionUtil.isEmpty(productDto)){
                    logger.info("ActivityFloorServiceImpl.findActivityFloorDtoList：查询关联商品数据失败，ID为[{}]", activityFloor.getTypeParam());
                }
                activityFloorDto.setProductList(productDto);
            } else if (Objects.equals(ConnectTypeEnum.ACTIVITY.getState(), activityFloor.getType())) {
                // 查询关联活动
                Activity activity = activityMapper.selectById(activityFloor.getTypeParam());
                if (null == activity) {
                    logger.info("ActivityFloorServiceImpl.findActivityFloorDtoList：查询活动数据失败，ID为[{}]", activityFloor.getTypeParam());
                } else {
                    ActivityDetailDto activityDetailDto = new ActivityDetailDto();
                    BeanUtils.copyProperties(activity, activityDetailDto);
                    activityFloorDto.setActivityDetailDto(activityDetailDto);
                }
            }
            response.add(activityFloorDto);
        }
        return response;


    }
}