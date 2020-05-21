package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.activity.domain.dto.ActivityH5DetailDto;
import com.deepexi.activity.domain.dto.ActivityH5Dto;
import com.deepexi.activity.domain.dto.ActivityListDto;
import com.deepexi.activity.domain.dto.ActivityQueryDto;
import com.deepexi.activity.domain.dto.ActivitySaveDto;
import com.deepexi.activity.domain.dto.AuditSaveDto;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.domain.eo.MemberActivity;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 活动服务
 *
 * @author 蝈蝈
 */
public interface ActivityService {

    /**
     * 新增
     *
     * @param saveDto 保存数据
     * @return Boolean
     */
    Boolean create(ActivitySaveDto saveDto);

    /**
     * 编辑
     *
     * @param id      主键ID
     * @param saveDto 保存数据
     * @return Boolean
     */
    Boolean update(String id, ActivitySaveDto saveDto);

    /**
     * 更新话题图片URL字段
     *
     * @param id            主键ID
     * @param topicImageUrl 话题图片URL
     */
    void updateTopicImageUrl(String id, String topicImageUrl);

    /**
     * 批量删除
     *
     * @param idList 主键ID列表
     * @return Boolean
     */
    Boolean delete(List<String> idList);

    /**
     * 分页查询-只有activity表数据
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    PageBean<ActivityListDto> findPageWithOnlyActivity(ActivityQueryDto query, Integer page, Integer size);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    PageBean<ActivityListDto> findPage(ActivityQueryDto query, Integer page, Integer size);

    /**
     * 根据ID查询
     *
     * @param id             主键ID
     * @param throwException 查询为null是否抛异常
     * @return eo数据
     */
    Activity selectById(String id, boolean throwException);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return ActivityDetailDto
     */
    ActivityDetailDto detail(String id);

    /**
     * 审核
     *
     * @param id      活动ID
     * @param saveDto 保存数据
     * @return Boolean
     */
    Boolean audit(String id, AuditSaveDto saveDto);

    /**
     * 开始活动（活动开始时间小于今天的待生效活动）
     *
     * @return Integer 影响行数
     */
    Integer start();

    /**
     * 手动开始活动（活动开始时间必须小于当前时间的待生效活动）
     *
     * @param id 活动ID
     * @return Boolean
     */
    Boolean manualStart(String id);

    /**
     * 结束活动（活动结束时间小于今天的进行中活动）
     *
     * @return Integer 影响行数
     */
    Integer end();

    /**
     * 结束活动
     *
     * @param activity    活动数据
     * @return Boolean
     */
    Boolean end(Activity activity);

    /**
     * 手动结束活动（活动结束时间必须小于当前时间的进行中活动）
     *
     * @param id 活动ID
     * @return Boolean
     */
    Boolean manualEnd(String id);

    /**
     * 停止活动
     *
     * @param id 活动ID
     * @return Boolean
     */
    Boolean stop(String id);

    /**
     * 初始化活动状态（影响字段有活动状态、真实开始时间、真实结束时间）
     *
     * @param activity 活动数据
     */
    void initializeActivityStatus(Activity activity);

    /**
     * 分页查询（进行中活动及达到预告时间未开始活动）-会员端使用
     *
     * @param type            活动类型
     * @param frontCategoryId 前端类目ID
     * @param name            名称
     * @param page            页码
     * @param size            页大小
     * @return PageBean
     */
    PageBean<ActivityH5Dto> findH5Page(Integer type, String frontCategoryId, String name, Integer page, Integer size);

    /**
     * 详情-移动端
     *
     * @param id         主键ID
     * @param personType 人员类型（1-商户、2-渠道、3-会员）
     * @return ActivityH5DetailDto
     */
    ActivityH5DetailDto detailForH5(String id, Integer personType);

    /**
     * 发券-定时发放
     *
     * @return Integer 影响行数
     */
    Integer issueCouponOnTime();

    /**
     * 发券
     *
     * @param activityId      活动ID
     * @param applicationType 创建平台
     */
    void issueCoupon(String activityId, Integer applicationType);

    /**
     * 发券
     *
     * @param activityId 活动ID
     * @param memberId   会员ID
     * @return Boolean
     */
    Boolean issueCoupon(String activityId, String memberId);

    /**
     * 补发券
     *
     * @param memberActivityId 会员活动ID
     * @param activityId       活动ID
     * @param memberId         会员ID
     */
    void reissueCoupon(String memberActivityId, String activityId, String memberId);

    /**
     * 新会员-参与新会员活动（获取注册礼包）
     *
     * @param memberId 会员ID
     * @return String
     */
    String newMember(String memberId);

    /**
     * 参加
     *
     * @param id        活动ID
     * @param channelId 渠道ID
     * @return String 会员活动ID
     */
    String participate(String id, String channelId);

    /**
     * 助力
     *
     * @param participantMemberActivityId 参与者会员活动ID
     * @return Boolean
     */
    Boolean boost(String participantMemberActivityId);

    /**
     * 助力
     *
     * @param participantMemberActivityId 参与者会员活动ID
     * @param helperMemberId              助力者会员ID
     * @return Boolean
     */
    Boolean boost(String participantMemberActivityId, String helperMemberId);

    /**
     * 取消助力
     *
     * @param helperMemberId 助力者会员ID
     * @return Boolean
     */
    Boolean cancelBoost(String helperMemberId);

    /**
     * 增加助力人数
     *
     * @param participantMemberActivity 发起者会员活动
     * @param helperMemberId            助力者会员ID
     * @param activity                  活动数据
     * @return Integer 增加后的助力人数
     */
    Integer addBoostNum(MemberActivity participantMemberActivity, String helperMemberId, Activity activity);

    /**
     * 减少助力人数
     *
     * @param participantMemberActivity 发起者会员活动
     * @param boostRecordId             助力记录ID
     * @return Integer 减少后的助力人数
     */
    Integer subBoostNum(MemberActivity participantMemberActivity, String boostRecordId);



    /**
     * 活动审核列表
     * @param query
     * @param page
     * @param size
     * @return
     */
    PageBean findAuditPage(ActivityQueryDto query, Integer page, Integer size);

    /**
     * 商户平台审核
     * @param id
     * @param saveDto
     * @return
     */
    Boolean auditOnBusiness(String id, AuditSaveDto saveDto);
}