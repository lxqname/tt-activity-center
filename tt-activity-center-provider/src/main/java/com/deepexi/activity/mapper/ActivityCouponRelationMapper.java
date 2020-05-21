package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.ActivityAwardDto;
import com.deepexi.activity.domain.eo.ActivityCouponRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动-优惠券/组合包关系Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface ActivityCouponRelationMapper extends BaseMapper<ActivityCouponRelation> {

    /**
     * 根据活动ID删除
     *
     * @param activityId 活动ID
     * @param updateBy   更新人
     * @return int 影响行数
     */
    int deleteByActivityId(@Param("activityId") String activityId, @Param("updateBy") String updateBy);

    /**
     * 增加数量
     *
     * @param id       主键ID
     * @param num      数量
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int addNum(@Param("id") String id, @Param("num") Integer num, @Param("updateBy") String updateBy);

    /**
     * 减少剩余数量
     *
     * @param id       主键ID
     * @param num      数量
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int subRemainNum(@Param("id") String id, @Param("num") Integer num, @Param("updateBy") String updateBy);

    /**
     * 增加剩余数量
     *
     * @param id       主键ID
     * @param num      数量
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int addRemainNum(@Param("id") String id, @Param("num") Integer num, @Param("updateBy") String updateBy);

    /**
     * 根据活动ID查询列表
     *
     * @param tenantCode 租户
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityAwardDto> selectByActivityId(@Param("tenantCode") String tenantCode,
                                              @Param("activityId") String activityId);

    /**
     * 根据活动ID查询在达标数量最近的一条记录（达标数量为null则查出最大的一条记录）
     *
     * @param tenantCode  租户
     * @param activityId  活动ID
     * @param completeNum 达标数量
     * @return ActivityAwardDto
     */
    ActivityAwardDto selectLatestComplete(@Param("tenantCode") String tenantCode,
                                          @Param("activityId") String activityId,
                                          @Param("completeNum") Integer completeNum);

    /**
     * 根据活动ID查询在未完成的达标数量最低的一条记录（达标数量为null则查出最小的一条记录）
     *
     * @param tenantCode  租户
     * @param activityId  活动ID
     * @param completeNum 达标数量
     * @return ActivityAwardDto
     */
    ActivityAwardDto selectLowestUnComplete(@Param("tenantCode") String tenantCode,
                                            @Param("activityId") String activityId,
                                            @Param("completeNum") Integer completeNum);

    /**
     * 查询类型ID列表
     *
     * @param tenantCode 租户
     * @param type       类型
     * @return List
     */
    List<String> selectTypeIdList(@Param("tenantCode") String tenantCode,
                                  @Param("type") Integer type);

    /**
     * 根据类型及类型ID统计
     *
     * @param tenantCode 租户
     * @param type       类型（1-优惠券、2-优惠券组合包）
     * @param typeId     类型ID
     * @return int
     */
    int countByTypeAndTypeId(@Param("tenantCode") String tenantCode,
                             @Param("type") Integer type,
                             @Param("typeId") String typeId);

    /**
     * 查询活动ID列表
     *
     * @param tenantCode 租户
     * @param typeIdList 类型ID列表
     * @return List
     */
    List<String> selectActivityIdList(@Param("tenantCode") String tenantCode,
                                      @Param("typeIdList") List<String> typeIdList);

    /**
     * 查询会员所完成有奖励的阶梯
     *
     * @param tenantCode 租户
     * @param memberId   会员ID
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityCouponRelation> selectAllCompleteRelation(@Param("tenantCode") String tenantCode,
                                                           @Param("memberId") String memberId,
                                                           @Param("activityId") String activityId);
}
