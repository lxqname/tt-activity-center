package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;

/**
 * 活动对象
 *
 * @author 蝈蝈
 */
@TableName("ac_activity_target")
public class ActivityTarget extends SuperEntity implements Serializable {

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 参与类型（1-发起者、2-助力者）
     */
    private Integer participateType;

    /**
     * 类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     */
    private Integer type;

    /**
     * 类型ID（会员分组ID/公众号ID）
     */
    private String typeId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getParticipateType() {
        return participateType;
    }

    public void setParticipateType(Integer participateType) {
        this.participateType = participateType;
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
}

