package com.deepexi.activity.service;

import java.util.List;

/**
 * 活动渠道关系服务
 *
 * @author 蝈蝈
 */
public interface ActivityChannelService {

    /**
     * 新增
     *
     * @param activityId 活动ID
     * @param channelId  渠道ID
     */
    void create(String activityId, String channelId);

    /**
     * 新增
     *
     * @param activityId    活动ID
     * @param channelIdList 渠道ID列表
     */
    void create(String activityId, List<String> channelIdList);

    /**
     * 根据活动ID删除
     *
     * @param activityId 活动ID
     */
    void deleteByActivityId(String activityId);

    /**
     * 根据活动ID查询推广渠道ID列表
     *
     * @param activityId 活动ID
     * @return List
     */
    List<String> findChannelIdList(String activityId);

    /**
     * 根据活动ID查询推广渠道信息
     *
     * @param activityId 活动ID
     * @return List
     */
    List findChannelList(String activityId);
}