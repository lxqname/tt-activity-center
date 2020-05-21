package com.deepexi.activity.utils;

import com.deepexi.activity.domain.dto.CouponAndPackageDto;
import com.deepexi.activity.domain.dto.CouponEquityEnterpriseDto;
import com.deepexi.activity.domain.eo.MemberCouponRelation;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.equity.domain.eo.Coupon;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 字段复制工具类
 *
 * @author 蝈蝈
 * @since 2019年10月16日 15:34
 */
public class FieldCopyUtils {

    /**
     * 复制字段
     *
     * @param couponAndPackageDto 优惠券/组合包DTO
     * @return CouponAndPackageDto
     */
    public static CouponAndPackageDto copy(com.deepexi.equity.domain.dto.CouponAndPackageDto couponAndPackageDto) {
        CouponAndPackageDto response = new CouponAndPackageDto();
        BeanUtils.copyProperties(couponAndPackageDto, response);
        if (CollectionUtil.isNotEmpty(couponAndPackageDto.getCouponList())) {
            List<CouponEquityEnterpriseDto> couponList = Lists.newArrayList();
            for (com.deepexi.equity.domain.dto.CouponEquityEnterpriseDto couponEquityEnterpriseDto : couponAndPackageDto.getCouponList()) {
                CouponEquityEnterpriseDto coupon = new CouponEquityEnterpriseDto();
                BeanUtils.copyProperties(couponEquityEnterpriseDto, coupon);
                couponList.add(coupon);
            }
            response.setCouponList(couponList);
        }
        return response;
    }

    /**
     * 复制字段
     *
     * @param coupon               优惠券数据
     * @param memberCouponRelation 会员优惠券关系数据
     */
    public static void copy(Coupon coupon, MemberCouponRelation memberCouponRelation) {
        if (coupon == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，Coupon数据为null"));
        }
        if (memberCouponRelation == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，MemberCouponRelation数据为null"));
        }
        memberCouponRelation.setCouponUseTimeFlag(coupon.getUseTimeFlag());
        memberCouponRelation.setCouponUseStartTime(coupon.getUseStartTime());
        memberCouponRelation.setCouponUseEndTime(coupon.getUseEndTime());
        memberCouponRelation.setCouponValidDay(coupon.getValidDay());
    }

    /**
     * 并集
     *
     * @param list1 列表1
     * @param list2 列表2
     * @return List
     */
    public static List<String> unionList(List<String> list1, List<String> list2) {
        if (CollectionUtil.isEmpty(list1)) {
            list1 = Lists.newArrayList();
        }
        if (CollectionUtil.isNotEmpty(list2)) {
            for (String id : list2) {
                if (!list1.contains(id)) {
                    list1.add(id);
                }
            }
        }
        return list1;
    }
}
