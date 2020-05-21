package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 会员活动状态统计DTO
 *
 * @author 蝈蝈
 * @since 2019年10月21日 19:50
 */
public class MemberActivityStatusCountDto implements Serializable {

    /**
     * 成功数量
     */
    private Integer successNum;

    /**
     * 进行中数量
     */
    private Integer processingNum;

    /**
     * 失败数量
     */
    private Integer failNum;

    public Integer getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public Integer getProcessingNum() {
        return processingNum;
    }

    public void setProcessingNum(Integer processingNum) {
        this.processingNum = processingNum;
    }

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }
}
