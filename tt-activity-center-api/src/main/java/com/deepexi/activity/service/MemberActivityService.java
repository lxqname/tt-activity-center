package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.MemberActivityDto;
import com.deepexi.activity.domain.dto.MemberActivityQueryDto;
import com.deepexi.activity.domain.dto.MemberActivityStatusCountDto;
import com.deepexi.activity.domain.dto.MemberAwardCountDto;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.domain.eo.MemberActivity;
import com.deepexi.util.pageHelper.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员活动服务
 *
 * @author 蝈蝈
 */
public interface MemberActivityService {

    /**
     * 新增
     *
     * @param memberId       会员ID
     * @param activity       活动数据
     * @param shareChannelId 分享者渠道ID
     * @return String 新增后的主键ID
     */
    String create(String memberId, Activity activity, String shareChannelId);

    /**
     * 新增
     *
     * @param memberId     会员ID
     * @param activityId   活动ID
     * @param activityType 活动类型
     * @param finish       是否已完成
     * @return String 新增后的主键ID
     */
    String create(String memberId, String activityId, Integer activityType, boolean finish);

    /**
     * 根据ID查询
     *
     * @param id             主键ID
     * @param throwException 查询为null是否抛异常
     * @return MemberActivity
     */
    MemberActivity selectById(String id, boolean throwException);

    /**
     * 统计
     *
     * @param activityId 活动ID
     * @param memberId   会员ID
     * @return Integer
     */
    Integer count(String activityId, String memberId);

    /**
     * 校验会员活动有效性（有效则返回会员活动信息）
     *
     * @param id 会员活动ID
     * @return MemberActivity
     */
    MemberActivity checkMemberActivityEffectiveness(String id);

    /**
     * 增加助力人数
     *
     * @param id  主键ID
     * @param num 数量
     * @return Integer 增加后的助力人数
     */
    Integer addBoostNum(String id, Integer num);

    /**
     * 减少助力人数
     *
     * @param id  主键ID
     * @param num 数量
     * @return Integer 减少后的助力人数
     */
    Integer subBoostNum(String id, Integer num);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return MemberActivityDto
     */
    MemberActivityDto detail(String id);

    /**
     * 会员参与活动详情（没有参与则返回null）
     *
     * @param activityId 活动ID
     * @param memberId   会员ID
     * @return MemberActivityDto
     */
    MemberActivityDto participateDetail(String activityId, String memberId);

    /**
     * 会员助力过的活动详情（没有助力过则返回null）
     *
     * @param memberId 会员ID
     * @return MemberActivityDto
     */
    MemberActivityDto boostDetail(String memberId);

    /**
     * 结束会员活动（超过有效期的进行中会员活动）
     *
     * @return List 会员活动ID列表
     */
    List<String> end();

    /**
     * 根据活动ID结束会员活动（进行中会员活动）
     *
     * @param activityId 活动ID
     */
    void endByActivityId(String activityId);

    /**
     * 手动结束会员活动
     *
     * @param id 会员活动ID
     * @return Boolean
     */
    Boolean manualEnd(String id);

    /**
     * 结束（进行中会员活动）-成功
     *
     * @param id       主键ID
     */
    void endToSuccess(@Param("id") String id);

    /**
     * 列表查询-仅包含MemberActivity数据
     *
     * @param query 查询条件
     * @return PageBean
     */
    List<MemberActivityDto> findListWithOnlyEo(MemberActivityQueryDto query);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    PageBean<MemberActivityDto> findPage(MemberActivityQueryDto query, Integer page, Integer size);

    /**
     * 我的参与中活动列表(包含发起与助力)
     *
     * @param statusList 状态列表
     * @param page       页码
     * @param size       页大小
     * @return PageBean
     */
    PageBean<MemberActivityDto> findPageForH5(List<Integer> statusList, Integer page, Integer size);

    /**
     * 奖品统计
     *
     * @param activityId   活动ID
     * @param memberMobile 会员手机号
     * @param page         页码
     * @param size         页大小
     * @return PageBean
     */
    PageBean<MemberAwardCountDto> countAward(String activityId, String memberMobile, Integer page, Integer size);

    /**
     * 会员统计
     *
     * @param activityId 活动ID
     * @return Integer
     */
    Integer countMember(String activityId);

    /**
     * 根据状态统计数量
     *
     * @param activityId 活动ID
     * @return MemberActivityStatusCountDto
     */
    MemberActivityStatusCountDto countByStatus(String activityId);

    /**
     * 统计我的活动数据
     * @param query
     * @return
     */
    Integer countByMemberActivityQueryDto(MemberActivityQueryDto query);
}