package com.deepexi.activity.enums;

/**
 * 渠道推广（0-不允许推广、1-允许全部渠道推广、2-允许部分渠道推广）
 *
 * @author 蝈蝈
 */
public enum ChannelPromotionTypeEnum {

    /**
     * 不允许
     */
    NOT_ALLOWED(0, "不允许"),
    /**
     * 允许全部渠道推广
     */
    ALLOW_ALL(1, "允许全部渠道推广"),
    /**
     * 允许部分渠道推广
     */
    ALLOW_PART(2, "允许部分渠道推广"),
    ;

    private Integer state;

    private String msg;

    ChannelPromotionTypeEnum(Integer state, String msg) {
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
