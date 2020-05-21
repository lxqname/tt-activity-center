package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 主键及名称DTO
 *
 * @author 蝈蝈
 * @since 2019年09月26日 21:14
 */
public class IdNameDto implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    public IdNameDto() {
    }

    public IdNameDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
