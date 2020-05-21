package com.deepexi.activity.domain.dto;

import javax.ws.rs.QueryParam;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会员优惠券关系查询DTO
 *
 * @author 蝈蝈
 * @since 2019年10月15日 15:47
 */
public class MemberCouponRelationQueryDto implements Serializable {

    /**
     * 租户
     */
    private String tenantCode;

    /**
     * 会员优惠券关系ID列表
     */
    private List<String> idList;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员ID列表
     */
    private List<String> memberIdList;

    /**
     * 会员手机号
     */
    @QueryParam("memberMobile")
    private String memberMobile;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 优惠券ID列表
     */
    private List<String> couponIdList;

    /**
     * 优惠券名称
     */
    @QueryParam("couponName")
    private String couponName;

    /**
     * 优惠券组合包ID
     */
    private String couponPackageId;

    /**
     * 活动优惠券关系ID
     */
    private String activityCouponRelationId;

    /**
     * 活动ID
     */
    @QueryParam("activityId")
    private String activityId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单奖品ID
     */
    private String orderAwardId;

    /**
     * 核销状态
     */
    @QueryParam("verificationStatus")
    private Integer verificationStatus;

    /**
     * 核销状态列表（逗号分隔）
     */
    @QueryParam("verificationStatusList")
    private String verificationStatusListStr;

    /**
     * 核销状态列表-IN查询
     */
    private List<Integer> verificationStatusList;

    /**
     * 核销时间-范围查询-左
     */
    private Date verificationTimeLeftRange;

    /**
     * 核销时间-范围查询-右
     */
    private Date verificationTimeRightRange;

    /**
     * 核销人员账号ID
     */
    private String verificationPersonAccountId;

    /**
     * 查询活动优惠券关系 0-不查询、1-查询
     */
    @QueryParam("queryActivityCouponRelation")
    private Integer queryActivityCouponRelation;

    /**
     * 排序类型（1-快过期>领取时间降序、2-核销时间降序、3-失效时间降序 为null则按照创建时间降序排序）
     */
    @QueryParam("sortType")
    private Integer sortType;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public List<String> getCouponIdList() {
        return couponIdList;
    }

    public void setCouponIdList(List<String> couponIdList) {
        this.couponIdList = couponIdList;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponPackageId() {
        return couponPackageId;
    }

    public void setCouponPackageId(String couponPackageId) {
        this.couponPackageId = couponPackageId;
    }

    public String getActivityCouponRelationId() {
        return activityCouponRelationId;
    }

    public void setActivityCouponRelationId(String activityCouponRelationId) {
        this.activityCouponRelationId = activityCouponRelationId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAwardId() {
        return orderAwardId;
    }

    public void setOrderAwardId(String orderAwardId) {
        this.orderAwardId = orderAwardId;
    }

    public Integer getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Integer verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getVerificationStatusListStr() {
        return verificationStatusListStr;
    }

    public void setVerificationStatusListStr(String verificationStatusListStr) {
        this.verificationStatusListStr = verificationStatusListStr;
    }

    public List<Integer> getVerificationStatusList() {
        return verificationStatusList;
    }

    public void setVerificationStatusList(List<Integer> verificationStatusList) {
        this.verificationStatusList = verificationStatusList;
    }

    public Date getVerificationTimeLeftRange() {
        return verificationTimeLeftRange;
    }

    public void setVerificationTimeLeftRange(Date verificationTimeLeftRange) {
        this.verificationTimeLeftRange = verificationTimeLeftRange;
    }

    public Date getVerificationTimeRightRange() {
        return verificationTimeRightRange;
    }

    public void setVerificationTimeRightRange(Date verificationTimeRightRange) {
        this.verificationTimeRightRange = verificationTimeRightRange;
    }

    public String getVerificationPersonAccountId() {
        return verificationPersonAccountId;
    }

    public void setVerificationPersonAccountId(String verificationPersonAccountId) {
        this.verificationPersonAccountId = verificationPersonAccountId;
    }

    public Integer getQueryActivityCouponRelation() {
        return queryActivityCouponRelation;
    }

    public void setQueryActivityCouponRelation(Integer queryActivityCouponRelation) {
        this.queryActivityCouponRelation = queryActivityCouponRelation;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
