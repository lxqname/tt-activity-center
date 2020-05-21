package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 活动奖品DTO
 *
 * @author 蝈蝈
 * @since 2019年09月26日 11:30
 */
public class ActivityAwardDto implements Serializable {

    /**
     * 关系主键ID
     */
    private String id;

    /**
     * 类型（1-优惠券、2-优惠券组合包）
     */
    private Integer type;

    /**
     * 类型ID（优惠券ID/优惠券组合包ID）
     */
    private String typeId;

    /**
     * 类型名称（优惠券名称/优惠券组合包名称）
     */
    private String typeName;

    /**
     * 类型详情
     */
    private CouponAndPackageDto typeDetail;

    /**
     * 奖励标题
     */
    private String title;

    /**
     * 达标数量
     */
    private Integer completeNum;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 剩余数量
     */
    private Integer remainNum;

    /**
     * 奖励领取方式（0-系统发放、1-线上领取）
     */
    private Integer receiveType;

    /**
     * 奖励领取说明
     */
    private String receiveDescription;

    /**
     * 领取限制数量
     */
    private Integer receiveLimitNum;

    /**
     * 商品标签列表
     */
    private List<IdNameDto> itemTagList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public CouponAndPackageDto getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(CouponAndPackageDto typeDetail) {
        this.typeDetail = typeDetail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public String getReceiveDescription() {
        return receiveDescription;
    }

    public void setReceiveDescription(String receiveDescription) {
        this.receiveDescription = receiveDescription;
    }

    public Integer getReceiveLimitNum() {
        return receiveLimitNum;
    }

    public void setReceiveLimitNum(Integer receiveLimitNum) {
        this.receiveLimitNum = receiveLimitNum;
    }

    public List<IdNameDto> getItemTagList() {
        return itemTagList;
    }

    public void setItemTagList(List<IdNameDto> itemTagList) {
        this.itemTagList = itemTagList;
    }
}
