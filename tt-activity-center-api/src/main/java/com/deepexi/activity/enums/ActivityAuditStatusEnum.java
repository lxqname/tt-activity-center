package com.deepexi.activity.enums;

public enum  ActivityAuditStatusEnum {

    /**
     * 0-待审核
     */
    PENDING_REVIEW(0, "待审核"),

    /**
     * 1-已通过
     */
    SUBMIT(1, "已通过"),
    /**
     * 2-已驳回
     */
    REJECT(2, "已驳回"),
    ;


    private Integer state;

    private String msg;

    ActivityAuditStatusEnum(Integer state, String msg){
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
        for (ActivityAuditStatusEnum activityAuditStatusEnum : values()) {
            if (activityAuditStatusEnum.getState().equals(state)) {
                return activityAuditStatusEnum.getMsg();
            }
        }
        return null;
    }

}
