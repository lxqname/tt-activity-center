package com.deepexi.activity.enums;

/**
 * 排序类型（1-快过期>领取时间降序、2-核销时间降序、3-失效时间降序 为null则按照创建时间降序排序）
 *
 * @author 蝈蝈
 * @since 2019年09月25日 11:30
 */
public enum SortTypeEnum {

    /**
     * 1-快过期>领取时间降序
     */
    EXPIRE_SOON_RECEIVE_TIME(1, "快过期>领取时间降序"),
    /**
     * 2-核销时间降序
     */
    VERIFICATION_TIME_DESC(2, "核销时间降序"),
    /**
     * 3-失效时间降序
     */
    EXPIRE_TIME_DESC(3, "失效时间降序"),
    ;

    private Integer state;

    private String msg;

    SortTypeEnum(Integer state, String msg) {
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
