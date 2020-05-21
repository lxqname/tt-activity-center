package com.deepexi.activity.enums;

/**
 * 活动对象类型
 *
 * @author 蝈蝈
 * @since 2019年09月25日 11:41
 */
public enum ActivityTargetTypeEnum {

    /**
     * 0-全部
     */
    All(0, "全部"),
    /**
     * 1-会员分组
     */
    MEMBER_GROUP(1, "会员分组"),
    /**
     * 2-关注公众号
     */
    ATTENTION_PUBLIC(2, "关注公众号"),
    /**
     * 3-会员标签
     */
    MEMBER_TAG(3, "会员标签"),
    ;

    private Integer state;

    private String msg;

    ActivityTargetTypeEnum(Integer state, String msg) {
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
