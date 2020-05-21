package com.deepexi.activity.domain.dto;

import java.util.Date;
import java.io.Serializable;

/**
 * 活动链接dto
 * @author pangyingfa
 * @since 2019年9月16日16:51:23
 */
public class LinkDto implements Serializable {
    private String id;

    private String name;

    private Integer type;

    private String url;

    private String wechatNumberId;

    private String wechatNumberName;

    private String remark;

    private String createdBy;

    private Date createdAt;



    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getType(){
        return this.type;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setWechatNumberId(String wechatNumberId){
        this.wechatNumberId = wechatNumberId;
    }

    public String getWechatNumberId(){
        return this.wechatNumberId;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return this.remark;
    }


    public String getWechatNumberName() {
        return wechatNumberName;
    }

    public void setWechatNumberName(String wechatNumberName) {
        this.wechatNumberName = wechatNumberName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

