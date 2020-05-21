package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.ActivityRemindDto;
import com.deepexi.activity.domain.eo.ActivityRemind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动提醒Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface ActivityRemindMapper extends BaseMapper<ActivityRemind> {

    /**
     * 根据会员ID及活动ID逻辑删除
     *
     * @param memberId   会员ID
     * @param activityId 活动ID
     * @param updateBy   更新人
     * @return int 影响行数
     */
    int deleteByMemberIdAndActivityId(@Param("memberId") String memberId, @Param("activityId") String activityId, @Param("updateBy") String updateBy);

    /**
     * 统计
     *
     * @param tenantCode 租户
     * @param activityId 活动ID
     * @param memberId   会员ID
     * @return int
     */
    int count(@Param("tenantCode") String tenantCode, @Param("activityId") String activityId, @Param("memberId") String memberId);

    /**
     * 查询活动ID列表
     *
     * @param tenantCode     租户
     * @param activityIdList 活动ID列表
     * @param memberId       会员ID
     * @return List
     */
    List<String> selectActivityIdList(@Param("tenantCode") String tenantCode,
                                      @Param("activityIdList") List<String> activityIdList,
                                      @Param("memberId") String memberId);

    /**
     * 查询需要提醒的活动（活动开始前十分钟）
     *
     * @return List
     */
    List<ActivityRemindDto> findRemindActivity();
}
