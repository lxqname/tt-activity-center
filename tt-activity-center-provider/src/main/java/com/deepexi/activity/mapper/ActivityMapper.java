package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.ActivityH5Dto;
import com.deepexi.activity.domain.dto.ActivityListDto;
import com.deepexi.activity.domain.dto.ActivityQueryDto;
import com.deepexi.activity.domain.eo.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 活动Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 批量逻辑删除
     *
     * @param idList   主键ID列表
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int deleteByIds(@Param("idList") List<String> idList, @Param("updateBy") String updateBy);

    /**
     * 开始活动（活动开始时间小于今天的未开始活动）
     *
     * @return int 影响行数
     */
    int start();

    /**
     * 开始活动（活动开始时间小于今天的未开始活动）
     *
     * @param id       主键ID
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int startById(@Param("id") String id, @Param("updateBy") String updateBy);

    /**
     * 结束活动（未失效活动）
     *
     * @param id       主键ID
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int end(@Param("id") String id, @Param("updateBy") String updateBy);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return List
     */
    List<ActivityListDto> findList(@Param("query") ActivityQueryDto query);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return List
     */
    List<ActivityListDto> findAuditList(@Param("query") ActivityQueryDto query);

    /**
     * 列表查询-特定条件（进行中活动及达到预告时间未开始活动）
     *
     * @param query 查询条件
     * @return List
     */
    List<ActivityH5Dto> findListByProcessingAndPendingStart(@Param("query") ActivityQueryDto query);

    /**
     * 查询已过指定时间的活动列表（状态为进行中）
     *
     * @param date 指定时间
     * @return List
     */
    List<Activity> selectOvertimeActivities(@Param("date") Date date);

    /**
     * 查询发券活动列表（状态为进行中）
     *
     * @return List
     */
    List<Activity> selectIssueCouponActivities();

    /**
     * 查询进行中的新会员活动列表
     *
     * @param tenantCode 租户
     * @return List
     */
    List<Activity> selectProcessingNewMemberActivities(@Param("tenantCode") String tenantCode);

    /**
     * 查询活动时间重叠的活动ID
     *
     * @param tenantCode 租户
     * @param type       活动类型
     * @param startTime  活动开始时间
     * @param endTime    活动结束时间
     * @param statusList 活动状态列表
     * @return List
     */
    List<String> selectOverlappingActivityIdList(@Param("tenantCode") String tenantCode,
                                                 @Param("type") Integer type,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime,
                                                 @Param("statusList") List<Integer> statusList);


    /**
     * 根据类型与渠道查询活动
     *
     * @param typeList
     * @param channelPromotionList
     * @return
     */
    List<Activity> findListByTypeInAndChannelPromotionIn(@Param("typeList") List<Integer> typeList,
                                                         @Param("channelPromotionList") List<Integer> channelPromotionList);


    /**
     * 查询活动市场列表
     * @param channelId
     * @return
     */
    List<Activity> selectActivityMarketList(@Param("channelId") String channelId);
}
