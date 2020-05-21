package com.deepexi.activity.service;

import java.util.List;

/**
 * 活动提醒服务
 *
 * @author 蝈蝈
 */
public interface ActivityRemindService {

    /**
     * 新增
     *
     * @param activityId 活动ID
     * @return Boolean
     */
    Boolean create(String activityId);

    /**
     * 取消活动提醒
     *
     * @param activityId 活动ID
     * @return Boolean
     */
    Boolean cancel(String activityId);

    /**
     * 根据登录会员信息过滤活动ID列表
     *
     * @param activityIdList 活动ID列表
     * @return List
     */
    List<String> filterActivityIdListByLogin(List<String> activityIdList);

    /**
     * 活动提醒
     *
     * @return Integer
     */
    Integer remind();
}