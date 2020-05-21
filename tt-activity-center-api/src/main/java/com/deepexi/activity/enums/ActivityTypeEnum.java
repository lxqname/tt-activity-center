package com.deepexi.activity.enums;

/**
 * 活动类型（1-领券活动、2-助力活动、3-发券活动、4-新会员活动,5-活动集）
 *
 * @author 蝈蝈
 */
public enum ActivityTypeEnum {
    /**
     * 1-领券活动
     */
    RECEIVE_COUPON_ACTIVITY(1, "领券活动"),
    /**
     * 2-助力活动
     */
    BOOST_ACTIVITY(2, "助力活动"),
    /**
     * 3-发券活动
     */
    ISSUE_COUPON_ACTIVITY(3, "发券活动"),
    /**
     * 4-新会员活动
     */
    NEW_MEMBER_ACTIVITY(4, "新会员活动"),
    /**
     * 5-活动集
     */
    GROUP_ACTIVITY(5, "活动集"),
    ;

    private Integer state;

    private String msg;

    ActivityTypeEnum(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

}
