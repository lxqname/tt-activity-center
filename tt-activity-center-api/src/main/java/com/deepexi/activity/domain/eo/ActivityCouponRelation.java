package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;

/**
 * 活动优惠券关系
 *
 * @author 蝈蝈
 */
@TableName("ac_activity_coupon_relation")
public class ActivityCouponRelation extends SuperEntity implements Serializable {

    /**
     * 活动ID
     */
    private String activityId;

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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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
}

