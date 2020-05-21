package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 活动会员端DTO
 *
 * @author 蝈蝈
 * @since 2019年10月12日 14:39
 */
public class ActivityH5Dto implements Serializable {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 类型（1-领券活动、2-助力活动、3-发券活动、4-新会员活动,5-活动集）
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 活动缩略图URL
     */
    private String thumbnailUrl;

    /**
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回、6-商户待审核、7-商户审核驳回）
     */
    private Integer status;

    /**
     * 系统当前时间
     */
    private Date currentTime;

    /**
     * 提醒我（0-未设置、1-已设置）
     */
    private Integer remindMe;

    /**
     * 奖品列表
     */
    private List<ActivityAwardDto> awardList;

    /**
     * 活动对象类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     */
    private Integer targetType;

    /**
     * 活动对象列表
     */
    private List<String> targetIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getRemindMe() {
        return remindMe;
    }

    public void setRemindMe(Integer remindMe) {
        this.remindMe = remindMe;
    }

    public List<ActivityAwardDto> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<ActivityAwardDto> awardList) {
        this.awardList = awardList;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public List<String> getTargetIdList() {
        return targetIdList;
    }

    public void setTargetIdList(List<String> targetIdList) {
        this.targetIdList = targetIdList;
    }
}
