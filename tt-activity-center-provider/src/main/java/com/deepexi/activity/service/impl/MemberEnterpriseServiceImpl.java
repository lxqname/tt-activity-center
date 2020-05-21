package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.MemberCouponRelationListDto;
import com.deepexi.activity.domain.dto.MemberCouponRelationQueryDto;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.service.MemberCouponRelationService;
import com.deepexi.activity.service.MemberEnterpriseService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.equity.domain.dto.EquityOrganizationDto;
import com.deepexi.equity.service.EquityOrganizationService;
import com.deepexi.member.api.MemberMerchantService;
import com.deepexi.member.domain.dto.MemberMerchantDto;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 会员企业服务实现
 *
 * @author 蝈蝈
 * @since 2019年10月29日 15:14
 */
@Service
public class MemberEnterpriseServiceImpl implements MemberEnterpriseService {

    private static final Logger logger = LoggerFactory.getLogger(MemberEnterpriseServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private MemberMerchantService memberMerchantService;

    @Reference(version = "${demo.service.version}", check = false)
    private EquityOrganizationService equityOrganizationService;

    @Override
    public void create(String memberId, String couponId, String activityId) {
        try {
            MemberMerchantDto dto = new MemberMerchantDto();
            dto.setMemberId(memberId);
            dto.setCouponId(couponId);
            dto.setActivityId(activityId);
            memberMerchantService.create(dto);
        } catch (Exception e) {
            logger.error("MemberEnterpriseServiceImpl.createByMemberCouponRelationListDto：记录会员企业关系失败：", e);
        }
    }

    @Override
    public void createByActivity(String memberId, String activityId) {
        try {
            // 查询会员优惠券关系
            MemberCouponRelationQueryDto memberCouponRelationQueryDto = new MemberCouponRelationQueryDto();
            memberCouponRelationQueryDto.setMemberId(memberId);
            memberCouponRelationQueryDto.setActivityId(activityId);
            this.createByMemberCouponRelationListDto(memberCouponRelationService.findAllWithOnlyEo(memberCouponRelationQueryDto));
        } catch (Exception e) {
            logger.error("MemberEnterpriseServiceImpl.createByActivity：记录会员企业关系失败：", e);
        }
    }

    @Override
    public void createByMemberCouponRelationId(List<String> memberCouponRelationIdList) {
        try {
            // 查询会员优惠券关系
            MemberCouponRelationQueryDto memberCouponRelationQueryDto = new MemberCouponRelationQueryDto();
            memberCouponRelationQueryDto.setIdList(memberCouponRelationIdList);
            this.createByMemberCouponRelationListDto(memberCouponRelationService.findAllWithOnlyEo(memberCouponRelationQueryDto));
        } catch (Exception e) {
            logger.error("MemberEnterpriseServiceImpl.createByMemberCouponRelationId：记录会员企业关系失败：", e);
        }
    }

    @Override
    public void createByUse(String memberId, String couponId, String activityId, String productId, String storeId) {
        try {
            // 查询优惠券关联的企业
            EquityOrganizationDto equityOrganizationDto = equityOrganizationService.queryByCouponId(couponId);
            MemberMerchantDto dto = new MemberMerchantDto();
            dto.setMemberId(memberId);
            dto.setCouponId(couponId);
            dto.setActivityId(activityId);
            dto.setProductId(productId);
            dto.setMerchantId(storeId);
            dto.setEnterprId(equityOrganizationDto.getEnterpriseId());
            dto.setDepartmentOrganizationId(equityOrganizationDto.getDepartmentId());
            memberMerchantService.create(dto);
        } catch (Exception e) {
            logger.error("MemberEnterpriseServiceImpl.createByMemberCouponRelationId：记录会员企业关系失败：", e);
        }
    }

    /**
     * 根据会员优惠券关系记录会员与企业关系
     *
     * @param memberCouponRelationList 会员优惠券关系列表
     */
    private void createByMemberCouponRelationListDto(List<MemberCouponRelationListDto> memberCouponRelationList) {
        if (CollectionUtil.isEmpty(memberCouponRelationList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "数据异常，会员优惠券数据为null"));
        }
        Set<MemberMerchantDto> saveDataSet = Sets.newHashSet();
        for (MemberCouponRelationListDto memberCouponRelationDto : memberCouponRelationList) {
            MemberMerchantDto dto = new MemberMerchantDto();
            dto.setMemberId(memberCouponRelationDto.getMemberId());
            dto.setCouponId(memberCouponRelationDto.getCouponId());
            dto.setActivityId(memberCouponRelationDto.getActivityId());
            saveDataSet.add(dto);
        }
        for (MemberMerchantDto dto : saveDataSet) {
            memberMerchantService.create(dto);
        }
    }
}
