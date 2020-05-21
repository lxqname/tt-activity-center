package com.deepexi.activity.enums;

/**
 * 参与类型
 *
 * @author 蝈蝈
 * @since 2019年09月25日 16:15
 */
public enum ParticipateTypeEnum {

    /**
     * 1-发起者
     */
    PARTICIPATE(1, "发起者"),
    /**
     * 2-助力者
     */
    HELPER(2, "助力者"),
    ;

    private Integer state;

    private String msg;

    ParticipateTypeEnum(Integer state, String msg) {
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
