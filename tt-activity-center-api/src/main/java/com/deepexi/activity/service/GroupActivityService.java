package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityQueryDto;
import com.deepexi.activity.domain.dto.GroupActivityCreateDto;
import com.deepexi.activity.domain.dto.GroupActivityDto;
import com.deepexi.activity.domain.dto.GroupActivityListDto;
import com.deepexi.util.pageHelper.PageBean;

/**
 * 活动集服务
 * @author  pangyingfa
 *
 */
public interface GroupActivityService {

    /**
     * 新增
     *
     * @param createDto 数据
     * @return Boolean
     */
    Boolean create(GroupActivityCreateDto createDto);

    /**
     * 更新
     *
     * @param pk        活动主键ID
     * @param createDto 数据
     * @return Boolean
     */
    Boolean update(String pk, GroupActivityCreateDto createDto);

    /**
     * 批量删除
     *
     * @param activityIdList 活动ID列表
     * @return Boolean
     */
    Boolean delete(String... activityIdList);

    /**
     * 分页查询
     *
     * @param queryDto 查询DTO
     * @param page     页码
     * @param size     页大小
     * @return PageBean
     */
    PageBean<GroupActivityListDto> findPage(ActivityQueryDto queryDto, Integer page, Integer size);

    /**
     * 详情
     *
     * @param pk 活动主键ID
     * @return GroupActivityDto
     */
    GroupActivityDto detail(String pk);
}
