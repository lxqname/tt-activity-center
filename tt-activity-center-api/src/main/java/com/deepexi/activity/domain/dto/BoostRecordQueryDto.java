package com.deepexi.activity.domain.dto;

import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * 助力记录查询DTO
 *
 * @author 蝈蝈
 * @since 2019年10月15日 11:30
 */
public class BoostRecordQueryDto implements Serializable {

    /**
     * DR
     */
    private Integer dr;

    /**
     * 多租户标识
     */
    private String tenantCode;

    /**
     * 会员活动ID
     */
    @QueryParam("memberActivityId")
    private String memberActivityId;

    /**
     * 助力人会员ID
     */
    private String boostMemberId;

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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
}
