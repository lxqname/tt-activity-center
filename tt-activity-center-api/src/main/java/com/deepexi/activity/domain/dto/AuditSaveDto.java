package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 审核保存DTO
 *
 * @author 蝈蝈
 * @since 2019年09月18日 20:15
 */
public class AuditSaveDto implements Serializable {

    /**
     * 审核状态（0-已驳回、1-已通过）
     */
    private Integer status;

    /**
     * 审核备注
     */
    private String remark;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
