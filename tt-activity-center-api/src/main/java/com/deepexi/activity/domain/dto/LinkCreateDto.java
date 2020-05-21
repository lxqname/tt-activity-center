package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 活动链接新增dto
 * @author pangyingfa
 * @since 2019年9月16日16:51:23
 */
public class LinkCreateDto implements Serializable {

    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型（1-页面链接、2-公众号）
     */
    private Integer type;

    /**
     * 页面链接URL
     */
    private String url;

    /**
     * 公众号原始ID
     */
    private String wechatNumberId;

    /**
     * 备注
     */
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWechatNumberId() {
        return wechatNumberId;
    }

    public void setWechatNumberId(String wechatNumberId) {
        this.wechatNumberId = wechatNumberId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
