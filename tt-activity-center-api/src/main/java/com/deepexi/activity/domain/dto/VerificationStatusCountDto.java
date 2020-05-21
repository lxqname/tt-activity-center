package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 核销状态统计DTO
 *
 * @author 蝈蝈
 * @since 2019年10月19日 17:35
 */
public class VerificationStatusCountDto implements Serializable {

    /**
     * 待领取数量
     */
    private Integer pendingReceiveNum;

    /**
     * 待领取金额
     */
    private BigDecimal pendingReceiveAmount;

    /**
     * 待核销数量
     */
    private Integer pendingUseNum;

    /**
     * 待核销金额
     */
    private BigDecimal pendingUseAmount;

    /**
     * 已核销数量
     */
    private Integer usedNum;

    /**
     * 已核销金额
     */
    private BigDecimal usedAmount;

    /**
     * 已过期数量
     */
    private Integer expiredNum;

    /**
     * 已过期金额
     */
    private BigDecimal expiredAmount;

    /**
     * 已领取数量 = 待核销数量 + 已核销数量 + 已过期数量
     */
    private Integer receiveNum;

    /**
     * 已领取金额 = 待核销金额 + 已核销金额 + 已过期金额
     */
    private BigDecimal receiveAmount;

    public Integer getPendingReceiveNum() {
        return pendingReceiveNum;
    }

    public void setPendingReceiveNum(Integer pendingReceiveNum) {
        this.pendingReceiveNum = pendingReceiveNum;
    }

    public BigDecimal getPendingReceiveAmount() {
        return pendingReceiveAmount;
    }

    public void setPendingReceiveAmount(BigDecimal pendingReceiveAmount) {
        this.pendingReceiveAmount = pendingReceiveAmount;
    }

    public Integer getPendingUseNum() {
        return pendingUseNum;
    }

    public void setPendingUseNum(Integer pendingUseNum) {
        this.pendingUseNum = pendingUseNum;
    }

    public BigDecimal getPendingUseAmount() {
        return pendingUseAmount;
    }

    public void setPendingUseAmount(BigDecimal pendingUseAmount) {
        this.pendingUseAmount = pendingUseAmount;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Integer getExpiredNum() {
        return expiredNum;
    }

    public void setExpiredNum(Integer expiredNum) {
        this.expiredNum = expiredNum;
    }

    public BigDecimal getExpiredAmount() {
        return expiredAmount;
    }

    public void setExpiredAmount(BigDecimal expiredAmount) {
        this.expiredAmount = expiredAmount;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }
}
