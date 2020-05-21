package com.deepexi.activity.domain.dto;

import javax.ws.rs.QueryParam;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 活动查询DTO
 *
 * @author 蝈蝈
 */
public class ActivityQueryDto implements Serializable {

    /**
     * 多租户标识
     */
    private String tenantCode;

    /**
     * 创建时间-范围查询-左
     */
    private Date createTimeLeftRange;

    /**
     * 创建时间-范围查询-左-前端传参
     */
    @QueryParam("createTimeLeftRange")
    private String createTimeLeftRangeStr;

    /**
     * 创建时间-范围查询-右
     */
    private Date createTimeRightRange;

    /**
     * 创建时间-范围查询-右-前端传参
     */
    @QueryParam("createTimeRightRange")
    private String createTimeRightRangeStr;

    /**
     * 活动ID列表-IN查询
     */
    private List<String> idList;

    /**
     * 类型（1-领券活动、2-助力活动、3-发券活动、4-新会员活动）
     */
    @QueryParam("type")
    private Integer type;

    /**
     * 类型列表(逗号分隔)
     */
    @QueryParam("typeList")
    private String typeListStr;

    /**
     * 类型列表-IN查询
     */
    private List<Integer> typeList;

    /**
     * 名称-模糊搜索
     */
    @QueryParam("name")
    private String fuzzyName;

    /**
     * 开始时间-范围查询-左
     */
    private Date startTimeLeftRange;

    /**
     * 开始时间-范围查询-左-前端传参
     */
    @QueryParam("startTimeLeftRange")
    private String startTimeLeftRangeStr;

    /**
     * 开始时间-范围查询-右
     */
    private Date startTimeRightRange;

    /**
     * 开始时间-范围查询-右-前端传参
     */
    @QueryParam("startTimeRightRange")
    private String startTimeRightRangeStr;

    /**
     * 活动真实开始时间-范围查询-左
     */
    private Date realStartTimeLeftRange;

    /**
     * 活动真实开始时间-范围查询-左-前端传参
     */
    @QueryParam("realStartTimeLeftRange")
    private String realStartTimeLeftRangeStr;

    /**
     * 活动真实开始时间-范围查询-右
     */
    private Date realStartTimeRightRange;

    /**
     * 活动真实开始时间-范围查询-右-前端传参
     */
    @QueryParam("realStartTimeRightRange")
    private String realStartTimeRightRangeStr;

    /**
     * 结束时间-范围查询-左
     */
    private Date endTimeLeftRange;

    /**
     * 结束时间-范围查询-左-前端传参
     */
    @QueryParam("endTimeLeftRange")
    private String endTimeLeftRangeStr;

    /**
     * 结束时间-范围查询-右
     */
    private Date endTimeRightRange;

    /**
     * 结束时间-范围查询-右-前端传参
     */
    @QueryParam("endTimeRightRange")
    private String endTimeRightRangeStr;

    /**
     * 平台推广（0-不允许推广、1-允许推广）
     */
    private Integer platformPromotion;

    /**
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回）
     */
    @QueryParam("status")
    private Integer status;

    /**
     * 活动状态(逗号分隔)
     */
    @QueryParam("statusList")
    private String statusListStr;

    /**
     * 活动状态-IN查询
     */
    private List<Integer> statusList;

    /**
     * 创建平台(1-运营平台、2-商户平台)
     */
    @QueryParam("applicationType")
    private Integer applicationType;

    /**
     * 审核状态(0-待审核,1-审核通过,2-审核驳回)
     */
    @QueryParam("auditStatus")
    private Integer auditStatus;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Date getCreateTimeLeftRange() {
        return createTimeLeftRange;
    }

    public void setCreateTimeLeftRange(Date createTimeLeftRange) {
        this.createTimeLeftRange = createTimeLeftRange;
    }

    public String getCreateTimeLeftRangeStr() {
        return createTimeLeftRangeStr;
    }

    public void setCreateTimeLeftRangeStr(String createTimeLeftRangeStr) {
        this.createTimeLeftRangeStr = createTimeLeftRangeStr;
    }

    public Date getCreateTimeRightRange() {
        return createTimeRightRange;
    }

    public void setCreateTimeRightRange(Date createTimeRightRange) {
        this.createTimeRightRange = createTimeRightRange;
    }

    public String getCreateTimeRightRangeStr() {
        return createTimeRightRangeStr;
    }

    public void setCreateTimeRightRangeStr(String createTimeRightRangeStr) {
        this.createTimeRightRangeStr = createTimeRightRangeStr;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeListStr() {
        return typeListStr;
    }

    public void setTypeListStr(String typeListStr) {
        this.typeListStr = typeListStr;
    }

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public String getFuzzyName() {
        return fuzzyName;
    }

    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    public Date getStartTimeLeftRange() {
        return startTimeLeftRange;
    }

    public void setStartTimeLeftRange(Date startTimeLeftRange) {
        this.startTimeLeftRange = startTimeLeftRange;
    }

    public String getStartTimeLeftRangeStr() {
        return startTimeLeftRangeStr;
    }

    public void setStartTimeLeftRangeStr(String startTimeLeftRangeStr) {
        this.startTimeLeftRangeStr = startTimeLeftRangeStr;
    }

    public Date getStartTimeRightRange() {
        return startTimeRightRange;
    }

    public void setStartTimeRightRange(Date startTimeRightRange) {
        this.startTimeRightRange = startTimeRightRange;
    }

    public String getStartTimeRightRangeStr() {
        return startTimeRightRangeStr;
    }

    public void setStartTimeRightRangeStr(String startTimeRightRangeStr) {
        this.startTimeRightRangeStr = startTimeRightRangeStr;
    }

    public Date getRealStartTimeLeftRange() {
        return realStartTimeLeftRange;
    }

    public void setRealStartTimeLeftRange(Date realStartTimeLeftRange) {
        this.realStartTimeLeftRange = realStartTimeLeftRange;
    }

    public String getRealStartTimeLeftRangeStr() {
        return realStartTimeLeftRangeStr;
    }

    public void setRealStartTimeLeftRangeStr(String realStartTimeLeftRangeStr) {
        this.realStartTimeLeftRangeStr = realStartTimeLeftRangeStr;
    }

    public Date getRealStartTimeRightRange() {
        return realStartTimeRightRange;
    }

    public void setRealStartTimeRightRange(Date realStartTimeRightRange) {
        this.realStartTimeRightRange = realStartTimeRightRange;
    }

    public String getRealStartTimeRightRangeStr() {
        return realStartTimeRightRangeStr;
    }

    public void setRealStartTimeRightRangeStr(String realStartTimeRightRangeStr) {
        this.realStartTimeRightRangeStr = realStartTimeRightRangeStr;
    }

    public Date getEndTimeLeftRange() {
        return endTimeLeftRange;
    }

    public void setEndTimeLeftRange(Date endTimeLeftRange) {
        this.endTimeLeftRange = endTimeLeftRange;
    }

    public String getEndTimeLeftRangeStr() {
        return endTimeLeftRangeStr;
    }

    public void setEndTimeLeftRangeStr(String endTimeLeftRangeStr) {
        this.endTimeLeftRangeStr = endTimeLeftRangeStr;
    }

    public Date getEndTimeRightRange() {
        return endTimeRightRange;
    }

    public void setEndTimeRightRange(Date endTimeRightRange) {
        this.endTimeRightRange = endTimeRightRange;
    }

    public String getEndTimeRightRangeStr() {
        return endTimeRightRangeStr;
    }

    public void setEndTimeRightRangeStr(String endTimeRightRangeStr) {
        this.endTimeRightRangeStr = endTimeRightRangeStr;
    }

    public Integer getPlatformPromotion() {
        return platformPromotion;
    }

    public void setPlatformPromotion(Integer platformPromotion) {
        this.platformPromotion = platformPromotion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusListStr() {
        return statusListStr;
    }

    public void setStatusListStr(String statusListStr) {
        this.statusListStr = statusListStr;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}

