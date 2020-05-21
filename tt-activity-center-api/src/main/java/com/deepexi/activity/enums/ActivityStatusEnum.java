package com.deepexi.activity.enums;

/**
 * 活动状态
 */
public enum ActivityStatusEnum {
    /**
     * 草稿
     */
    DRAFT(0, "草稿"),
    /**
     * 待审核
     */
    PENDING_REVIEW(1, "待审核"),
    /**
     * 待生效
     */
    PENDING_START(2,"待生效"),
    /**
     * 进行中
     */
    PROCESSING(3, "进行中"),
    /**
     * 已失效
     */
    INVALID(4, "已失效"),
    /**
     * 已驳回
     */
    REJECT(5, "已驳回"),
    /**
     * 商户待审核
     */
    BUSINESS_PENDING_REVIEW(6,"商户待审核"),
    /**
     * 商户审核驳回
     */
    BUSINESS_REJECT(7,"商户审核驳回"),
    ;

    private Integer state;

    private String msg;

    ActivityStatusEnum(Integer state, String msg) {
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

    public static String getMsgByState(Integer state) {
        for (ActivityStatusEnum activityStatusEnum : values()) {
            if (activityStatusEnum.getState().equals(state)) {
                return activityStatusEnum.getMsg();
            }
        }
        return null;
    }
}
