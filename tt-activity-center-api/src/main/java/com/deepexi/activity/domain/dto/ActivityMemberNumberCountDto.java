package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * @author LuoGuang
 * @version V1.0
 * @Package com.deepexi.activity.domain.vo
 * @Description:
 * @date: 2019/5/30 18:58
 */
public class ActivityMemberNumberCountDto implements Serializable {

    /**
     * 未使用优惠卷 数量
     */
    private int unUsedCouponNumber;
    /**
     * 我的活动数据 数量
     */
    private int myActivityNumber;
    /**
     * 待领取礼品 数量
     */
    private int unReceiveGiftNumber;
    /**
     * 未付款的订单 数量
     */
    private int unPayOrder;

    public int getUnUsedCouponNumber() {
        return unUsedCouponNumber;
    }

    public void setUnUsedCouponNumber(int unUsedCouponNumber) {
        this.unUsedCouponNumber = unUsedCouponNumber;
    }

    public int getMyActivityNumber() {
        return myActivityNumber;
    }

    public void setMyActivityNumber(int myActivityNumber) {
        this.myActivityNumber = myActivityNumber;
    }

    public int getUnReceiveGiftNumber() {
        return unReceiveGiftNumber;
    }

    public void setUnReceiveGiftNumber(int unReceiveGiftNumber) {
        this.unReceiveGiftNumber = unReceiveGiftNumber;
    }

    public int getUnPayOrder() {
        return unPayOrder;
    }

    public void setUnPayOrder(int unPayOrder) {
        this.unPayOrder = unPayOrder;
    }

    @Override
    public String toString() {
        return "ActivityMemberNumberCountDto{" +
                "unUsedCouponNumber=" + unUsedCouponNumber +
                ", myActivityNumber=" + myActivityNumber +
                ", unReceiveGiftNumber=" + unReceiveGiftNumber +
                ", unPayOrder=" + unPayOrder +
                '}';
    }
}
