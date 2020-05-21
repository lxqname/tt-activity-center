package com.deepexi.activity.domain.dto;

import java.io.Serializable;

public class ActivityFloorCreateDto implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 图片URL
     */
    private String imgUrl;

    /**
     * 关联类型 1-商品 2-活动 3-自定义url  4-无
     */
    private Integer type;

    /**
     * 关联类型参数（逗号分隔）
     */
    private String typeParam;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeParam() {
        return typeParam;
    }

    public void setTypeParam(String typeParam) {
        this.typeParam = typeParam;
    }
}
