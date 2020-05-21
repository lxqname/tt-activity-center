package com.deepexi.activity.enums;

/**
 * 会员活动状态（1-成功、2-进行中、3-失败）
 *
 * @author 蝈蝈
 */
public enum MemberActivityStatusEnum {
    /**
     * 成功
     */
    SUCCESS(1, "成功"),
    /**
     * 进行中
     */
    PROCESSING(2, "进行中"),
    /**
     * 失败
     */
    FAIL(3, "失败"),
    ;

    private Integer state;

    private String msg;

    MemberActivityStatusEnum(Integer state, String msg) {
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
