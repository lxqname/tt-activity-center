package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数量-金额DTO
 *
 * @author 蝈蝈
 * @since 2019年10月19日 18:06
 */
public class NumAmountDto implements Serializable {

    /**
     * 数量
     */
    private Integer num;

    /**
     * 金额
     */
    private BigDecimal amount;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
