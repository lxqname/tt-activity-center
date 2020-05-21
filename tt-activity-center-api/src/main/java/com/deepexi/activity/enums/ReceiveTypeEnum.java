package com.deepexi.activity.enums;

/**
 * 奖励领取方式（0-系统发放、1-线上领取）
 *
 * @author 蝈蝈
 * @since 2019年10月05日 16:34
 */
public enum ReceiveTypeEnum {

    /**
     * 0-系统发放
     */
    GET_ON_SYSTEM(0, "系统发放"),
    /**
     * 1-线上领取
     */
    GET_ON_LINE(1, "线上领取"),
    ;

    private Integer state;

    private String msg;

    ReceiveTypeEnum(Integer state, String msg) {
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
