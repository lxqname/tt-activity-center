package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员活动
 *
 * @author 蝈蝈
 */
@TableName("ac_member_activity")
public class MemberActivity extends SuperEntity implements Serializable {

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 活动有效时间
     */
    private BigDecimal activityValidTime;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 助力人数
     */
    private Integer boostNum;

    /**
     * 状态（1-成功、2-进行中、3-失败）
     */
    private Integer status;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 分享者渠道ID
     */
    private String shareChannelId;

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

    public BigDecimal getActivityValidTime() {
        return activityValidTime;
    }

    public void setActivityValidTime(BigDecimal activityValidTime) {
        this.activityValidTime = activityValidTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getBoostNum() {
        return boostNum;
    }

    public void setBoostNum(Integer boostNum) {
        this.boostNum = boostNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getShareChannelId() {
        return shareChannelId;
    }

    public void setShareChannelId(String shareChannelId) {
        this.shareChannelId = shareChannelId;
    }
}

