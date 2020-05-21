package com.deepexi.activity.enums;

/**
 * 活动楼层_关联类型
 * 1-商品 2-活动 3-自定义url  4-无
 */
public enum ConnectTypeEnum {

    /**
     * 1-商品
     */
    ITEM(1, "商品"),
    /**
     * 2-活动
     */
    ACTIVITY(2, "活动"),
    /**
     * 3-自定义URL
     */
    DIV_URL(3, "自定义URL"),
    /**
     * 4-无
     */
    NONE(4, "无"),
    ;

    private Integer state;

    private String msg;

    ConnectTypeEnum(Integer state, String msg) {
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
