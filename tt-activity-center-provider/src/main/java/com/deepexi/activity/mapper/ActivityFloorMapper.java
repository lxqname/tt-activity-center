package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.ActivityFloorDto;
import com.deepexi.activity.domain.eo.ActivityFloor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface ActivityFloorMapper extends BaseMapper<ActivityFloor> {

    List<ActivityFloor> findList(@Param("eo") ActivityFloorDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<ActivityFloorDto> eo);

    int batchUpdate(@Param("eo") List<ActivityFloorDto> eo);

    /**
     * 根据活动ID统计
     *
     * @param tenantCode 租户
     * @param activityId 活动ID
     * @return Integer
     */
    Integer countByActivityId(@Param("tenantCode") String tenantCode, @Param("activityId") String activityId);

    /**
     * 根据活动ID删除
     *
     * @param tenantCode 租户
     * @param activityId 活动ID
     * @param updatedBy  更新者
     * @return int 影响行数
     */
    int deleteByActivityId(@Param("tenantCode") String tenantCode, @Param("activityId") String activityId, @Param("updatedBy") String updatedBy);

    /**
     * 根据活动ID查询列表
     *
     * @param tenantCode 租户
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityFloor> findByActivityId(@Param("tenantCode") String tenantCode, @Param("activityId") String activityId);
}
