package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityAuditHistoryDto;

import java.util.List;

/**
 * 活动审核记录服务
 *
 * @author 蝈蝈
 */
public interface ActivityAuditHistoryService {

    /**
     * 新增
     *
     * @param activityId 活动ID
     * @param status     审核状态（0-驳回、1-通过）
     * @param remark     审核备注
     */
    void create(String activityId, Integer status, String remark);

    /**
     * 查询全部审核记录
     *
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityAuditHistoryDto> findAll(String activityId);
}