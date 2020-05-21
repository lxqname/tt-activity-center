package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.constant.EventConstant;
import com.deepexi.activity.domain.dto.ActivityMemberNumberCountDto;
import com.deepexi.activity.domain.dto.MemberActivityDto;
import com.deepexi.activity.domain.dto.MemberActivityQueryDto;
import com.deepexi.activity.domain.dto.MemberCouponRelationQueryDto;
import com.deepexi.activity.enums.ActivityTypeEnum;
import com.deepexi.activity.enums.UseStatusEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.service.*;
import com.deepexi.trade.domain.dto.TcOrderMainDto;
import com.deepexi.trade.domain.vo.TcOrderMainVO;
import com.deepexi.trade.service.TcOrderMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lxq
 */
@Service
public class MemberNumberCountServiceImpl implements MemberNumberCountService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;


    @Resource
    private MemberActivityService memberActivityService;


    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Resource
    private MemberService memberService;


    @Reference(version = "${demo.service.version}", check = false)
    private TcOrderMainService tcOrderMainService;

    @Override
    public ActivityMemberNumberCountDto numberCount() {
        ActivityMemberNumberCountDto dto = new ActivityMemberNumberCountDto();
        String memberId = memberService.getLoginMemberId();
        //String memberId = "076186715060498cb9636fde19197de6";
        // 优惠卷未使用状态
        MemberCouponRelationQueryDto memberCouponRelationQueryDto = new MemberCouponRelationQueryDto();
        memberCouponRelationQueryDto.setTenantCode(appRuntimeEnv.getTenantId());
        memberCouponRelationQueryDto.setMemberId(memberId);
        memberCouponRelationQueryDto.setVerificationStatus(UseStatusEnum.PENDING_USE.getState());
        //获取未使用的优惠卷数量
        int unUsedCouponNumber = memberCouponRelationService.count(memberCouponRelationQueryDto);
        dto.setUnUsedCouponNumber(unUsedCouponNumber);

        memberCouponRelationQueryDto.setVerificationStatus(UseStatusEnum.PENDING_RECEIVE.getState());
        //获取待领取的礼品数量
        Integer unReceiveGiftNumber = memberCouponRelationService.count(memberCouponRelationQueryDto);
        dto.setUnReceiveGiftNumber(unReceiveGiftNumber);

        //查询我助力过的活动
        MemberActivityDto boostDetail = memberActivityService.boostDetail(memberId);
        //查询我参与的会员活动列表
        MemberActivityQueryDto queryDto = new MemberActivityQueryDto();
        if (boostDetail != null) {
            //将我助力过的活动也加入统计
            queryDto.setBoostMemberActivityId(boostDetail.getId());
        }
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setStatusList(new ArrayList<Integer>() {{
            this.add(2);
        }});
        queryDto.setMemberId(memberId);
        queryDto.setActivityType(ActivityTypeEnum.BOOST_ACTIVITY.getState());
        //获取正在进行的活动数量
        int myActivityNumber = memberActivityService.countByMemberActivityQueryDto(queryDto);
        dto.setMyActivityNumber(myActivityNumber);
        TcOrderMainDto tcOrderMainDto = new TcOrderMainDto();
        tcOrderMainDto.setMemberId(memberId);
        tcOrderMainDto.setStatus(EventConstant.PAY_PENDING);
        //获取未付款的订单数量
        List<TcOrderMainVO> tcOrderMainVOList = tcOrderMainService.findAll(tcOrderMainDto);
        dto.setUnPayOrder(tcOrderMainVOList.size());
        return dto;
    }
}
