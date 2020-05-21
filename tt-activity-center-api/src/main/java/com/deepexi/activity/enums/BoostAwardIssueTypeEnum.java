package com.deepexi.activity.enums;

/**
 * 助力奖品发放条件类型
 *
 * @author 蝈蝈
 * @since 2019年08月17日 17:07
 */
public enum BoostAwardIssueTypeEnum {

    /**
     * 1-满足任一阶梯后即发放每一阶梯的奖品
     */
    ANY_AFTER_ANY(1, "满足任一阶梯后即发放每一阶梯的奖品"),
    /**
     * 2-满足活动最高阶梯后即发放全部阶梯的奖品
     */
    ALL_AFTER_TOP(2, "满足活动最高阶梯后即发放全部阶梯的奖品"),
    /**
     * 3-活动结束后发放所满足的全部阶梯奖品
     */
    ALL_AFTER_ACTIVITY_TIME(3, "活动结束后发放所满足的全部阶梯奖品"),
    /**
     * 4-活动结束后发放所满足的最高阶梯奖品
     */
    TOP_AFTER_ACTIVITY_TIME(4, "活动结束后发放所满足的最高阶梯奖品"),
    /**
     * 5-有效期结束后发放所满足的全部阶梯奖品
     */
    ALL_AFTER_VALID_TIME(5, "有效期结束后发放所满足的全部阶梯奖品"),
    /**
     * 6-有效期结束后发放所满足的最高阶梯奖品
     */
    TOP_AFTER_VALID_TIME(6, "有效期结束后发放所满足的最高阶梯奖品"),
    ;

    private Integer state;

    private String msg;

    BoostAwardIssueTypeEnum(Integer state, String msg) {
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
