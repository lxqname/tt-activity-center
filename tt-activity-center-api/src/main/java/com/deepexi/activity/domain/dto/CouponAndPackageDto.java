package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 优惠券/组合包DTO
 *
 * @author 蝈蝈
 * @since 2019年09月25日 17:16
 */
public class CouponAndPackageDto implements Serializable {

    /**
     * 类型（1-优惠券、2-优惠券组合包）
     */
    private Integer type;

    /**
     * 类型ID（优惠券ID/优惠券组合包ID）
     */
    private String typeId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 副图URL列表（逗号分隔）
     */
    private String subImageUrls;

    /**
     * 描述
     */
    private String description;

    /**
     * 使用说明
     */
    private String useDescription;

    /**
     * 面值
     */
    private BigDecimal faceValue;

    /**
     * 剩余数量
     */
    private Integer remainNum;

    /**
     * 使用条件（核销条件）-（0-无门槛、1-满额、2-自定义）
     */
    private Integer useLimitFlag;

    /**
     * 自定义核销条件说明
     */
    private String useDivRemark;

    /**
     * 满额金额
     */
    private BigDecimal fullAmount;

    /**
     * 使用时间类型（0-不限制、1-指定时间、2-自领取后规定天数有效）
     */
    private Integer useTimeFlag;

    /**
     * 使用开始时间
     */
    private Date useStartTime;

    /**
     * 使用结束时间
     */
    private Date useEndTime;

    /**
     * 有效天数（自领取日起）
     */
    private Integer validDay;

    /**
     * 权益ID
     */
    private String equityId;

    /**
     * 权益名称
     */
    private String equityName;

    /**
     * 企业ID
     */
    private String enterpriseId;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 企业简称
     */
    private String enterpriseShortName;

    /**
     * 企业-省名称
     */
    private String enterpriseProvinceName;

    /**
     * 企业-市名称
     */
    private String enterpriseCityName;

    /**
     * 企业-区名称
     */
    private String enterpriseAreaName;

    /**
     * 企业-详细地址
     */
    private String enterpriseAddress;

    /**
     * 优惠券列表
     */
    private List<CouponEquityEnterpriseDto> couponList;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSubImageUrls() {
        return subImageUrls;
    }

    public void setSubImageUrls(String subImageUrls) {
        this.subImageUrls = subImageUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUseDescription() {
        return useDescription;
    }

    public void setUseDescription(String useDescription) {
        this.useDescription = useDescription;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Integer getUseLimitFlag() {
        return useLimitFlag;
    }

    public void setUseLimitFlag(Integer useLimitFlag) {
        this.useLimitFlag = useLimitFlag;
    }

    public String getUseDivRemark() {
        return useDivRemark;
    }

    public void setUseDivRemark(String useDivRemark) {
        this.useDivRemark = useDivRemark;
    }

    public BigDecimal getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(BigDecimal fullAmount) {
        this.fullAmount = fullAmount;
    }

    public Integer getUseTimeFlag() {
        return useTimeFlag;
    }

    public void setUseTimeFlag(Integer useTimeFlag) {
        this.useTimeFlag = useTimeFlag;
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    public String getEquityId() {
        return equityId;
    }

    public void setEquityId(String equityId) {
        this.equityId = equityId;
    }

    public String getEquityName() {
        return equityName;
    }

    public void setEquityName(String equityName) {
        this.equityName = equityName;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseShortName() {
        return enterpriseShortName;
    }

    public void setEnterpriseShortName(String enterpriseShortName) {
        this.enterpriseShortName = enterpriseShortName;
    }

    public String getEnterpriseProvinceName() {
        return enterpriseProvinceName;
    }

    public void setEnterpriseProvinceName(String enterpriseProvinceName) {
        this.enterpriseProvinceName = enterpriseProvinceName;
    }

    public String getEnterpriseCityName() {
        return enterpriseCityName;
    }

    public void setEnterpriseCityName(String enterpriseCityName) {
        this.enterpriseCityName = enterpriseCityName;
    }

    public String getEnterpriseAreaName() {
        return enterpriseAreaName;
    }

    public void setEnterpriseAreaName(String enterpriseAreaName) {
        this.enterpriseAreaName = enterpriseAreaName;
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        this.enterpriseAddress = enterpriseAddress;
    }

    public List<CouponEquityEnterpriseDto> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponEquityEnterpriseDto> couponList) {
        this.couponList = couponList;
    }
}
