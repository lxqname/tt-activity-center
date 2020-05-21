package com.deepexi.activity.service;

import java.util.List;

/**
 * 会员企业服务
 *
 * @author 蝈蝈
 * @since 2019年10月29日 15:06
 */
public interface MemberEnterpriseService {

    /**
     * 新增
     *
     * @param memberId   会员ID
     * @param couponId   优惠券ID
     * @param activityId 活动ID
     */
    void create(String memberId, String couponId, String activityId);

    /**
     * 根据活动ID记录会员与企业关系
     *
     * @param memberId   会员ID
     * @param activityId 活动ID
     */
    void createByActivity(String memberId, String activityId);

    /**
     * 根据会员优惠券关系ID记录会员与企业关系
     *
     * @param memberCouponRelationIdList 会员优惠券关系ID列表
     */
    void createByMemberCouponRelationId(List<String> memberCouponRelationIdList);

    /**
     * 记录会员与企业关系-核销使用
     *
     * @param memberId   会员ID
     * @param couponId   优惠券ID
     * @param activityId 活动ID-非必须
     * @param productId  商品ID-非必须
     * @param storeId    核销门店ID
     */
    void createByUse(String memberId, String couponId, String activityId, String productId, String storeId);
}
