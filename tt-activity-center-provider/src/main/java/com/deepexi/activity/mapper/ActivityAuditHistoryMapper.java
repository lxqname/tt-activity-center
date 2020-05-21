package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.ActivityAuditHistoryDto;
import com.deepexi.activity.domain.eo.ActivityAuditHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动审核记录Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface ActivityAuditHistoryMapper extends BaseMapper<ActivityAuditHistory> {

    /**
     * 查询全部审核记录
     *
     * @param tenantCode 租户编码
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityAuditHistoryDto> selectByActivityId(@Param("tenantCode") String tenantCode,
                                                     @Param("activityId") String activityId);
}
