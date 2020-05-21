package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;

/**
 * 活动推广渠道
 *
 * @author 蝈蝈
 */
@TableName("ac_activity_channel")
public class ActivityChannel extends SuperEntity implements Serializable {

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 渠道ID
     */
    private String channelId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}

