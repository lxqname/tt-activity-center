package com.deepexi.activity.domain.dto;

import javax.ws.rs.QueryParam;
import java.io.Serializable;
import java.util.List;

/**
 * 会员活动查询DTO
 *
 * @author 蝈蝈
 * @since 2019年10月14日 11:31
 */
public class MemberActivityQueryDto implements Serializable {

    /**
     * 租户
     */
    private String tenantCode;

    /**
     * 活动ID
     */
    @QueryParam("activityId")
    private String activityId;

    /**
     * 活动类型（1-领取优惠券活动、2-助力活动）
     */
    private Integer activityType;

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
     * 状态列表（1-成功、2-进行中、3-失败）
     */
    private Integer status;

    /**
     * 状态列表（1-成功、2-进行中、3-失败）
     */
    private List<Integer> statusList;

    /**
     * 助力者助力过的会员活动ID
     */
    private String boostMemberActivityId;

    /**
     * 排序升降（0-降序，1-升序）默认降序
     */
    private Integer sortAsc;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public String getBoostMemberActivityId() {
        return boostMemberActivityId;
    }

    public void setBoostMemberActivityId(String boostMemberActivityId) {
        this.boostMemberActivityId = boostMemberActivityId;
    }

    public Integer getSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(Integer sortAsc) {
        this.sortAsc = sortAsc;
    }
}
