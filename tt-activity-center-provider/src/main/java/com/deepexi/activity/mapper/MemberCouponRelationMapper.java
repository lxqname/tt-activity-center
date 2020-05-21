package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.IdNumDto;
import com.deepexi.activity.domain.dto.MemberCouponAndPackageDto;
import com.deepexi.activity.domain.dto.MemberCouponH5Dto;
import com.deepexi.activity.domain.dto.MemberCouponRelationListDto;
import com.deepexi.activity.domain.dto.MemberCouponRelationQueryDto;
import com.deepexi.activity.domain.eo.MemberCouponRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员优惠券关系Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface MemberCouponRelationMapper extends BaseMapper<MemberCouponRelation> {

    /**
     * 领取
     *
     * @param idList   主键ID列表
     * @param memberId 会员ID
     * @param updateBy 更新人
     * @return int
     */
    int receive(@Param("idList") List<String> idList, @Param("memberId") String memberId, @Param("updateBy") String updateBy);

    /**
     * 核销
     *
     * @param id       主键ID
     * @param storeId  门店ID
     * @param updateBy 更新人
     * @return int
     */
    int use(@Param("id") String id, @Param("storeId") String storeId, @Param("updateBy") String updateBy);

    /**
     * 更新预定状态
     *
     * @param id                 主键ID
     * @param verificationStatus 核销状态
     * @param updateBy           更新人
     * @return int
     */
    int updateReservation(@Param("id") String id, @Param("verificationStatus") Integer verificationStatus, @Param("updateBy") String updateBy);

    /**
     * 过期（未使用状态优惠券）
     *
     * @param id       主键ID
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int expire(@Param("id") String id, @Param("updateBy") String updateBy);

    /**
     * 批量逻辑删除
     *
     * @param idList   主键ID列表
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int deleteByIds(@Param("idList") List<String> idList, @Param("updateBy") String updateBy);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return List
     */
    List<MemberCouponRelationListDto> findList(@Param("query") MemberCouponRelationQueryDto query);

    /**
     * ID列表查询
     *
     * @param query 查询条件
     * @return List
     */
    List<String> findIdList(@Param("query") MemberCouponRelationQueryDto query);

    /**
     * 统计
     *
     * @param query 查询条件
     * @return int
     */
    int count(@Param("query") MemberCouponRelationQueryDto query);

    /**
     * 查询活动产生的奖励列表
     *
     * @param query 查询条件
     * @return List
     */
    List<MemberCouponAndPackageDto> selectActivityAwardList(@Param("query") MemberCouponRelationQueryDto query);

    /**
     * 根据活动ID及核销状态统计数量
     *
     * @param tenantCode         租户
     * @param activityId         活动ID
     * @param verificationStatus 核销状态
     * @return List [优惠券ID，数量]
     */
    List<IdNumDto> countNumByStatus(@Param("tenantCode") String tenantCode, @Param("activityId") String activityId, @Param("verificationStatus") Integer verificationStatus);

    /**
     * 查询我的优惠券列表
     *
     * @param query 查询条件
     * @return List
     */
    List<MemberCouponH5Dto> findMyCouponList(@Param("query") MemberCouponRelationQueryDto query);

    /**
     * 查询即将过期会员优惠券列表
     *
     * @return List
     */
    List<MemberCouponRelation> findExpireSoonList();

    /**
     * 查询过期会员优惠券列表-状态为未使用
     *
     * @return List
     */
    List<MemberCouponRelation> selectUnusedExpiredList();
}
