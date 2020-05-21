package com.deepexi.activity.enums;

public enum ActivityApplicationTypeEnum {

    /**
     * 1-运营平台
     */
    MARKET_PLATFORM(1, "运营平台"),

    /**
     * 2-商户平台
     */
    BUSINESS_PLATFORM(2, "商户平台"),
    ;

    private Integer state;

    private String msg;

    ActivityApplicationTypeEnum(Integer state, String msg){
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
        for (ActivityApplicationTypeEnum activityApplicationTypeEnum : values()) {
            if (activityApplicationTypeEnum.getState().equals(state)) {
                return activityApplicationTypeEnum.getMsg();
            }
        }
        return null;
    }

}
