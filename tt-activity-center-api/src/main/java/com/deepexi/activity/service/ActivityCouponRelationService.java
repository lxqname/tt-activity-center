package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityAwardDto;
import com.deepexi.activity.domain.dto.ActivityAwardSaveDto;
import com.deepexi.activity.domain.dto.ActivityListDto;
import com.deepexi.activity.domain.eo.ActivityCouponRelation;

import java.util.List;

/**
 * 活动-优惠券/组合包关系服务
 *
 * @author 蝈蝈
 */
public interface ActivityCouponRelationService {

    /**
     * 新增
     *
     * @param activityId 活动ID
     * @param saveDto    保存数据
     */
    void create(String activityId, ActivityAwardSaveDto saveDto);

    /**
     * 新增
     *
     * @param activityId  活动ID
     * @param saveDtoList 保存数据列表
     */
    void create(String activityId, List<ActivityAwardSaveDto> saveDtoList);

    /**
     * 增加数量
     *
     * @param id  主键ID
     * @param num 数量
     */
    void addNum(String id, Integer num);

    /**
     * 根据活动ID删除
     *
     * @param activityId 活动ID
     */
    void deleteByActivityId(String activityId);

    /**
     * 根据ID选择
     *
     * @param id 主键ID
     * @return ActivityCouponRelation
     */
    ActivityCouponRelation selectById(String id);

    /**
     * 填充关联奖品信息
     *
     * @param list 数据
     */
    void fillUpAwardInfo(List<ActivityListDto> list);

    /**
     * 返还优惠券数量
     *
     * @param activityId 活动ID
     */
    void returnCouponNum(String activityId);

    /**
     * 返还权益金额
     *
     * @param activityId 活动ID
     */
    void returnEquityAmount(String activityId);

    /**
     * 返还权益金额
     *
     * @param type   类型（1-优惠券、2-优惠券组合包）
     * @param typeId 类型ID
     * @param num    数量
     */
    void returnEquityAmount(Integer type, String typeId, Integer num);

    /**
     * 获取奖品列表
     *
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityAwardDto> getAwardList(String activityId);

    /**
     * 获取奖品列表-包含优惠券信息
     *
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityAwardDto> getAwardListWithCouponInfo(String activityId);

    /**
     * 查询类型ID列表
     *
     * @param type 类型（1-优惠券、2-优惠券组合包）
     * @return List
     */
    List<String> findTypeIdList(Integer type);

    /**
     * 根据类型及类型ID统计
     *
     * @param type   类型（1-优惠券、2-优惠券组合包）
     * @param typeId 类型ID
     * @return Integer
     */
    Integer countByTypeAndTypeId(Integer type, String typeId);

    /**
     * 查询活动ID列表
     *
     * @param typeIdList 类型ID列表（1-优惠券、2-优惠券组合包）
     * @return List
     */
    List<String> findActivityIdList(List<String> typeIdList);

    /**
     * 领取
     *
     * @param memberId   会员ID
     * @param activityId 活动ID
     */
    void receive(String memberId, String activityId);

    /**
     * 减少剩余数量
     *
     * @param id  主键ID
     * @param num 数量
     * @return Integer 影响行数
     */
    Integer subRemainNum(String id, Integer num);

    /**
     * 增加剩余数量
     *
     * @param id  主键ID
     * @param num 数量
     * @return Integer 影响行数
     */
    Integer addRemainNum(String id, Integer num);

    /**
     * 查询最新完成的一个关系
     *
     * @param activityId  活动ID
     * @param completeNum 已完成数量
     * @return ActivityAwardDto
     */
    ActivityAwardDto queryLatestCompleteRelation(String activityId, Integer completeNum);

    /**
     * 查询达标数量最高的一个关系
     *
     * @param activityId  活动ID
     * @return ActivityAwardDto
     */
    ActivityAwardDto queryTopRelation(String activityId);

    /**
     * 查询达标数量最低的未完成的一个关系
     *
     * @param activityId  活动ID
     * @param completeNum 已完成数量
     * @return ActivityAwardDto
     */
    ActivityAwardDto queryLowestUnCompleteRelation(String activityId, Integer completeNum);

    /**
     * 查询类型名称
     *
     * @param type   类型（1-优惠券、2-优惠券组合包）
     * @param typeId 类型ID
     * @return String
     */
    String queryTypeName(Integer type, String typeId);

    /**
     * 查询会员完成的所有阶梯（已获得奖励的阶梯）
     *
     * @param memberId   会员ID
     * @param activityId 活动ID
     * @return List
     */
    List<ActivityCouponRelation> findAllCompleteRelation(String memberId, String activityId);
}