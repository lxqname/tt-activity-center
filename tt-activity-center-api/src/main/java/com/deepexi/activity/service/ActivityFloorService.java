package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityFloorCreateDto;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.activity.domain.eo.ActivityFloor;
import com.deepexi.activity.domain.dto.ActivityFloorDto;

import java.util.*;

/**
 * 活动楼层
 * @author pangyingfa
 */
public interface ActivityFloorService {

    /**
     * 分页查询
     * @param eo
     * @param page
     * @param size
     * @return
     */
    PageBean<ActivityFloor> findPage(ActivityFloorDto eo, Integer page, Integer size);

    /**
     * 条件查询
     * @param eo
     * @return
     */
    List<ActivityFloor> findAll(ActivityFloorDto eo);

    /**
     * 详情
     * @param pk
     * @return
     */
    ActivityFloor detail(String pk);

    /**
     * 编辑
     * @param pk
     * @param eo
     * @return
     */
    Boolean update(String pk, ActivityFloorDto eo);

    /**
     * 新增
     * @param eo
     * @return
     */
    Boolean create(ActivityFloorDto eo);

    /**
     * dto新增
     * @param id
     * @param list
     * @return
     */
    Boolean create(String id, List<ActivityFloorCreateDto> list);

    /**
     * 删除
     * @param pk
     * @return
     */
    Boolean delete(String... pk);

    /**
     * 根据活动ID统计
     * @param activityId
     * @return
     */
    Integer countByActivityId(String activityId);

    /**
     * 根据活动ID删除
     * @param activityId
     * @return
     */
    Integer deleteByActivityId(String activityId);

    /**
     * 根据活动ID查询详细信息列表
     *
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityFloorDto> findActivityFloorDtoList(String activityId);
}