package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;

/**
 * 活动提醒
 *
 * @author 蝈蝈
 */
@TableName("ac_activity_remind")
public class ActivityRemind extends SuperEntity implements Serializable {

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 是否发送（0-未发送、1-已发送）
     */
    private Integer sendSuccess;

    /**
     * 备注
     */
    private String remark;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getSendSuccess() {
        return sendSuccess;
    }

    public void setSendSuccess(Integer sendSuccess) {
        this.sendSuccess = sendSuccess;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

