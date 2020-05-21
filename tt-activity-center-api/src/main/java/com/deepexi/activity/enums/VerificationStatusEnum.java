package com.deepexi.activity.enums;

/**
 * 核销状态
 *
 * @author 蝈蝈
 */
public enum VerificationStatusEnum {
    /**
     * 预定
     */
    RESERVATION(-1, "预定"),
    /**
     * 待领取
     */
    PENDING_RECEIVE(0, "待领取"),
    /**
     * 未使用/未核销
     */
    PENDING_USE(1, "未使用/未核销"),
    /**
     * 使用中
     */
    USING(2, "使用中"),
    /**
     * 已使用/已核销
     */
    USED(3,"已使用/已核销"),
    /**
     * 已过期
     */
    EXPIRED(4, "已过期"),
    /**
     * 退款失效
     */
    REFUND_FAIL(6, "退款失效"),
    ;

    private Integer state;

    private String msg;

    VerificationStatusEnum(Integer state, String msg) {
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
