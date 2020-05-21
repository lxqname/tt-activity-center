package com.deepexi.activity.domain.dto;

import java.io.Serializable;

/**
 * 渠道列表DTO
 *
 * @author 蝈蝈
 * @since 2019年10月01日 15:15
 */
public class ChannelListDto implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区名称
     */
    private String areaName;

    /**
     * 渠道分类ID
     */
    private String channelClassifyId;

    /**
     * 渠道分类名称
     */
    private String channelClassifyName;

    /**
     * 渠道主账号ID
     */
    private String accountId;

    /**
     * 渠道主账号名称
     */
    private String accountName;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getChannelClassifyId() {
        return channelClassifyId;
    }

    public void setChannelClassifyId(String channelClassifyId) {
        this.channelClassifyId = channelClassifyId;
    }

    public String getChannelClassifyName() {
        return channelClassifyName;
    }

    public void setChannelClassifyName(String channelClassifyName) {
        this.channelClassifyName = channelClassifyName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
