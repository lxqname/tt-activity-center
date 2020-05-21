package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 活动奖品保存DTO
 *
 * @author 蝈蝈
 * @since 2019年09月24日 22:03
 */
public class ActivityAwardSaveDto implements Serializable {

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
}
