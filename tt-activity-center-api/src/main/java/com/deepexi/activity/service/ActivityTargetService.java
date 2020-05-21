package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityConditionDto;
import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.activity.domain.dto.ActivityListDto;
import com.deepexi.activity.domain.dto.TargetDto;

import java.util.List;

/**
 * 活动对象服务
 *
 * @author 蝈蝈
 */
public interface ActivityTargetService {

    /**
     * 新增
     *
     * @param activityId      活动ID
     * @param participateType 参与类型（1-发起者、2-助力者）
     * @param type            类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     * @param typeId          类型ID
     */
    void create(String activityId, Integer participateType, Integer type, String typeId);

    /**
     * 新增
     *
     * @param activityId      活动ID
     * @param participateType 参与类型（1-发起者、2-助力者）
     * @param type            类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     * @param typeIdList      类型ID列表
     */
    void create(String activityId, Integer participateType, Integer type, List<String> typeIdList);

    /**
     * 新增
     *
     * @param activityId           活动ID
     * @param participateType      参与类型（1-发起者、2-助力者）
     * @param activityConditionDto 活动条件DTO
     */
    void create(String activityId, Integer participateType, ActivityConditionDto activityConditionDto);

    /**
     * 根据活动ID删除
     *
     * @param activityId 活动ID
     */
    void deleteByActivityId(String activityId);

    /**
     * 填充活动对象信息
     *
     * @param list 数据
     */
    void fillUpTargetInfo(List<ActivityListDto> list);

    /**
     * 填充活动对象信息
     *
     * @param activityDetailDto 数据
     */
    void fillUpTargetInfo(ActivityDetailDto activityDetailDto);

    /**
     * 查询活动条件
     *
     * @param activityId      活动ID
     * @param participateType 参与类型（1-发起者、2-助力者）
     * @return ActivityConditionDto
     */
    ActivityConditionDto findActivityConditionDto(String activityId, Integer participateType);

    /**
     * 查询目标信息
     *
     * @param activityId 活动ID
     * @return TargetDto
     */
    TargetDto queryTargetInfo(String activityId);

    /**
     * 查询目标会员的ID列表
     *
     * @param activityId      活动ID
     * @param applicationType 创建平台
     * @return List
     */
    List<String> findTargetMemberIdList(String activityId, Integer applicationType);
}