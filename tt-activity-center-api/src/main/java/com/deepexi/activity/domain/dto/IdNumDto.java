package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 主键ID-数量DTO
 *
 * @author 蝈蝈
 */
public class IdNumDto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 数量
     */
    private Integer num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
