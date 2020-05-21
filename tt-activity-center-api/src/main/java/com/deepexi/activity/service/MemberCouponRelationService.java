package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.MemberCouponAndPackageDto;
import com.deepexi.activity.domain.dto.MemberCouponH5DetailDto;
import com.deepexi.activity.domain.dto.MemberCouponH5Dto;
import com.deepexi.activity.domain.dto.MemberCouponRelationListDto;
import com.deepexi.activity.domain.dto.MemberCouponRelationQueryDto;
import com.deepexi.activity.domain.dto.VerificationStatusCountDto;
import com.deepexi.activity.domain.eo.MemberCouponRelation;
import com.deepexi.util.pageHelper.PageBean;

import java.util.Date;
import java.util.List;

/**
 * 会员优惠券服务
 *
 * @author 蝈蝈
 */
public interface MemberCouponRelationService {

    /**
     * 新增-优惠券
     *
     * @param memberId                 会员ID
     * @param couponId                 优惠券ID
     * @param activityCouponRelationId 活动优惠券关系ID
     * @param activityId               活动ID
     * @param verificationStatus       核销状态
     * @param receiveType              领取方式
     */
    void createByCoupon(String memberId, String couponId, String activityCouponRelationId, String activityId, Integer verificationStatus, Integer receiveType);

    /**
     * 新增-优惠券组合包
     *
     * @param memberId                 会员ID
     * @param couponPackageId          优惠券组合包ID
     * @param activityCouponRelationId 活动优惠券关系ID
     * @param activityId               活动ID
     * @param verificationStatus       核销状态
     * @param receiveType              领取方式
     */
    void createByCouponPackage(String memberId, String couponPackageId, String activityCouponRelationId, String activityId, Integer verificationStatus, Integer receiveType);

    /**
     * 新增-交易使用
     *
     * @param memberId           会员ID
     * @param type               关联类型（1-优惠券、2-优惠券组合包）
     * @param typeId             类型ID
     * @param productId          商品ID
     * @param orderId            订单ID
     * @param orderAwardId       订单奖品ID
     * @param verificationStatus 核销状态
     * @param receiveType        领取方式
     */
    void createForTrade(String memberId, Integer type, String typeId, String productId, String orderId, String orderAwardId, Integer verificationStatus, Integer receiveType);

    /**
     * 数据更新入库
     *
     * @param memberCouponRelation 数据
     */
    Boolean updateById(MemberCouponRelation memberCouponRelation);

    /**
     * 批量删除
     *
     * @param idList 主键ID列表
     * @return Boolean
     */
    Boolean delete(List<String> idList);

    /**
     * 统计
     *
     * @param query 查询条件
     * @return Integer
     */
    Integer count(MemberCouponRelationQueryDto query);

    /**
     * 查询当前会员参与活动产生的奖励列表
     *
     * @param activityId 活动ID
     * @return List
     */
    List<MemberCouponAndPackageDto> findActivityAwardList(String activityId);

    /**
     * 分页查询会员奖品
     *
     * @param page 页码
     * @param size 页大小
     * @return PageBean
     */
    PageBean<MemberCouponAndPackageDto> findAwardPage(Integer page, Integer size);

    /**
     * 领取
     *
     * @param idList ID列表
     * @return Boolean
     */
    Boolean receive(List<String> idList);

    /**
     * 根据领取类型更新预定核销状态
     *
     * @param activityId  活动ID
     * @param memberId    会员ID，为null则更新全部会员的
     */
    void updateReservationByReceiveType(String activityId, String memberId);

    /**
     * 列表查询-仅包含MemberCouponRelation数据
     *
     * @param query 查询条件
     * @return List
     */
    List<MemberCouponRelationListDto> findAllWithOnlyEo(MemberCouponRelationQueryDto query);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    PageBean<MemberCouponRelationListDto> findPage(MemberCouponRelationQueryDto query, Integer page, Integer size);

    /**
     * 根据核销状态统计数量及金额
     *
     * @param activityId 活动ID
     * @return VerificationStatusCountDto
     */
    VerificationStatusCountDto countNumAndAmountByStatus(String activityId);

    /**
     * 直接发送优惠卷
     * @param memberId 会员ID
     * @param couponId 优惠卷ID
     * @return
     */
    Boolean sendCoupon(String memberId, String couponId);

    /**
     * 我的优惠券列表-分页查询
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    PageBean<MemberCouponH5Dto> findMyCoupon(MemberCouponRelationQueryDto query, Integer page, Integer size);

    /**
     * 我的优惠券-详情
     *
     * @param id 会员优惠券关系ID
     * @return MemberCouponH5DetailDto
     */
    MemberCouponH5DetailDto myCouponDetail(String id);

    /**
     * 校验当前门店是否为可核销门店
     *
     * @param id      会员优惠券关系ID
     * @param storeId 门店ID
     * @return Boolean
     */
    Boolean checkUseStore(String id, String storeId);

    /**
     * 核销
     *
     * @param id      会员优惠券关系ID
     * @param storeId 门店ID
     * @return Boolean
     */
    Boolean use(String id, String storeId);

    /**
     * 分页查询-核销记录
     *
     * @param startTime 开始时间-范围查询
     * @param endTime   结束时间-范围查询
     * @param page      页码
     * @param size      页大小
     * @return PageBean
     */
    PageBean<MemberCouponRelationListDto> findVerificationRecordPage(Date startTime, Date endTime, Integer page, Integer size);

    /**
     * 详情-商户端
     *
     * @param id 会员优惠券关系ID
     * @return MemberCouponH5Dto
     */
    MemberCouponH5Dto detailForMerchant(String id);

    /**
     * 优惠券过期提醒
     *
     * @return Integer 提醒行数
     */
    Integer couponExpireSoonRemind();

    /**
     * 过期操作
     *
     * @return Integer 影响数
     */
    Integer expire();

    /**
     * 过期操作
     *
     * @param expiredMemberCoupon 过期会员优惠券信息
     * @return Boolean
     */
    Boolean expire(MemberCouponRelation expiredMemberCoupon);

    /**
     * 计算优惠券过期时间
     *
     * @param couponUseTimeFlag 优惠券使用时间类型
     * @param couponUseEndTime  优惠券使用结束时间
     * @param couponValidDay    优惠券有效天数
     * @param receiveTime       领取时间
     * @return Date
     */
    Date calculateCouponExpireTime(Integer couponUseTimeFlag, Date couponUseEndTime, Integer couponValidDay, Date receiveTime);

    /**
     * 查询会员优惠券关系主键ID列表
     *
     * @param activityId 活动ID
     * @return List
     */
    List<String> findIdList(String activityId);
}