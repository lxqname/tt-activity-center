package com.deepexi.activity.enums;


public enum ActivityAuditHistoryStatusEnum {

    /**
     * 0-运营平台已驳回
     */
    MARKET_REJECT(0, "运营平台已驳回"),

    /**
     * 1-运营平台已通过
     */
    MARKET_SUBMIT(1, "运营平台已通过"),
    /**
     * 2-商户平台已驳回
     */
    BUSINESS_REJECT(2, "商户平台已驳回"),
    /**
     * 3-商户平台已通过
     */
    BUSINESS_SUBMIT(3, "商户平台已通过"),

    ;

    private Integer state;

    private String msg;

    ActivityAuditHistoryStatusEnum(Integer state, String msg){
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
        for (ActivityAuditHistoryStatusEnum activityAuditHistoryStatusEnum : values()) {
            if (activityAuditHistoryStatusEnum.getState().equals(state)) {
                return activityAuditHistoryStatusEnum.getMsg();
            }
        }
        return null;
    }

}
