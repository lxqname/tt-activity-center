package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.MemberActivityDto;
import com.deepexi.activity.domain.dto.MemberActivityQueryDto;
import com.deepexi.activity.domain.dto.MemberAwardCountDto;
import com.deepexi.activity.domain.eo.MemberActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员活动Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface MemberActivityMapper extends BaseMapper<MemberActivity> {

    /**
     * 增加助力人数
     *
     * @param id       主键ID
     * @param num      数量
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int addBoostNum(@Param("id") String id, @Param("num") Integer num, @Param("updateBy") String updateBy);

    /**
     * 减少助力人数
     *
     * @param id       主键ID
     * @param num      数量
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int subBoostNum(@Param("id") String id, @Param("num") Integer num, @Param("updateBy") String updateBy);

    /**
     * 结束（进行中会员活动）-成功
     *
     * @param id       主键ID
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int endToSuccess(@Param("id") String id, @Param("updateBy") String updateBy);

    /**
     * 结束（进行中会员活动）-失败
     *
     * @param id       主键ID
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int endToFail(@Param("id") String id, @Param("updateBy") String updateBy);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return List
     */
    List<MemberActivityDto> findList(@Param("query") MemberActivityQueryDto query);

    /**
     * 列表查询-EO数据
     *
     * @param query 查询条件
     * @return List
     */
    List<MemberActivity> findEoList(@Param("query") MemberActivityQueryDto query);

    /**
     * 统计
     *
     * @param query 查询条件
     * @return int
     */
    int count(@Param("query") MemberActivityQueryDto query);

    /**
     * 查询过期会员活动列表
     *
     * @return List
     */
    List<MemberActivity> selectOvertimeList();

    /**
     * 奖品统计
     *
     * @param tenantCode   租户
     * @param activityId   活动ID
     * @param memberIdList 会员ID列表
     * @return List
     */
    List<MemberAwardCountDto> countAward(@Param("tenantCode") String tenantCode,
                                         @Param("activityId") String activityId,
                                         @Param("memberIdList") List<String> memberIdList);

    /**
     * 统计会员数
     *
     * @param tenantCode 租户
     * @param activityId 会员ID
     * @return int
     */
    int countMember(@Param("tenantCode") String tenantCode,
                    @Param("activityId") String activityId);

    /**
     * 统计我的活动
     * @param query
     * @return
     */
    Integer countByMemberActivityQueryDto(@Param("queryDto") MemberActivityQueryDto query);
}
