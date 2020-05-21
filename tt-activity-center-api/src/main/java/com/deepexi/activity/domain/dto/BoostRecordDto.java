package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 助力记录DTO
 *
 * @author 蝈蝈
 * @since 2019年10月15日 19:19
 */
public class BoostRecordDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 会员活动ID
     */
    private String memberActivityId;

    /**
     * 助力人会员ID
     */
    private String boostMemberId;

    /**
     * 助力人昵称
     */
    private String boostMemberNickName;

    /**
     * 助力人头像
     */
    private String boostMemberHeadImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberActivityId() {
        return memberActivityId;
    }

    public void setMemberActivityId(String memberActivityId) {
        this.memberActivityId = memberActivityId;
    }

    public String getBoostMemberId() {
        return boostMemberId;
    }

    public void setBoostMemberId(String boostMemberId) {
        this.boostMemberId = boostMemberId;
    }

    public String getBoostMemberNickName() {
        return boostMemberNickName;
    }

    public void setBoostMemberNickName(String boostMemberNickName) {
        this.boostMemberNickName = boostMemberNickName;
    }

    public String getBoostMemberHeadImg() {
        return boostMemberHeadImg;
    }

    public void setBoostMemberHeadImg(String boostMemberHeadImg) {
        this.boostMemberHeadImg = boostMemberHeadImg;
    }
}
