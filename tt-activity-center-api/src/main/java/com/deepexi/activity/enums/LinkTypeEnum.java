package com.deepexi.activity.enums;

/**
 * 链接类型（1-页面链接、2-公众号）
 *
 * @author 蝈蝈
 *
 */
public enum  LinkTypeEnum {

    /** 页面链接 */
    PAGE_LINK(1, "页面链接"),
    /** 公众号 */
    PUBLIC(2, "公众号"),
    ;

    private Integer state;

    private String msg;

    LinkTypeEnum(Integer state, String msg) {
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
