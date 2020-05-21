package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.ActivityOrganizationDto;
import com.deepexi.activity.domain.eo.ActivityOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityOrganizationMapper extends BaseMapper<ActivityOrganization> {

    List<ActivityOrganization> findList(@Param("eo") ActivityOrganizationDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<ActivityOrganizationDto> eo);

    int batchUpdate(@Param("eo") List<ActivityOrganizationDto> eo);

    /**
     * 列表查询
     *
     * @param tenantCode 租户
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityOrganization> selectByActivityId(@Param("tenantCode") String tenantCode, @Param("activityId") String activityId);
}
