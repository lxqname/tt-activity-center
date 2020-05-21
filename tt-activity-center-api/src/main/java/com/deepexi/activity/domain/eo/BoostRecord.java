package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;

/**
 * 助力记录
 *
 * @author 蝈蝈
 */
@TableName("ac_boost_record")
public class BoostRecord extends SuperEntity implements Serializable {

    /**
     * 会员活动ID
     */
    private String memberActivityId;

    /**
     * 助力人会员ID
     */
    private String boostMemberId;

    public String getMemberActivityId() {
        return memberActivityId;
    }

    public void setMemberActivityId(String memberActivityId) {
        this.memberActivityId = memberActivityId;
    }

    public String getBoostMemberId() {
        return boostMemberId;
    }

    public void setBoostMemberId(String boostMemberId) {
        this.boostMemberId = boostMemberId;
    }
}

