package com.deepexi.activity.domain.dto;

import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * 活动链接查询dto
 * @author pangyingfa
 * @since 2019年9月16日16:51:23
 */
public class LinkQueryDto implements Serializable {

    /**
     * 租户
     */
    private String tenantCode;

    /**
     * 名称-模糊查询
     */
    @QueryParam("name")
    private String fuzzyName;

    /**
     * 类型（1-页面链接、2-公众号）
     */
    @QueryParam("type")
    private Integer type;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getFuzzyName() {
        return fuzzyName;
    }

    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
