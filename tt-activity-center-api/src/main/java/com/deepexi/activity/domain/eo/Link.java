package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;


/**
 * 活动链接
 *
 * @author pangyingfa
 * @since 2019年9月16日17:15:41
 */
@TableName("ac_link")
public class Link extends SuperEntity implements Serializable {

    /**
     * 名称
     */
    private String name;
    /**
     * 类型（1-页面链接、2-公众号）
     */
    private Integer type;
    /**
     * 链接url
     */
    private String url;
    /**
     * 公众号原始id
     */
    private String wechatNumberId;
    /**
     * 备注
     */
    private String remark;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setWechatNumberId(String wechatNumberId) {
        this.wechatNumberId = wechatNumberId;
    }

    public String getWechatNumberId() {
        return this.wechatNumberId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }
}

