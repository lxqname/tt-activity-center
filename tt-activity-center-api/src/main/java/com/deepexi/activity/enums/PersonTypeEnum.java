package com.deepexi.activity.enums;

/**
 * 人员类型
 *
 * @author 蝈蝈
 */
public enum PersonTypeEnum {
    /**
     * 企业
     */
    ENTERPRISE(1, "企业"),
    /**
     * 渠道
     */
    CHANNEL(2, "渠道"),
    /**
     * 会员
     */
    MEMBER(3, "会员"),
    ;

    private Integer state;

    private String msg;

    PersonTypeEnum(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
