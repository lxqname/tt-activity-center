package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员奖品统计DTO
 *
 * @author 蝈蝈
 * @since 2019年10月21日 17:34
 */
public class MemberAwardCountDto implements Serializable {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员手机号
     */
    private String memberMobile;

    /**
     * 待领取数量
     */
    private Integer pendingReceiveNum;

    /**
     * 待核销数量
     */
    private Integer pendingUseNum;

    /**
     * 已核销数量
     */
    private Integer usedNum;

    /**
     * 已过期数量
     */
    private Integer expiredNum;

    /**
     * 已领取数量 = 待核销数量 + 已核销数量 + 已过期数量
     */
    private Integer receiveNum;

    /**
     * 发放时间
     */
    private Date issueTime;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public Integer getPendingReceiveNum() {
        return pendingReceiveNum;
    }

    public void setPendingReceiveNum(Integer pendingReceiveNum) {
        this.pendingReceiveNum = pendingReceiveNum;
    }

    public Integer getPendingUseNum() {
        return pendingUseNum;
    }

    public void setPendingUseNum(Integer pendingUseNum) {
        this.pendingUseNum = pendingUseNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public Integer getExpiredNum() {
        return expiredNum;
    }

    public void setExpiredNum(Integer expiredNum) {
        this.expiredNum = expiredNum;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }
}
