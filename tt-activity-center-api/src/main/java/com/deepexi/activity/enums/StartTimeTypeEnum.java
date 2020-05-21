package com.deepexi.activity.enums;

/**
 * 开始时间类型
 *
 * @author 蝈蝈
 * @since 2019年09月25日 11:30
 */
public enum StartTimeTypeEnum {

    /**
     * 1-立即
     */
    NOW(1, "立即"),
    /**
     * 2-定时
     */
    ON_TIME(2, "定时"),
    ;

    private Integer state;

    private String msg;

    StartTimeTypeEnum(Integer state, String msg) {
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
