package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.eo.ActivityTarget;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动对象Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface ActivityTargetMapper extends BaseMapper<ActivityTarget> {

    /**
     * 根据活动ID删除
     *
     * @param activityId 活动ID
     * @param updateBy   更新人
     * @return int 影响行数
     */
    int deleteByActivityId(@Param("activityId") String activityId, @Param("updateBy") String updateBy);

    /**
     * 根据活动ID查询
     *
     * @param tenantCode      租户
     * @param activityId      活动ID
     * @param participateType 参与类型（1-发起者、2-助力者）
     * @return List
     */
    List<ActivityTarget> selectByActivityId(@Param("tenantCode") String tenantCode,
                                            @Param("activityId") String activityId,
                                            @Param("participateType") Integer participateType);
}
