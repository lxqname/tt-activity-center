package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.eo.ActivityChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动渠道关系Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface ActivityChannelMapper extends BaseMapper<ActivityChannel> {

    /**
     * 根据活动ID删除
     *
     * @param activityId 活动ID
     * @param updateBy   更新人
     * @return int 影响行数
     */
    int deleteByActivityId(@Param("activityId") String activityId, @Param("updateBy") String updateBy);

    /**
     * 根据活动ID查询推广渠道ID列表
     *
     * @param tenantCode 租户
     * @param activityId 活动ID
     * @return List
     */
    List<String> selectChannelIdList(@Param("tenantCode") String tenantCode, @Param("activityId") String activityId);
}
