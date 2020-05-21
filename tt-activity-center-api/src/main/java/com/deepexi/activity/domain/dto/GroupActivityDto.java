package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 活动集DTO
 *
 * @author 蝈蝈
 * @since 2019年07月05日 10:31
 */
public class GroupActivityDto implements Serializable {

    private String id;

    /**
     * 活动名称
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
     * 活动集明细
     */
    private List<ActivityFloorDto> list;

    /**
     * 活动状态（0-草稿、1-待审核、2-待生效、3-进行中、4-已失效、5-已驳回）
     */
    private Integer status;

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

    public List<ActivityFloorDto> getList() {
        return list;
    }

    public void setList(List<ActivityFloorDto> list) {
        this.list = list;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
