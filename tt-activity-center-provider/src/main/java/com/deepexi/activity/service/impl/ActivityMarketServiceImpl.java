package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityAwardDto;
import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.activity.domain.dto.ActivityMarketDto;
import com.deepexi.activity.domain.dto.CouponAndPackageDto;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.domain.eo.ActivityTarget;
import com.deepexi.activity.enums.ActivityTypeEnum;
import com.deepexi.activity.enums.ChannelPromotionTypeEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityCouponRelationMapper;
import com.deepexi.activity.mapper.ActivityMapper;
import com.deepexi.activity.mapper.ActivityTargetMapper;
import com.deepexi.activity.service.ActivityMarketService;
import com.deepexi.activity.service.ActivityService;
import com.deepexi.business.domain.vo.LoginBusinessInfoVo;
import com.deepexi.business.service.BusinessAccountService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.equity.domain.dto.CouponDetailDto;
import com.deepexi.equity.domain.eo.Coupon;
import com.deepexi.equity.service.CouponPackageService;
import com.deepexi.equity.service.CouponService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.ListUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 推广市场service
 *
 * @author pyf
 */
@Component
@Service(version = "${demo.service.version}")
public class ActivityMarketServiceImpl implements ActivityMarketService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityMarketServiceImpl.class);

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityCouponRelationMapper activityCouponRelationMapper;

    @Autowired
    private ActivityTargetMapper activityTargetMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Autowired
    private ActivityService activityService;

    @Reference(version = "${demo.service.version}", check = false)
    BusinessAccountService businessAccountService;

    @Reference(version = "${demo.service.version}", check = false)
    CouponService couponService;

    @Reference(version = "${demo.service.version}", check = false)
    CouponPackageService couponPackageService;


    @Override
    public PageBean findPage(ActivityDetailDto dto, Integer page, Integer size) {
        //获取渠道id
        String channelId = businessAccountService.getLoginChannelId();
        //查询活动
        PageHelper.startPage(page, size);
        List<Activity> lstActivity = activityMapper.selectActivityMarketList(channelId);
        if (lstActivity.size() <= 0) {
            logger.error("ActivityMarketServiceImpl.findPage:推广市场查询活动为空,channelId={}", channelId);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询活动为空"));
        }
        List<ActivityMarketDto> lstResult = new ArrayList(lstActivity.size());
        for (Activity activity : lstActivity) {
            ActivityMarketDto marketDto = new ActivityMarketDto();
            BeanUtils.copyProperties(activity, marketDto);
            List<ActivityAwardDto> awardDtoList = activityCouponRelationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activity.getId());
            for (ActivityAwardDto awardDto : awardDtoList) {
                marketDto.setCouponType(awardDto.getType());
                com.deepexi.equity.domain.dto.CouponAndPackageDto couponAndPackageDto = couponPackageService.queryCouponAndPackage(awardDto.getType(), awardDto.getTypeId());
                CouponAndPackageDto couponAndPackageDtoThis = new CouponAndPackageDto();
                BeanUtils.copyProperties(couponAndPackageDto, couponAndPackageDtoThis);
                awardDto.setTypeDetail(couponAndPackageDtoThis);
            }
            List<ActivityTarget> activityTargets = activityTargetMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activity.getId(), 1);
            if (activityTargets.size() >= 0) {
                marketDto.setActivityTargetType(activityTargets.get(0).getType());
            }
            marketDto.setActivityTargetList(activityTargets);
            marketDto.setRelatedCouponList(awardDtoList);
            lstResult.add(marketDto);
        }
        PageBean pageBean = new PageBean(lstActivity);
        pageBean.setContent(lstResult);
        return pageBean;
    }

    @Override
    public Object detail(String pk) {
        return activityService.detail(pk);
    }
}
