package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityAwardDto;
import com.deepexi.activity.domain.dto.ActivityAwardSaveDto;
import com.deepexi.activity.domain.dto.ActivityConditionDto;
import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.activity.domain.dto.ActivityH5DetailDto;
import com.deepexi.activity.domain.dto.ActivityH5Dto;
import com.deepexi.activity.domain.dto.ActivityListDto;
import com.deepexi.activity.domain.dto.ActivityQueryDto;
import com.deepexi.activity.domain.dto.ActivitySaveDto;
import com.deepexi.activity.domain.dto.AuditSaveDto;
import com.deepexi.activity.domain.dto.BoostRecordDto;
import com.deepexi.activity.domain.dto.IdNameDto;
import com.deepexi.activity.domain.dto.MemberActivityDto;
import com.deepexi.activity.domain.dto.MemberActivityQueryDto;
import com.deepexi.activity.domain.dto.MemberCouponRelationListDto;
import com.deepexi.activity.domain.dto.MemberCouponRelationQueryDto;
import com.deepexi.activity.domain.dto.TargetDto;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.domain.eo.ActivityCouponRelation;
import com.deepexi.activity.domain.eo.ActivityOrganization;
import com.deepexi.activity.domain.eo.MemberActivity;
import com.deepexi.activity.enums.*;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityMapper;
import com.deepexi.activity.service.ActivityAuditHistoryService;
import com.deepexi.activity.service.ActivityChannelService;
import com.deepexi.activity.service.ActivityCouponRelationService;
import com.deepexi.activity.service.ActivityOrganizationService;
import com.deepexi.activity.service.ActivityRemindService;
import com.deepexi.activity.service.ActivityService;
import com.deepexi.activity.service.ActivityTargetService;
import com.deepexi.activity.service.BoostRecordService;
import com.deepexi.activity.service.MemberActivityService;
import com.deepexi.activity.service.MemberCouponRelationService;
import com.deepexi.activity.service.MemberEnterpriseService;
import com.deepexi.activity.service.MemberService;
import com.deepexi.activity.service.MessageService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.activity.utils.DingDingBot;
import com.deepexi.activity.utils.PageBeanUtils;
import com.deepexi.business.domain.vo.LoginBusinessInfoVo;
import com.deepexi.business.service.BusinessAccountService;
import com.deepexi.business.service.EnterprService;
import com.deepexi.common.enums.PlatformTypeEnum;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.enums.YesNoEnum;
import com.deepexi.equity.enums.ApplicationTypeEnum;
import com.deepexi.equity.enums.ConnectTypeEnum;
import com.deepexi.equity.service.CategoryConnectRelationService;
import com.deepexi.equity.service.TagConnectRelationService;
import com.deepexi.member.api.WechatMemberInfoService;
import com.deepexi.member.domain.eo.Member;
import com.deepexi.member.enums.MemberRankEnum;
import com.deepexi.product.service.CategoryRelationService;
import com.deepexi.product.service.FrontCategoryService;
import com.deepexi.system.domain.eo.MerchantSystemConfig;
import com.deepexi.system.domain.eo.SystemConfig;
import com.deepexi.system.service.AllConfigService;
import com.deepexi.system.service.MerchantAllConfigService;
import com.deepexi.user.domain.eo.Account;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.boot.autoconfigure.klock.model.LockTimeoutStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 活动服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class ActivityServiceImpl implements ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityAuditHistoryService activityAuditHistoryService;

    @Resource
    private ActivityCouponRelationService activityCouponRelationService;

    @Resource
    private ActivityTargetService activityTargetService;

    @Resource
    private ActivityChannelService activityChannelService;

    @Resource
    private ActivityRemindService activityRemindService;

    @Resource
    private MemberActivityService memberActivityService;

    @Resource
    private BoostRecordService boostRecordService;

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Resource
    private MemberService memberService;

    @Resource
    private ActivityOrganizationService activityOrganizationService;

    @Resource
    private MessageService messageService;

    @Resource
    private MemberEnterpriseService memberEnterpriseService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Reference(version = "${demo.service.version}", check = false)
    private AllConfigService allConfigService;

    @Reference(version = "${demo.service.version}", check = false)
    private FrontCategoryService frontCategoryService;

    @Reference(version = "${demo.service.version}", check = false)
    private CategoryRelationService categoryRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private CategoryConnectRelationService categoryConnectRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private TagConnectRelationService tagConnectRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private WechatMemberInfoService wechatMemberInfoService;

    @Reference(version = "${demo.service.version}", check = false)
    private EnterprService enterprService;

    @Reference(version = "${demo.service.version}", check = false)
    private MerchantAllConfigService merchantAllConfigService;

    @Reference(version = "${demo.service.version}", check = false)
    private BusinessAccountService businessAccountService;




    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(ActivitySaveDto saveDto) {
        // 校验平台类型合法性
        this.validateApplicationType(saveDto.getApplicationType());
        // 校验参数合法性
        this.validateInfo(saveDto);

        //插入活动数据
        Activity activity = new Activity();
        activity.copy(saveDto);
        // 为了使发券活动发券时间查询能查到未开始的活动，默认设置真实开始时间等于开始时间
        activity.setRealStartTime(activity.getStartTime());
        activity.setApplicationType(saveDto.getApplicationType());
        // 初始化活动状态
        this.initializeActivityStatus(activity);
        // 新会员活动校验是否存在重叠活动
        this.checkOverlapping(null, activity.getType(), activity.getStartTime(), activity.getEndTime(), activity.getStatus());
        this.insert(activity);

        // 保存活动与奖品关系
        activityCouponRelationService.create(activity.getId(), saveDto.getAwardList());

        if (saveDto.getTargetType() != null) {
            // 保存活动对象
            activityTargetService.create(activity.getId(), ParticipateTypeEnum.PARTICIPATE.getState(), saveDto.getTargetType(), saveDto.getTargetIdList());
        }
        if (saveDto.getParticipateCondition() != null) {
            // 保存发起人条件
            activityTargetService.create(activity.getId(), ParticipateTypeEnum.PARTICIPATE.getState(), saveDto.getParticipateCondition());
        }
        if (saveDto.getHelperCondition() != null) {
            // 保存助力人条件
            activityTargetService.create(activity.getId(), ParticipateTypeEnum.HELPER.getState(), saveDto.getHelperCondition());
        }
        if (Objects.equals(ChannelPromotionTypeEnum.ALLOW_PART.getState(), saveDto.getChannelPromotion())) {
            // 保存推广渠道关系
            activityChannelService.create(activity.getId(), saveDto.getChannelIdList());
        }
        if (Objects.equals(ActivityApplicationTypeEnum.BUSINESS_PLATFORM.getState(), saveDto.getApplicationType())) {
            //若创建平台是商户平台, 保存 活动-组织关系
            activityOrganizationService.create(activity.getId());
        }
        // 发券活动发券操作
        if (Objects.equals(ActivityTypeEnum.ISSUE_COUPON_ACTIVITY.getState(), activity.getType())
                && Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            this.issueCoupon(activity.getId(), activity.getApplicationType());
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(String id, ActivitySaveDto saveDto) {
        Activity activity = this.selectById(id, true);
        if (Objects.equals(ActivityStatusEnum.DRAFT.getState(), activity.getStatus())
                || Objects.equals(ActivityStatusEnum.REJECT.getState(), activity.getStatus())
                || Objects.equals(ActivityStatusEnum.BUSINESS_REJECT.getState(), activity.getStatus())) {
            // 原活动状态为草稿或驳回状态，允许编辑所有项
            this.updateAll(activity, saveDto);
        } else if (Objects.equals(ActivityStatusEnum.PENDING_START.getState(), activity.getStatus())
                || Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            // 原活动状态为待生效或进行中
            if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activity.getType())) {
                // 只允许增加奖品数量、延后活动结束时间
                this.extendEndTimeAndAddAwardNum(activity, saveDto.getEndTime(), saveDto.getAwardList());
            } else if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activity.getType())) {
                // 只允许增加奖品数量、延后活动结束时间、变更活动缩略图、发起者活动海报、助力者活动海报以及修改助力说明
                activity.setThumbnailUrl(saveDto.getThumbnailUrl());
                activity.setImageUrl(saveDto.getImageUrl());
                activity.setBoostImageUrl(saveDto.getBoostImageUrl());
                activity.setBoostDescription(saveDto.getBoostDescription());
                this.extendEndTimeAndAddAwardNum(activity, saveDto.getEndTime(), saveDto.getAwardList());
            } else if (Objects.equals(ActivityTypeEnum.NEW_MEMBER_ACTIVITY.getState(), activity.getType())) {
                // 只允许增加奖品数量、延后活动结束时间
                this.extendEndTimeAndAddAwardNum(activity, saveDto.getEndTime(), saveDto.getAwardList());
                // 补充发放礼品
                this.reissueCoupon(id);
            } else {
                logger.error("ActivityServiceImpl.update：活动类型[{}]异常", activity.getType());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动类型异常"));
            }
        } else {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.DATABASE_ERROR, "当前状态为"
                    + ActivityStatusEnum.getMsgByState(activity.getStatus()) + "，不允许编辑"));
        }
        return Boolean.TRUE;
    }

    @Override
    public void updateTopicImageUrl(String id, String topicImageUrl) {
        Activity activity = this.selectById(id, true);
        if (StringUtils.isBlank(topicImageUrl)) {
            topicImageUrl = "";
        }
        activity.setTopicImageUrl(topicImageUrl);
        this.updateById(activity);
    }

    /**
     * 更新全部信息项
     *
     * @param activity 数据库活动信息
     * @param saveDto  保存数据
     */
    private void updateAll(Activity activity, ActivitySaveDto saveDto) {
        // 校验参数合法性
        this.validateInfo(saveDto);

        //插入活动数据
        activity.copy(saveDto);
        // 为了使发券活动发券时间查询能查到未开始的活动，默认设置真实开始时间等于开始时间
        activity.setRealStartTime(activity.getStartTime());
        // 初始化活动状态
        this.initializeActivityStatus(activity);
        // 新会员活动校验是否存在重叠活动
        this.checkOverlapping(activity.getId(), activity.getType(), activity.getStartTime(), activity.getEndTime(), activity.getStatus());
        this.updateById(activity);

        // 删除活动与奖品关系
        activityCouponRelationService.deleteByActivityId(activity.getId());
        // 保存活动与奖品关系
        activityCouponRelationService.create(activity.getId(), saveDto.getAwardList());

        // 删除活动对象
        activityTargetService.deleteByActivityId(activity.getId());
        if (saveDto.getTargetType() != null) {
            // 保存活动对象
            activityTargetService.create(activity.getId(), ParticipateTypeEnum.PARTICIPATE.getState(), saveDto.getTargetType(), saveDto.getTargetIdList());
        }
        if (saveDto.getParticipateCondition() != null) {
            // 保存发起人条件
            activityTargetService.create(activity.getId(), ParticipateTypeEnum.PARTICIPATE.getState(), saveDto.getParticipateCondition());
        }
        if (saveDto.getHelperCondition() != null) {
            // 保存助力人条件
            activityTargetService.create(activity.getId(), ParticipateTypeEnum.HELPER.getState(), saveDto.getHelperCondition());
        }
        //删除活动组织关系
        activityOrganizationService.deleteByActivityId(activity.getId());
        if (Objects.equals(ActivityApplicationTypeEnum.BUSINESS_PLATFORM.getState(), saveDto.getApplicationType())) {
            //若创建平台是商户平台, 保存 活动-组织关系
            activityOrganizationService.create(activity.getId());
        }

        // 删除推广渠道关系
        activityChannelService.deleteByActivityId(activity.getId());
        if (Objects.equals(ChannelPromotionTypeEnum.ALLOW_PART.getState(), saveDto.getChannelPromotion())) {
            // 保存推广渠道关系
            activityChannelService.create(activity.getId(), saveDto.getChannelIdList());
        }

        // 发券活动发券操作
        if (Objects.equals(ActivityTypeEnum.ISSUE_COUPON_ACTIVITY.getState(), activity.getType())
                && Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            this.issueCoupon(activity.getId(), activity.getApplicationType());
        }
    }

    /**
     * 延长结束时间、增加奖品数量
     *
     * @param activity  活动数据
     * @param endTime   结束时间
     * @param awardList 奖品列表
     */
    private void extendEndTimeAndAddAwardNum(Activity activity, Date endTime, List<ActivityAwardSaveDto> awardList) {
        if (endTime == null || activity.getEndTime().after(endTime)) {
            logger.error("ActivityServiceImpl.extendEndTimeAndAddAwardNum：活动原结束时间[{}]比入参结束时间[{}]晚", activity.getEndTime(), endTime);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动结束时间不可比原活动结束时间早"));
        } else {
            // 延后活动结束时间
            activity.setEndTime(endTime);
            // 新会员活动校验是否存在重叠活动
            this.checkOverlapping(activity.getId(), activity.getType(), activity.getStartTime(), activity.getEndTime(), activity.getStatus());
            this.updateById(activity);
        }

        if (CollectionUtil.isNotEmpty(awardList)) {
            // 增加奖品数量
            for (ActivityAwardSaveDto awardSaveDto : awardList) {
                ActivityCouponRelation activityCouponRelation = activityCouponRelationService.selectById(awardSaveDto.getId());
                if (activityCouponRelation.getNum() > awardSaveDto.getNum()) {
                    logger.error("ActivityServiceImpl.extendEndTimeAndAddAwardNum：当前入参奖品数量[{}]不可少于原有奖品数量[{}]，活动奖品ID为[{}]",
                            awardSaveDto.getNum(), activityCouponRelation.getNum(), activityCouponRelation.getId());
                    throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "奖励" +
                            awardSaveDto.getNum() + "奖品数量不可少于" + activityCouponRelation.getNum()));
                } else if (activityCouponRelation.getNum() < awardSaveDto.getNum()) {
                    int addNum = awardSaveDto.getNum() - activityCouponRelation.getNum();
                    activityCouponRelationService.addNum(activityCouponRelation.getId(), addNum);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<String> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动删除入参为null"));
        }
        for (String id : idList) {
            Activity activity = this.selectById(id, true);
            if (!Objects.equals(ActivityStatusEnum.DRAFT.getState(), activity.getStatus())
                    && !Objects.equals(ActivityStatusEnum.REJECT.getState(), activity.getStatus())) {
                // 只能删除草稿或已驳回活动
                logger.error("ActivityServiceImpl.delete：活动[{}]当前状态[{}]不允许删除", id, activity.getStatus());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动当前状态不允许删除"));
            }

            // 删除活动与奖品关系
            activityCouponRelationService.deleteByActivityId(id);
            // 删除活动对象
            activityTargetService.deleteByActivityId(id);
            // 删除推广渠道关系
            activityChannelService.deleteByActivityId(activity.getId());
        }
        int deleteResult = activityMapper.deleteByIds(idList, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (deleteResult != idList.size()) {
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        return Boolean.TRUE;
    }

    @Override
    public PageBean<ActivityListDto> findPageWithOnlyActivity(ActivityQueryDto query, Integer page, Integer size) {
        query.setTenantCode(appRuntimeEnv.getTenantId());
        Account account = accountService.getLoginAccountByToken(appRuntimeEnv.getToken());
        if(Objects.equals(account.getPlatformType(), PlatformTypeEnum.INTERNAL_ACCOUNT.getValue())){
            query.setStatusList(Lists.newArrayList(ActivityStatusEnum.DRAFT.getState(),
                    ActivityStatusEnum.PENDING_REVIEW.getState(),
                    ActivityStatusEnum.PENDING_START.getState(),
                    ActivityStatusEnum.PROCESSING.getState(),
                    ActivityStatusEnum.INVALID.getState(),
                    ActivityStatusEnum.REJECT.getState()));
        }
        PageHelper.startPage(page, size);
        List<ActivityListDto> list = activityMapper.findList(query);
        return new PageBean<>(list);
    }

    @Override
    public PageBean<ActivityListDto> findPage(ActivityQueryDto query, Integer page, Integer size) {
        PageBean<ActivityListDto> pageBean = this.findPageWithOnlyActivity(query, page, size);
        List<ActivityListDto> list = pageBean.getContent();
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        activityCouponRelationService.fillUpAwardInfo(list);
        // 查询活动对象
        activityTargetService.fillUpTargetInfo(list);
        return pageBean;
    }

    @Override
    public Activity selectById(String id, boolean throwException) {
        Activity activity = activityMapper.selectById(id);
        if (activity == null && throwException) {
            logger.error("ActivityServiceImpl.selectById：查询活动数据失败，ID为[{}]", id);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询活动数据失败"));
        }
        return activity;
    }

    @Override
    public ActivityDetailDto detail(String id) {
        Activity activity = this.selectById(id, true);
        ActivityDetailDto response = new ActivityDetailDto();
        response.copy(activity);

        // 查询奖品明细
        response.setAwardList(activityCouponRelationService.getAwardListWithCouponInfo(activity.getId()));

        // 查询活动对象
        if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activity.getType())) {
            response.setParticipateCondition(activityTargetService.findActivityConditionDto(activity.getId(), ParticipateTypeEnum.PARTICIPATE.getState()));
            response.setHelperCondition(activityTargetService.findActivityConditionDto(activity.getId(), ParticipateTypeEnum.HELPER.getState()));
        } else {
            activityTargetService.fillUpTargetInfo(response);
        }

        if (Objects.equals(ChannelPromotionTypeEnum.ALLOW_PART.getState(), activity.getChannelPromotion())) {
            // 查询推广渠道信息
            response.setChannelList(activityChannelService.findChannelList(activity.getId()));
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean audit(String id, AuditSaveDto saveDto) {
        Activity activity = this.selectById(id, true);
        if (!Objects.equals(ActivityStatusEnum.PENDING_REVIEW.getState(), activity.getStatus())) {
            logger.error("ActivityServiceImpl.audit：活动[{}]当前状态[{}]不允许进行审核操作", id, activity.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动并非待审核状态，不允许进行审核操作"));
        }
        if (saveDto == null) {
            logger.error("ActivityServiceImpl.audit：入参为null");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，审核信息为null"));
        }
        if (Objects.equals(YesNoEnum.YES.getState(), saveDto.getStatus())) {
            // 审核通过
            this.initializeActivityStatusAfterPass(activity);
            // 新会员活动校验是否存在重叠活动
            this.checkOverlapping(activity.getId(), activity.getType(), activity.getStartTime(), activity.getEndTime(), activity.getStatus());
        } else if (Objects.equals(YesNoEnum.NO.getState(), saveDto.getStatus())) {
            // 审核驳回
            activity.setStatus(ActivityStatusEnum.REJECT.getState());
        } else {
            logger.error("ActivityServiceImpl.audit：入参审核状态异常为[{}]", saveDto.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，审核状态入参异常"));
        }
        this.updateById(activity);

        // 插入审核日志记录
        activityAuditHistoryService.create(id, saveDto.getStatus(), saveDto.getRemark());

        // 发券活动发券操作
        if (Objects.equals(ActivityTypeEnum.ISSUE_COUPON_ACTIVITY.getState(), activity.getType())
                && Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            this.issueCoupon(activity.getId(), activity.getApplicationType());
        }
        return Boolean.TRUE;
    }

    /**
     * 商户平台审核
     *
     * @param id
     * @param saveDto
     * @return
     */
    @Override
    public Boolean auditOnBusiness(String id, AuditSaveDto saveDto) {
        Activity activity = this.selectById(id, true);
        if (!Objects.equals(ActivityStatusEnum.BUSINESS_PENDING_REVIEW.getState(), activity.getStatus())) {
            logger.error("ActivityServiceImpl.auditOnBusiness：活动[{}]当前状态[{}]不允许进行审核操作", id, activity.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动并非商户待审核状态，不允许进行商户平台审核操作"));
        }
        if (saveDto == null) {
            logger.error("ActivityServiceImpl.auditOnBusiness：入参为null");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，审核信息为null"));
        }
        if (Objects.equals(YesNoEnum.YES.getState(), saveDto.getStatus())) {
            // 审核通过
            activity.setStatus(ActivityStatusEnum.PENDING_REVIEW.getState());
        } else if (Objects.equals(YesNoEnum.NO.getState(), saveDto.getStatus())) {
            // 审核驳回
            activity.setStatus(ActivityStatusEnum.BUSINESS_REJECT.getState());
        } else {
            logger.error("ActivityServiceImpl.auditOnBusiness：入参审核状态异常为[{}]", saveDto.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，审核状态入参异常"));
        }
        this.updateById(activity);
        // 插入审核日志记录
        saveDto.setStatus(saveDto.getStatus() == 0 ? 2 : 3);
        activityAuditHistoryService.create(id, saveDto.getStatus(), saveDto.getRemark());
        return Boolean.TRUE;
    }

    @Override
    public Integer start() {
        return activityMapper.start();
    }

    @Override
    public Boolean manualStart(String id) {
        // 查询活动信息
        Activity activity = this.selectById(id, true);
        if (activity.getStartTime().after(new Date())) {
            // 活动开始时间大于当前时间，不允许手动开始
            logger.error("ActivityServiceImpl.manualStart：活动[{}]开始时间[{}]大于当前时间，不允许手动开始",
                    activity.getId(), activity.getStartTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动还未到开始时间，不允许手动开始"));
        }
        if (Objects.equals(ActivityStatusEnum.PENDING_START.getState(), activity.getStatus())) {
            int updateResult = activityMapper.startById(id, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
            return updateResult == 1;
        } else {
            logger.info("ActivityServiceImpl.manualStart：活动[{}]当前状态为[{}]，不需要手动开始", activity.getId(), activity.getStatus());
            return Boolean.TRUE;
        }
    }

    @Override
    public Integer end() {
        Date now = new Date();
        int effectRows = 0;
        List<Activity> activityList = activityMapper.selectOvertimeActivities(now);
        if (CollectionUtil.isEmpty(activityList)) {
            // 没有已超时的进行中活动
            return effectRows;
        }
        for (Activity activity : activityList) {
            appRuntimeEnv.setTenantId(activity.getTenantCode());
            // 结束活动,各个活动结束互不影响
            try {
                Boolean result = activityService.end(activity);
                if (result) {
                    effectRows = effectRows + 1;
                }
            } catch (Exception e) {
                logger.error("ActivityServiceImpl.end：活动结束异常，错误信息：", e);
            }
        }
        return effectRows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean end(Activity activity) {
        if (activity == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "入参活动数据为null"));
        }
        // 修改活动状态为 已失效
        int updateResult = activityMapper.end(activity.getId(), BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (updateResult != 1) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "更新活动状态失败"));
        }

        // 返还权益
        activityCouponRelationService.returnEquityAmount(activity.getId());

        if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activity.getType())) {
            // 结束会员活动
            memberActivityService.endByActivityId(activity.getId());
            //系统发放奖励或线上领取奖励
            memberCouponRelationService.updateReservationByReceiveType(activity.getId(), null);
            // 发送模板消息
            try {
                // 查询成功的会员活动
                MemberActivityQueryDto queryDto = new MemberActivityQueryDto();
                queryDto.setActivityId(activity.getId());
                queryDto.setStatus(MemberActivityStatusEnum.SUCCESS.getState());
                List<MemberActivityDto> memberActivityDtoList = memberActivityService.findListWithOnlyEo(queryDto);
                if (CollectionUtil.isNotEmpty(memberActivityDtoList)) {
                    for (MemberActivityDto dto : memberActivityDtoList) {
                        if (Objects.equals(BoostAwardIssueTypeEnum.ALL_AFTER_TOP.getState(), activity.getBoostAwardIssueType())) {
                            ActivityAwardDto topAwardDto = activityCouponRelationService.queryTopRelation(activity.getId());
                            if (dto.getBoostNum() >= topAwardDto.getCompleteNum()) {
                                // 满足活动最高阶梯后即发放全部阶梯的奖品，并且已发放全部阶梯，则模板消息不在此处发送
                                continue;
                            }
                        }
                        messageService.issueAwards(dto.getMemberId(), dto.getActivityId());
                    }
                }
            } catch (Exception e) {
                logger.error("ActivityServiceImpl.end：发送模板消息失败，异常如下", e);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean manualEnd(String id) {
        // 查询活动信息
        Activity activity = this.selectById(id, true);
        if (activity.getEndTime().after(new Date())) {
            // 活动结束时间大于当前时间，不允许手动结束
            logger.error("ActivityServiceImpl.manualEnd：活动[{}]结束时间[{}]大于当前时间，不允许手动结束",
                    activity.getId(), activity.getEndTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动还未到结束时间，不允许手动结束"));
        }
        if (Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            activityService.end(activity);
        } else {
            logger.info("ActivityServiceImpl.manualEnd：活动[{}]当前状态为[{}]，不需要手动结束",
                    activity.getId(), activity.getStatus());
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean stop(String id) {
        Activity activity = this.selectById(id, true);
        // 判断状态是否允许停用（待生效或进行中才允许停止）
        if (!Objects.equals(ActivityStatusEnum.PENDING_START.getState(), activity.getStatus())
                && !Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            logger.error("ActivityServiceImpl.stop：活动[{}]状态[{}]不允许停止", id, activity.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "当前活动状态不可执行停止操作"));
        }

        activityService.end(activity);
        return Boolean.TRUE;
    }

    @Override
    public void initializeActivityStatus(Activity activity) {
        // 当不为草稿状态
        if (!Objects.equals(ActivityStatusEnum.DRAFT.getState(), activity.getStatus())) {
            if (Objects.equals(ActivityApplicationTypeEnum.BUSINESS_PLATFORM.getState(), activity.getApplicationType())) {
                //若创建平台是商户平台
                if (this.getMerchantActivityAuditConfig()) {
                    //初始为商户待审核
                    activity.setStatus(ActivityStatusEnum.BUSINESS_PENDING_REVIEW.getState());
                } else {
                    if (this.getActivityAuditConfig()) {
                        //运营平台需要审核
                        //初始为待审核
                        activity.setStatus(ActivityStatusEnum.PENDING_REVIEW.getState());
                    } else {
                        // 活动不需要审核，初始化
                        this.initializeActivityStatusAfterPass(activity);
                    }
                }
            } else {
                //若创建平台是运营平台
                if (this.getActivityAuditConfig()) {
                    //运营平台需要审核
                    //初始为待审核
                    activity.setStatus(ActivityStatusEnum.PENDING_REVIEW.getState());
                } else {
                    // 活动不需要审核，初始化
                    this.initializeActivityStatusAfterPass(activity);
                }
            }
        }

    }

    /**
     * 分页查询（进行中活动及达到预告时间未开始活动）
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    private PageBean<ActivityH5Dto> findPageByProcessingAndPendingStart(ActivityQueryDto query, Integer page, Integer size) {
        if (query == null) {
            query = new ActivityQueryDto();
        }
        query.setTenantCode(appRuntimeEnv.getTenantId());
        PageHelper.startPage(page, size);
        List<ActivityH5Dto> list = activityMapper.findListByProcessingAndPendingStart(query);
        return new PageBean<>(list);
    }

    @Override
    public PageBean<ActivityH5Dto> findH5Page(Integer type, String frontCategoryId, String name, Integer page, Integer size) {
        List<String> idList = null;
        if (StringUtils.isNotBlank(frontCategoryId)) {
            // 根据前端类目ID查询活动ID列表
            idList = this.findIdListByFrontCategoryId(frontCategoryId);
            if (CollectionUtil.isEmpty(idList)) {
                // 没有找到关联活动，直接返回
                return PageBeanUtils.getDefaultPageBean();
            }
        }

        //查询活动数据
        ActivityQueryDto query = new ActivityQueryDto();
        query.setType(type);
        query.setIdList(idList);
        query.setFuzzyName(name);
        query.setPlatformPromotion(YesNoEnum.YES.getState());
        PageBean<ActivityH5Dto> pageBean = this.findPageByProcessingAndPendingStart(query, page, size);
        List<ActivityH5Dto> list = pageBean.getContent();
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        List<String> activityIdList = new ArrayList<>(list.size());
        for (ActivityH5Dto activityH5Dto : list) {
            activityIdList.add(activityH5Dto.getId());
            // 查询奖品明细
            activityH5Dto.setAwardList(activityCouponRelationService.getAwardListWithCouponInfo(activityH5Dto.getId()));
            // 查询活动对象
            if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activityH5Dto.getType())) {
                TargetDto targetDto = activityTargetService.queryTargetInfo(activityH5Dto.getId());
                if (targetDto != null) {
                    activityH5Dto.setTargetType(targetDto.getTargetType());
                    activityH5Dto.setTargetIdList(targetDto.getTargetIdList());
                }
            }
        }
        if (StringUtils.isNotBlank(appRuntimeEnv.getToken())) {
            // 查询当前会员的消息提醒活动列表
            List<String> remindActivityIdList = activityRemindService.filterActivityIdListByLogin(activityIdList);
            for (ActivityH5Dto activityH5Dto : list) {
                if (remindActivityIdList.contains(activityH5Dto.getId())) {
                    activityH5Dto.setRemindMe(YesNoEnum.YES.getState());
                } else {
                    activityH5Dto.setRemindMe(YesNoEnum.NO.getState());
                }
            }
        }
        return pageBean;
    }

    @Override
    public ActivityH5DetailDto detailForH5(String id, Integer personType) {
        Activity activity = this.selectById(id, true);
        ActivityH5DetailDto response = new ActivityH5DetailDto();
        response.copy(activity);
        // 查询奖品列表
        response.setAwardList(activityCouponRelationService.getAwardListWithCouponInfo(id));
        for (ActivityAwardDto awardDto : response.getAwardList()) {
            // 查询标签列表
            awardDto.setItemTagList(this.trans(tagConnectRelationService.findTagList(awardDto.getType(), awardDto.getTypeId())));
        }
        // 查询活动对象
        if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activity.getType())) {
            response.setParticipateCondition(activityTargetService.findActivityConditionDto(activity.getId(), ParticipateTypeEnum.PARTICIPATE.getState()));
            response.setHelperCondition(activityTargetService.findActivityConditionDto(activity.getId(), ParticipateTypeEnum.HELPER.getState()));
        } else {
            TargetDto targetDto = activityTargetService.queryTargetInfo(id);
            if (targetDto != null) {
                response.setTargetType(targetDto.getTargetType());
                response.setTargetIdList(targetDto.getTargetIdList());
            }
        }
        // 会员端才有提醒我功能
        if (StringUtils.isNotBlank(appRuntimeEnv.getToken()) && Objects.equals(PersonTypeEnum.MEMBER.getState(), personType)) {
            // 查询当前会员的消息提醒活动列表
            List<String> remindActivityIdList = activityRemindService.filterActivityIdListByLogin(Lists.newArrayList(response.getId()));
            if (remindActivityIdList.contains(response.getId())) {
                response.setRemindMe(YesNoEnum.YES.getState());
            } else {
                response.setRemindMe(YesNoEnum.NO.getState());
            }
        }
        return response;
    }

    @Override
    public Integer issueCouponOnTime() {
        int effectRows = 0;
        List<Activity> activityList = activityMapper.selectIssueCouponActivities();
        if (CollectionUtil.isEmpty(activityList)) {
            // 没有进行中的发券活动
            return effectRows;
        }
        for (Activity activity : activityList) {
            appRuntimeEnv.setTenantId(activity.getTenantCode());
            // 发券,各个活动互不影响
            try {
                activityService.issueCoupon(activity.getId(), activity.getApplicationType());
                effectRows = effectRows + 1;
            } catch (Exception e) {
                logger.error("ActivityServiceImpl.issueCouponOnTime：发券异常，错误信息：", e);
            }
        }
        return effectRows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Klock(name = "issueCouponActivity", keys = {"#activityId"}, lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST)
    public void issueCoupon(String activityId, Integer applicationType) {
        // 查询会员列表
        List<String> memberIdList = activityTargetService.findTargetMemberIdList(activityId, applicationType);
        // 查询活动奖品
        List<ActivityAwardDto> awardList = activityCouponRelationService.getAwardList(activityId);
        if (CollectionUtil.isEmpty(awardList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询活动奖品数据失败"));
        }
        // 发券到会员手上
        if (CollectionUtil.isEmpty(memberIdList)) {
            logger.info("ActivityServiceImpl.issueCoupon：查询不到发放会员");
        } else {
            for (String memberId : memberIdList) {
                boolean issueSuccessToMember = true;
                for (ActivityAwardDto awardDto : awardList) {
                    if (awardDto.getRemainNum() > 0) {
                        if (Objects.equals(ConnectTypeEnum.COUPON.getState(), awardDto.getType())) {
                            memberCouponRelationService.createByCoupon(memberId, awardDto.getTypeId(), awardDto.getId(), activityId, VerificationStatusEnum.PENDING_USE.getState(), ReceiveTypeEnum.GET_ON_SYSTEM.getState());
                        } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), awardDto.getType())) {
                            memberCouponRelationService.createByCouponPackage(memberId, awardDto.getTypeId(), awardDto.getId(), activityId, VerificationStatusEnum.PENDING_USE.getState(), ReceiveTypeEnum.GET_ON_SYSTEM.getState());
                        }
                        awardDto.setRemainNum(awardDto.getRemainNum() - 1);
                    } else {
                        issueSuccessToMember = false;
                    }
                }
                // 新增会员活动数据
                memberActivityService.create(memberId, activityId, ActivityTypeEnum.ISSUE_COUPON_ACTIVITY.getState(), issueSuccessToMember);
            }
        }
        // 记录剩余奖品数量
        for (ActivityAwardDto awardDto : awardList) {
            activityCouponRelationService.subRemainNum(awardDto.getId(), awardDto.getNum() - awardDto.getRemainNum());
        }
        // 修改活动状态为 已失效
        int updateResult = activityMapper.end(activityId, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (updateResult != 1) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "更新活动状态失败"));
        }
        // 返还权益
        for (ActivityAwardDto awardDto : awardList) {
            activityCouponRelationService.returnEquityAmount(awardDto.getType(), awardDto.getTypeId(), awardDto.getRemainNum());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Klock(name = "activityCouponRelation", keys = {"#activityId"}, lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST)
    public Boolean issueCoupon(String activityId, String memberId) {
        if (StringUtils.isBlank(activityId) || StringUtils.isBlank(memberId)) {
            logger.info("ActivityServiceImpl.issueCoupon：入参活动ID为[{}],会员ID为[{}]", activityId, memberId);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常"));
        }
        // 查询活动奖品
        List<ActivityAwardDto> awardList = activityCouponRelationService.getAwardList(activityId);
        if (CollectionUtil.isEmpty(awardList)) {
            logger.error("ActivityServiceImpl.issueCoupon：查询活动优惠券关系失败，活动ID为[{}]", activityId);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询活动优惠券关系数据失败"));
        }
        for (ActivityAwardDto awardDto : awardList) {
            if (awardDto.getRemainNum() > 0) {
                if (Objects.equals(ConnectTypeEnum.COUPON.getState(), awardDto.getType())) {
                    memberCouponRelationService.createByCoupon(memberId, awardDto.getTypeId(), awardDto.getId(), activityId, VerificationStatusEnum.PENDING_USE.getState(), ReceiveTypeEnum.GET_ON_SYSTEM.getState());
                } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), awardDto.getType())) {
                    memberCouponRelationService.createByCouponPackage(memberId, awardDto.getTypeId(), awardDto.getId(), activityId, VerificationStatusEnum.PENDING_USE.getState(), ReceiveTypeEnum.GET_ON_SYSTEM.getState());
                }
                // 减少剩余数量
                activityCouponRelationService.subRemainNum(awardDto.getId(), 1);
            } else {
                logger.error("ActivityServiceImpl.issueCoupon：活动奖品[{}]剩余数量不足", awardDto.getId());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动奖品数量不足"));
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 补发券
     *
     * @param activityId 新会员活动ID
     */
    private void reissueCoupon(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            return;
        }
        MemberActivityQueryDto queryDto = new MemberActivityQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setActivityId(activityId);
        queryDto.setStatus(MemberActivityStatusEnum.PROCESSING.getState());
        queryDto.setSortAsc(YesNoEnum.YES.getState());
        List<MemberActivityDto> list = memberActivityService.findListWithOnlyEo(queryDto);
        if (CollectionUtil.isEmpty(list)) {
            logger.info("ActivityServiceImpl.reissueCoupon：没有需要补发奖品的会员活动，活动ID为[{}]", activityId);
            return;
        }
        // 补发礼品
        for (MemberActivityDto dto : list) {
            try {
                activityService.reissueCoupon(dto.getId(), dto.getActivityId(), dto.getMemberId());
            } catch (Exception e) {
                // 发放奖励失败
                logger.error("ActivityServiceImpl.reissueCoupon：补发活动奖励失败：", e);
                break;
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reissueCoupon(String memberActivityId, String activityId, String memberId) {
        // 发券
        activityService.issueCoupon(activityId, memberId);
        // 将会员活动设置为成功
        memberActivityService.endToSuccess(memberActivityId);
    }

    /**
     * 查询进行中的新会员活动（当没有时返回null，有多个时取第一个，并钉钉消息提示管理员异常）
     *
     * @return Activity
     */
    private Activity findProcessingNewMemberActivity() {
        List<Activity> list = activityMapper.selectProcessingNewMemberActivities(appRuntimeEnv.getTenantId());
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            DingDingBot.sendMsg("ActivityServiceImpl.findProcessingNewMemberActivity：查询出多个进行中新会员活动，请排查一下，租户为：" + appRuntimeEnv.getTenantId());
        }
        return list.get(0);
    }

    @Override
    public String newMember(String memberId) {
        // 查询进行中的新会员活动
        Activity activity = this.findProcessingNewMemberActivity();
        if (activity == null) {
            return null;
        }
        return this.participateNewMemberActivity(memberId, activity.getId());
    }

    /**
     * 参加新会员活动
     *
     * @param memberId   会员ID
     * @param activityId 活动ID
     * @return String 会员活动ID
     */
    @Klock(name = "newMemberActivity", keys = {"#activityId"}, lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST)
    private String participateNewMemberActivity(String memberId, String activityId) {
        // 查询是否已参加过活动
        Integer count = memberActivityService.count(activityId, memberId);
        if (count > 0) {
            // 已发起过当前活动
            DingDingBot.sendMsg("ActivityServiceImpl.participateNewMemberActivity：会员[" + memberId + "]已参加过活动[" + activityId + "]");
            return null;
        }
        // 发券到会员手上
        boolean issueSuccessToMember = true;
        try {
            activityService.issueCoupon(activityId, memberId);
        } catch (Exception e) {
            // 发券失败
            logger.error("ActivityServiceImpl.participateNewMemberActivity：发券至会员失败：", e);
            issueSuccessToMember = false;
        }
        // 新增会员活动数据
        return memberActivityService.create(memberId, activityId, ActivityTypeEnum.NEW_MEMBER_ACTIVITY.getState(), issueSuccessToMember);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String participate(String id, String channelId) {
        // 查询会员信息
        Member loginMember = memberService.getLoginMember();
        // 校验活动有效性
        Activity activity = this.checkActivityEffectiveness(id);
        // 校验是否活动对象
        this.checkTarget(activity, loginMember);
        // 查询活动奖品
        List<ActivityAwardDto> awardList = activityCouponRelationService.getAwardList(activity.getId());
        if (CollectionUtil.isEmpty(awardList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询活动奖品数据失败"));
        }
        // 校验是否超过参与次数
        this.checkParticipateTimes(activity, loginMember, awardList);
        // 领券活动校验奖品数量
        if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activity.getType())) {
            if (awardList.get(0).getRemainNum() <= 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "优惠券已领完!"));
            }
        }

        // 会员发起活动记录数据
        String memberActivityId = memberActivityService.create(loginMember.getId(), activity, channelId);
        if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activity.getType())) {
            // 领券活动，领券
            activityCouponRelationService.receive(loginMember.getId(), activity.getId());
            // 记录会员企业关系
            memberEnterpriseService.createByActivity(loginMember.getId(), activity.getId());
        } else if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activity.getType())) {
            // 助力活动，记录奖励
            this.recordAward(activity.getId(), activity.getBoostAwardIssueType(), loginMember.getId(), 0);
        }
        return memberActivityId;
    }

    @Override
    public Boolean boost(String participantMemberActivityId) {
        String loginMemberId = memberService.getLoginMemberId();
        return this.boost(participantMemberActivityId, loginMemberId);
    }

    @Override
    public Boolean boost(String participantMemberActivityId, String helperMemberId) {
        if (StringUtils.isBlank(participantMemberActivityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，发起者会员活动ID为null"));
        }
        if (StringUtils.isBlank(helperMemberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，助力者会员ID为null"));
        }
        // 查询助力者会员信息
        Member helperMember = memberService.getMemberById(helperMemberId);
        // 校验发起者会员活动有效性
        MemberActivity participantActivity = memberActivityService.checkMemberActivityEffectiveness(participantMemberActivityId);
        // 校验活动有效性
        Activity activity = this.checkActivityEffectiveness(participantActivity.getActivityId());
        // 校验是否满足助力者条件
        this.checkBoostActivityTarget(activity.getId(), helperMember, ParticipateTypeEnum.HELPER.getState());

        activityService.addBoostNum(participantActivity, helperMemberId, activity);
        return Boolean.TRUE;
    }

    @Override
    public Boolean cancelBoost(String helperMemberId) {
        if (StringUtil.isBlank(helperMemberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，助力者会员ID为null"));
        }
        // 查询助力记录
        BoostRecordDto boostRecordDto = boostRecordService.queryByBoostMemberId(helperMemberId);
        if (boostRecordDto == null) {
            // 没有助力过活动,不操作
            return Boolean.TRUE;
        }
        // 校验发起者会员活动有效性
        MemberActivity participantActivity = memberActivityService.checkMemberActivityEffectiveness(boostRecordDto.getMemberActivityId());
        // 校验活动有效性
        this.checkActivityEffectiveness(participantActivity.getActivityId());

        activityService.subBoostNum(participantActivity, boostRecordDto.getId());
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Klock(name = "memberActivity", keys = {"#participantMemberActivity.id"}, lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST)
    public Integer addBoostNum(MemberActivity participantMemberActivity, String helperMemberId, Activity activity) {
        // 校验助力者是否已助力过任何活动
        if (!boostRecordService.allowBoost(helperMemberId)) {
            logger.error("ActivityServiceImpl.addBoostNum：助力者会员ID为[{}]，已助力过活动", helperMemberId);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "助力者已助力过活动"));
        }

        // 添加助力记录
        boostRecordService.create(participantMemberActivity.getId(), helperMemberId);

        // 助力人数 + 1
        Integer boostNum = memberActivityService.addBoostNum(participantMemberActivity.getId(), 1);

        // 记录奖励
        this.recordAward(participantMemberActivity.getActivityId(), activity.getBoostAwardIssueType(), participantMemberActivity.getMemberId(), boostNum);
        return boostNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Klock(name = "memberActivity", keys = {"#participantMemberActivity.id"}, lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST)
    public Integer subBoostNum(MemberActivity participantMemberActivity, String boostRecordId) {
        // 删除助力记录
        boostRecordService.delete(Lists.newArrayList(boostRecordId));

        // 获取旧的最后已达成阶梯
        ActivityAwardDto oldLatestAward = activityCouponRelationService.queryLatestCompleteRelation(participantMemberActivity.getActivityId(), participantMemberActivity.getBoostNum());

        // 助力人数 - 1
        Integer boostNum = memberActivityService.subBoostNum(participantMemberActivity.getId(), 1);

        // 获取新的最后已达成阶梯
        ActivityAwardDto newLatestAward = activityCouponRelationService.queryLatestCompleteRelation(participantMemberActivity.getActivityId(), boostNum);

        if (!Objects.equals(oldLatestAward, newLatestAward)) {
            // 前后阶梯不一致，需返回奖励
            MemberCouponRelationQueryDto queryDto = new MemberCouponRelationQueryDto();
            queryDto.setMemberId(participantMemberActivity.getMemberId());
            queryDto.setActivityId(participantMemberActivity.getActivityId());
            queryDto.setActivityCouponRelationId(oldLatestAward.getId());
            queryDto.setVerificationStatus(VerificationStatusEnum.RESERVATION.getState());
            List<MemberCouponRelationListDto> memberCouponRelationList = memberCouponRelationService.findAllWithOnlyEo(queryDto);
            if (CollectionUtil.isEmpty(memberCouponRelationList)) {
                logger.error("ActivityServiceImpl.subBoostNum：查询预定会员优惠券数据失败，活动ID[{}]，会员ID[{}]，活动优惠券关系ID[{}]",
                        participantMemberActivity.getActivityId(), participantMemberActivity.getActivityId(), oldLatestAward.getId());
                return boostNum;
            }
            List<String> memberCouponRelationIdList = new ArrayList<>(memberCouponRelationList.size());
            memberCouponRelationList.forEach(dto -> memberCouponRelationIdList.add(dto.getId()));
            memberCouponRelationService.delete(memberCouponRelationIdList);

            // 返还奖励数量
            Integer addResult = activityCouponRelationService.addRemainNum(oldLatestAward.getId(), 1);
            if (addResult != 1) {
                logger.error("ActivityServiceImpl.subBoostNum：活动优惠券关系增加剩余数量操作失败，addRemainNum方法返回值为[{}]，主键ID为[{}]，数量为[{}]",
                        addResult, oldLatestAward.getId(), 1);
                throw new ApplicationException(ResultEnum.DATABASE_ERROR);
            }
        }
        return boostNum;
    }

    /**
     * 记录奖励
     *
     * @param activityId          活动ID
     * @param boostAwardIssueType 助力奖品发放条件类型
     * @param memberId            会员ID
     * @param boostNum            助力人数
     */
    private void recordAward(String activityId, Integer boostAwardIssueType, String memberId, Integer boostNum) {
        // 查询当前助力人数所达到的最高奖励
        ActivityAwardDto latestAwardDto = activityCouponRelationService.queryLatestCompleteRelation(activityId, boostNum);
        if (latestAwardDto != null) {
            // 查询是否已经记录过奖励
            MemberCouponRelationQueryDto memberCouponRelationQueryDto = new MemberCouponRelationQueryDto();
            memberCouponRelationQueryDto.setMemberId(memberId);
            memberCouponRelationQueryDto.setActivityCouponRelationId(latestAwardDto.getId());
            memberCouponRelationQueryDto.setActivityId(activityId);
            Integer count = memberCouponRelationService.count(memberCouponRelationQueryDto);
            if (count <= 0) {
                // 没有记录过该奖励，记录
                Integer subResult = activityCouponRelationService.subRemainNum(latestAwardDto.getId(), 1);
                if (subResult == 1) {
                    // 奖励数量充足
                    if (Objects.equals(ConnectTypeEnum.COUPON.getState(), latestAwardDto.getType())) {
                        memberCouponRelationService.createByCoupon(memberId, latestAwardDto.getTypeId(), latestAwardDto.getId(), activityId, VerificationStatusEnum.RESERVATION.getState(), latestAwardDto.getReceiveType());
                    } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), latestAwardDto.getType())) {
                        memberCouponRelationService.createByCouponPackage(memberId, latestAwardDto.getTypeId(), latestAwardDto.getId(), activityId, VerificationStatusEnum.RESERVATION.getState(), latestAwardDto.getReceiveType());
                    }
                } else {
                    // 奖励数量不足
                    logger.info("ActivityServiceImpl.recordAward：活动奖励剩余数量不足，活动优惠券关系ID为[{}]", latestAwardDto.getId());
                }
            }

            // 检查奖励发放类型，判断是否发放给用户
            if (Objects.equals(BoostAwardIssueTypeEnum.ALL_AFTER_TOP.getState(), boostAwardIssueType)) {
                ActivityAwardDto topAwardDto = activityCouponRelationService.queryTopRelation(activityId);
                if (boostNum >= topAwardDto.getCompleteNum()) {
                    // 满足最高阶梯，发放全部阶梯奖品给会员
                    memberCouponRelationService.updateReservationByReceiveType(activityId, memberId);
                    // 发送模板消息
                    messageService.issueAwards(memberId, activityId);
                }
            }
        }
    }

    /**
     * 校验参与活动次数
     *
     * @param activity  活动数据
     * @param member    会员数据
     * @param awardList 奖品数据
     */
    private void checkParticipateTimes(Activity activity, Member member, List<ActivityAwardDto> awardList) {
        Integer count = memberActivityService.count(activity.getId(), member.getId());
        if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activity.getType())) {
            // 领券活动
            Integer receiveLimitNum = awardList.get(0).getReceiveLimitNum();
            if (receiveLimitNum <= count) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "您已领取过该优惠券，不要太贪心哦"));
            }
        } else if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activity.getType())) {
            // 助力活动
            if (count > 0) {
                // 已发起过当前活动
                logger.error("ActivityServiceImpl.checkParticipateTimes：会员ID为[{}]，活动ID为[{}]，已经发起过当前活动", member.getId(), activity.getId());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "不可重复发起相同活动"));
            }
        } else {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动类型异常"));
        }
    }

    /**
     * 校验是否活动对象
     *
     * @param activity 活动数据
     * @param member   会员数据
     */
    private void checkTarget(Activity activity, Member member) {
        if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activity.getType())) {
            // 领券活动
            this.checkReceiveCouponActivityTarget(activity.getId(), member);
        } else if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), activity.getType())) {
            // 助力活动
            this.checkBoostActivityTarget(activity.getId(), member, ParticipateTypeEnum.PARTICIPATE.getState());
        } else {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动类型异常"));
        }
    }

    /**
     * 校验是否领券活动的活动对象
     *
     * @param activityId 活动ID
     * @param member     会员数据
     */
    private void checkReceiveCouponActivityTarget(String activityId, Member member) {
        // 查询活动对象
        TargetDto targetDto = activityTargetService.queryTargetInfo(activityId);
        // 默认必须满足注册会员条件
        if (!Objects.equals(MemberRankEnum.REGISTER_MEMBER.getValue(), member.getMemberLevelId())) {
            logger.error("ActivityServiceImpl.checkReceiveCouponActivityTarget：会员[{}]的等级ID为[{}]",
                    member.getId(), member.getMemberLevelId());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "不满足活动条件"));
        }
        if (Objects.equals(ActivityTargetTypeEnum.ATTENTION_PUBLIC.getState(), targetDto.getTargetType())) {
            // 校验是否满足关注公众号条件
            Boolean attentionPublicResult = wechatMemberInfoService.isPayByWechatNumberIds(targetDto.getTargetIdList(), member.getId());
            if (attentionPublicResult == null || !attentionPublicResult) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "不满足活动条件"));
            }
        }
    }

    /**
     * 校验是否助力活动的活动对象
     *
     * @param activityId 活动ID
     * @param member     会员数据
     */
    private void checkBoostActivityTarget(String activityId, Member member, Integer participateType) {
        // 查询活动对象
        ActivityConditionDto conditionDto = activityTargetService.findActivityConditionDto(activityId, participateType);
        if (CollectionUtil.isNotEmpty(conditionDto.getMemberGroupIdList())) {
            if (!conditionDto.getMemberGroupIdList().contains(member.getMemberLevelId())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "不满足活动条件"));
            }
        }
        if (CollectionUtil.isNotEmpty(conditionDto.getWeChatNumberIdList())) {
            // 校验是否满足关注公众号条件
            Boolean attentionPublicResult = wechatMemberInfoService.isPayByWechatNumberIds(conditionDto.getWeChatNumberIdList(), member.getId());
            if (attentionPublicResult == null || !attentionPublicResult) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "不满足活动条件"));
            }
        }
    }

    /**
     * 校验活动有效性（有效则返回活动信息）
     *
     * @param id 活动ID
     * @return Activity 活动信息
     */
    private Activity checkActivityEffectiveness(String id) {
        // 查询活动信息
        Activity activity = this.selectById(id, true);
        // 下面两个判断是为了增加前端提示语
        if (Objects.equals(ActivityStatusEnum.PENDING_START.getState(), activity.getStatus())) {
            // 活动待生效，无效
            logger.error("ActivityServiceImpl.checkActivityEffectiveness：活动[{}]当前状态为[{}]，不允许参加", id, activity.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动还未开始哦"));
        }
        if (Objects.equals(ActivityStatusEnum.INVALID.getState(), activity.getStatus())) {
            // 活动已失效，无效
            logger.error("ActivityServiceImpl.checkActivityEffectiveness：活动[{}]当前状态为[{}]，不允许参加", id, activity.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "来晚一步啦，活动结束了"));
        }
        if (!Objects.equals(ActivityStatusEnum.PROCESSING.getState(), activity.getStatus())) {
            // 活动不在进行中，无效
            logger.error("ActivityServiceImpl.checkActivityEffectiveness：活动[{}]当前状态为[{}]，不允许参加", id, activity.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动状态异常"));
        }
        if (new Date().after(activity.getEndTime())) {
            // 当前时间大于活动结束时间，活动已结束
            logger.error("ActivityServiceImpl.checkActivityEffectiveness：活动[{}]结束时间为[{}]，活动已结束", id, activity.getEndTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "来晚一步啦，活动结束了"));
        }
        return activity;
    }

    /**
     * 数据入库
     *
     * @param activity 数据
     */
    private void insert(Activity activity) {
        if (activity == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动保存数据为null!"));
        }
        int insertResult = activityMapper.insert(activity);
        if (insertResult != 1) {
            logger.error("ActivityServiceImpl.insert：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    /**
     * 数据更新入库
     *
     * @param activity 数据
     */
    private void updateById(Activity activity) {
        if (activity == null) {
            logger.error("ActivityServiceImpl.updateById：入参为null");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常!"));
        }
        int updateResult = activityMapper.updateById(activity);
        if (updateResult != 1) {
            logger.error("ActivityServiceImpl.updateById：更新数据操作失败，updateById方法返回值为[]，主键ID为[{}]",
                    updateResult, activity.getId());
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    /**
     * 验证数据合法性
     *
     * @param saveDto 数据
     */
    private void validateInfo(ActivitySaveDto saveDto) {
        if (saveDto == null) {
            logger.error("ActivityServiceImpl.validateInfo：入参为null");
            throw new ApplicationException(ResultEnum.PARAMETER_ERROR);
        }
        if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), saveDto.getType())) {
            this.validateReceiveCouponActivityInfo(saveDto);
        } else if (Objects.equals(ActivityTypeEnum.BOOST_ACTIVITY.getState(), saveDto.getType())) {
            this.validateBoostActivityInfo(saveDto);
        } else if (Objects.equals(ActivityTypeEnum.ISSUE_COUPON_ACTIVITY.getState(), saveDto.getType())) {
            this.validateIssueCouponActivityInfo(saveDto);
        } else if (Objects.equals(ActivityTypeEnum.NEW_MEMBER_ACTIVITY.getState(), saveDto.getType())) {
            this.validateNewMemberActivityInfo(saveDto);
        } else {
            logger.error("ActivityServiceImpl.validateInfo：活动类型[{}]异常", saveDto.getType());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动类型异常"));
        }
    }

    /**
     * 验证领券活动数据合法性
     *
     * @param saveDto 数据
     */
    private void validateReceiveCouponActivityInfo(ActivitySaveDto saveDto) {
        if (StringUtils.isBlank(saveDto.getName())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动名称"));
        }
        final int nameLimitLength = 20;
        if (saveDto.getName().length() > nameLimitLength) {
            logger.error("ActivityServiceImpl.validateReceiveCouponActivityInfo：活动名称长度为[{}]", saveDto.getName().length());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动名称最多输入" + nameLimitLength + "个字符"));
        }
        if (StringUtils.isBlank(saveDto.getDescription())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动描述"));
        }
        final int descriptionLimitLength = 60;
        if (saveDto.getDescription().length() > descriptionLimitLength) {
            logger.error("ActivityServiceImpl.validateReceiveCouponActivityInfo：活动描述长度为[{}]", saveDto.getDescription().length());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动描述最多输入" + descriptionLimitLength + "个字符"));
        }
        List<ActivityAwardSaveDto> awardList = saveDto.getAwardList();
        if (CollectionUtil.isEmpty(awardList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择优惠券"));
        }
        final int awardLimitLength = 1;
        if (awardList.size() > awardLimitLength) {
            logger.error("ActivityServiceImpl.validateReceiveCouponActivityInfo：奖品长度为[{}]", awardList.size());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "最多支持添加" + awardLimitLength + "张优惠券"));
        }
        for (ActivityAwardSaveDto activityAwardSaveDto : awardList) {
            if (!Objects.equals(ConnectTypeEnum.COUPON.getState(), activityAwardSaveDto.getType())
                    && !Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), activityAwardSaveDto.getType())) {
                logger.error("ActivityServiceImpl.validateReceiveCouponActivityInfo：活动奖品类型[{}]异常", activityAwardSaveDto.getType());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券类型异常"));
            }
            if (StringUtils.isBlank(activityAwardSaveDto.getTypeId())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券ID为null"));
            }
            if (activityAwardSaveDto.getNum() == null || activityAwardSaveDto.getNum() <= 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入领券数量"));
            }
            if (activityAwardSaveDto.getReceiveLimitNum() == null || activityAwardSaveDto.getReceiveLimitNum() <= 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入领券限制数量"));
            }
        }
        if (saveDto.getStartTime() == null || saveDto.getStartTime().before(new Date())) {
            logger.error("ActivityServiceImpl.validateReceiveCouponActivityInfo：开始时间[{}]不得早于当前时间", saveDto.getStartTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "开始时间不得早于当前时间"));
        }
        if (saveDto.getEndTime() == null || saveDto.getEndTime().before(saveDto.getStartTime())) {
            logger.error("ActivityServiceImpl.validateReceiveCouponActivityInfo：结束时间[{}]不得早于开始时间[{}]",
                    saveDto.getEndTime(), saveDto.getStartTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "结束时间不得早于开始时间"));
        }
        this.validateChannelPromotion(saveDto.getChannelPromotion(), saveDto.getChannelIdList());
        if (Objects.equals(ActivityTargetTypeEnum.ATTENTION_PUBLIC.getState(), saveDto.getTargetType())) {
            if (CollectionUtil.isEmpty(saveDto.getTargetIdList())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择关注的公众号"));
            }
        } else if (!Objects.equals(ActivityTargetTypeEnum.All.getState(), saveDto.getTargetType())) {
            logger.error("ActivityServiceImpl.validateIssueCouponActivityInfo：活动对象类型[{}]异常", saveDto.getTargetType());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "领取条件类型异常"));
        }
    }

    /**
     * 验证助力活动数据合法性
     *
     * @param saveDto 数据
     */
    private void validateBoostActivityInfo(ActivitySaveDto saveDto) {
        if (StringUtils.isBlank(saveDto.getName())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动名称"));
        }
        if (saveDto.getStartTime() == null || saveDto.getStartTime().before(new Date())) {
            logger.error("ActivityServiceImpl.validateBoostActivityInfo：开始时间[{}]不得早于当前时间", saveDto.getStartTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "开始时间不得早于当前时间"));
        }
        if (saveDto.getEndTime() == null || saveDto.getEndTime().before(saveDto.getStartTime())) {
            logger.error("ActivityServiceImpl.validateBoostActivityInfo：结束时间[{}]不得早于开始时间[{}]",
                    saveDto.getEndTime(), saveDto.getStartTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "结束时间不得早于开始时间"));
        }
        if (saveDto.getValidTime() == null) {
            logger.error("ActivityServiceImpl.validateBoostActivityInfo：有效时间入参为null");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入助力有效时间"));
        }
        List<ActivityAwardSaveDto> awardList = saveDto.getAwardList();
        if (CollectionUtil.isEmpty(awardList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请添加阶梯助力奖励"));
        }
        final int awardLimitLength = 6;
        if (awardList.size() > awardLimitLength) {
            logger.error("ActivityServiceImpl.validateBoostActivityInfo：奖品长度为[{}]", awardList.size());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "最多支持添加" + awardLimitLength + "级阶梯助力奖励"));
        }
        for (ActivityAwardSaveDto activityAwardSaveDto : awardList) {
            if (!Objects.equals(ConnectTypeEnum.COUPON.getState(), activityAwardSaveDto.getType())
                    && !Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), activityAwardSaveDto.getType())) {
                logger.error("ActivityServiceImpl.validateBoostActivityInfo：活动奖品类型[{}]异常", activityAwardSaveDto.getType());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券类型异常"));
            }
            if (StringUtils.isBlank(activityAwardSaveDto.getTypeId())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券ID为null"));
            }
            if (StringUtils.isBlank(activityAwardSaveDto.getTitle())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入奖励标题"));
            }
            if (activityAwardSaveDto.getCompleteNum() == null || activityAwardSaveDto.getCompleteNum() < 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入助力人数"));
            }
            if (activityAwardSaveDto.getNum() == null || activityAwardSaveDto.getNum() <= 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入礼品数量"));
            }
            if (!Objects.equals(ReceiveTypeEnum.GET_ON_SYSTEM.getState(), activityAwardSaveDto.getReceiveType())
                    && !Objects.equals(ReceiveTypeEnum.GET_ON_LINE.getState(), activityAwardSaveDto.getReceiveType())) {
                logger.error("ActivityServiceImpl.validateBoostActivityInfo：奖励领取方式[{}]异常", activityAwardSaveDto.getReceiveType());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "领取类型异常"));
            }
            final int receiveDescriptionLimitLength = 100;
            if (!StringUtils.isBlank(activityAwardSaveDto.getReceiveDescription()) && activityAwardSaveDto.getReceiveDescription().length() > receiveDescriptionLimitLength) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "领取说明最多支持输入" + receiveDescriptionLimitLength + "个字符"));
            }
            if (activityAwardSaveDto.getReceiveLimitNum() == null || activityAwardSaveDto.getReceiveLimitNum() <= 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入领券限制数量"));
            }
        }
        this.validateActivityCondition(saveDto.getParticipateCondition(), "请选择发起人条件");
        this.validateActivityCondition(saveDto.getHelperCondition(), "请选择助力人条件");
        if (null == saveDto.getBoostAwardIssueType()) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择奖品发放条件"));
        }
        if (StringUtils.isBlank(saveDto.getThumbnailUrl())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动缩略图"));
        }
        if (StringUtils.isBlank(saveDto.getImageUrl())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入发起者页活动海报"));
        }
        if (StringUtils.isBlank(saveDto.getBoostImageUrl())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入助力页活动海报"));
        }
        this.validateChannelPromotion(saveDto.getChannelPromotion(), saveDto.getChannelIdList());
        if (StringUtils.isBlank(saveDto.getBoostDescription())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入助力说明"));
        }
    }

    /**
     * 验证发券活动数据合法性
     *
     * @param saveDto 数据
     */
    private void validateIssueCouponActivityInfo(ActivitySaveDto saveDto) {
        if (StringUtils.isBlank(saveDto.getName())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动名称"));
        }
        final int nameLimitLength = 20;
        if (saveDto.getName().length() > nameLimitLength) {
            logger.error("ActivityServiceImpl.validateIssueCouponActivityInfo：活动名称长度为[{}]", saveDto.getName().length());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动名称最多输入" + nameLimitLength + "个字符"));
        }
        List<ActivityAwardSaveDto> awardList = saveDto.getAwardList();
        if (CollectionUtil.isEmpty(awardList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择优惠券"));
        }
        final int awardLimitLength = 6;
        if (awardList.size() > awardLimitLength) {
            logger.error("ActivityServiceImpl.validateIssueCouponActivityInfo：奖品长度为[{}]", awardList.size());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "最多支持添加" + awardLimitLength + "张优惠券"));
        }
        for (ActivityAwardSaveDto activityAwardSaveDto : awardList) {
            if (!Objects.equals(ConnectTypeEnum.COUPON.getState(), activityAwardSaveDto.getType())
                    && !Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), activityAwardSaveDto.getType())) {
                logger.error("ActivityServiceImpl.validateIssueCouponActivityInfo：活动奖品类型[{}]异常", activityAwardSaveDto.getType());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券类型异常"));
            }
            if (StringUtils.isBlank(activityAwardSaveDto.getTypeId())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券ID为null"));
            }
            if (activityAwardSaveDto.getNum() == null || activityAwardSaveDto.getNum() <= 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入发券数量"));
            }
        }
        if (Objects.equals(ActivityTargetTypeEnum.MEMBER_GROUP.getState(), saveDto.getTargetType())
                || Objects.equals(ActivityTargetTypeEnum.MEMBER_TAG.getState(), saveDto.getTargetType())) {
            if (CollectionUtil.isEmpty(saveDto.getTargetIdList())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的发放会员ID列表为null"));
            }
        } else if (!Objects.equals(ActivityTargetTypeEnum.All.getState(), saveDto.getTargetType())) {
            logger.error("ActivityServiceImpl.validateIssueCouponActivityInfo：活动对象类型[{}]异常", saveDto.getTargetType());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的发放会员类型异常"));
        }
        if (Objects.equals(StartTimeTypeEnum.ON_TIME.getState(), saveDto.getStartTimeType())) {
            if (saveDto.getStartTime() == null || saveDto.getStartTime().before(new Date())) {
                logger.error("ActivityServiceImpl.validateIssueCouponActivityInfo：活动开始时间异常[{}]", saveDto.getStartTime());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入正确的发放开始时间"));
            }
        } else if (!Objects.equals(StartTimeTypeEnum.NOW.getState(), saveDto.getStartTimeType())) {
            logger.error("ActivityServiceImpl.validateIssueCouponActivityInfo：开始时间类型[{}]异常", saveDto.getStartTimeType());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "发放时间类型异常"));
        }
    }

    /**
     * 验证新会员活动数据合法性
     *
     * @param saveDto 数据
     */
    private void validateNewMemberActivityInfo(ActivitySaveDto saveDto) {
        if (StringUtils.isBlank(saveDto.getName())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入活动名称"));
        }
        final int nameLimitLength = 20;
        if (saveDto.getName().length() > nameLimitLength) {
            logger.error("ActivityServiceImpl.validateNewMemberActivityInfo：活动名称长度为[{}]", saveDto.getName().length());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动名称最多输入" + nameLimitLength + "个字符"));
        }
        List<ActivityAwardSaveDto> awardList = saveDto.getAwardList();
        if (CollectionUtil.isEmpty(awardList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请添加礼品明细"));
        }
        final int awardLimitLength = 6;
        if (awardList.size() > awardLimitLength) {
            logger.error("ActivityServiceImpl.validateNewMemberActivityInfo：奖品长度为[{}]", awardList.size());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "最多支持添加" + awardLimitLength + "级阶梯助力奖励"));
        }
        for (ActivityAwardSaveDto activityAwardSaveDto : awardList) {
            if (!Objects.equals(ConnectTypeEnum.COUPON.getState(), activityAwardSaveDto.getType())
                    && !Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), activityAwardSaveDto.getType())) {
                logger.error("ActivityServiceImpl.validateNewMemberActivityInfo：活动奖品类型[{}]异常", activityAwardSaveDto.getType());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券类型异常"));
            }
            if (StringUtils.isBlank(activityAwardSaveDto.getTypeId())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "选择的优惠券ID为null"));
            }
            if (StringUtils.isBlank(activityAwardSaveDto.getTitle())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入奖励标题"));
            }
            final int titleLimitLength = 6;
            if (activityAwardSaveDto.getTitle().length() > titleLimitLength) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "礼品标题最多支持输入" + titleLimitLength + "个字符"));
            }
            if (activityAwardSaveDto.getNum() == null || activityAwardSaveDto.getNum() <= 0) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入礼品数量"));
            }
        }
        if (saveDto.getStartTime() == null || saveDto.getStartTime().before(new Date())) {
            logger.error("ActivityServiceImpl.validateNewMemberActivityInfo：开始时间[{}]不得早于当前时间", saveDto.getStartTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "开始时间不得早于当前时间"));
        }
        if (saveDto.getEndTime() == null || saveDto.getEndTime().before(saveDto.getStartTime())) {
            logger.error("ActivityServiceImpl.validateNewMemberActivityInfo：结束时间[{}]不得早于开始时间[{}]",
                    saveDto.getEndTime(), saveDto.getStartTime());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "结束时间不得早于开始时间"));
        }
    }

    /**
     * 验证活动条件数据合法性
     *
     * @param activityConditionDto 活动条件
     * @param exceptionMsg         异常信息
     */
    private void validateActivityCondition(ActivityConditionDto activityConditionDto, String exceptionMsg) {
        if (activityConditionDto == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, exceptionMsg));
        } else {
            if (CollectionUtil.isEmpty(activityConditionDto.getMemberGroupIdList())
                    && CollectionUtil.isEmpty(activityConditionDto.getWeChatNumberIdList())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, exceptionMsg));
            }
        }
    }

    /**
     * 验证渠道推广数据合法性
     *
     * @param channelPromotionType 渠道推广类型
     * @param channelIdList        渠道ID列表
     */
    private void validateChannelPromotion(Integer channelPromotionType, List<String> channelIdList) {
        if (Objects.equals(ChannelPromotionTypeEnum.ALLOW_PART.getState(), channelPromotionType)) {
            if (CollectionUtil.isEmpty(channelIdList)) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择推广团"));
            }
        } else if (!Objects.equals(ChannelPromotionTypeEnum.NOT_ALLOWED.getState(), channelPromotionType)
                && !Objects.equals(ChannelPromotionTypeEnum.ALLOW_ALL.getState(), channelPromotionType)) {
            logger.error("ActivityServiceImpl.validateChannelPromotion：渠道推广类型[{}]异常", channelPromotionType);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "推广团类型异常"));
        }
    }

    /**
     * 验证平台类型
     *
     * @param applicationType 平台类型
     */
    private void validateApplicationType(Integer applicationType) {
        if (!Objects.equals(ApplicationTypeEnum.OPERATION_PLATFORM.getState(), applicationType)
                && !Objects.equals(ApplicationTypeEnum.MERCHANT_PLATFORM.getState(), applicationType)) {
            logger.error("ActivityServiceImpl.validateApplicationType：创建平台类型[{}]异常", applicationType);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入创建平台"));
        }
    }

    /**
     * 初始化活动状态（审核通过后）（影响字段有活动状态、真实开始时间、真实结束时间）
     *
     * @param activity 活动数据
     */
    private void initializeActivityStatusAfterPass(Activity activity) {
        //初始化为待生效
        activity.setStatus(ActivityStatusEnum.PENDING_START.getState());
        Date now = new Date();
        if (Objects.equals(StartTimeTypeEnum.NOW.getState(), activity.getStartTimeType())) {
            // 立即开始
            activity.setStatus(ActivityStatusEnum.PROCESSING.getState());
            activity.setRealStartTime(now);
        } else {
            if (activity.getStartTime() != null && activity.getStartTime().before(now)) {
                // 开始时间小于今天，活动状态默认为活动中
                activity.setStatus(ActivityStatusEnum.PROCESSING.getState());
                activity.setRealStartTime(now);
            }
        }
        if (activity.getEndTime() != null && activity.getEndTime().before(now)) {
            // 结束时间小于今天，活动状态默认为已失效
            activity.setStatus(ActivityStatusEnum.INVALID.getState());
            activity.setRealEndTime(now);
        }
    }

    /**
     * 获取活动审核配置（查询不到则默认需要审核）
     *
     * @return SystemConfig
     */
    private boolean getActivityAuditConfig() {
        SystemConfig systemConfig = allConfigService.detail();
        if (systemConfig == null || systemConfig.getActivityEnable() == null) {
            logger.error("ActivityServiceImpl.getActivityAuditConfig：查询配置信息为null");
            // 查询不到配置信息，默认需要审核
            return true;
        }
        return systemConfig.getActivityEnable();
    }

    /**
     * 获取商户端企业活动审核配置（查询不到则默认需要审核）
     *
     * @return SystemConfig
     */
    private boolean getMerchantActivityAuditConfig() {
        LoginBusinessInfoVo loginBusinessInfo = businessAccountService.getLoginBusinessInfo();
        if (loginBusinessInfo == null) {
            // 活动时间重叠
            logger.error("ActivityServiceImpl.getMerchantActivityAuditConfig：查询不到配置的企业 " );
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "企业活动配置审核:查询不到配置的企业"));
        }
        MerchantSystemConfig merchantConfig = merchantAllConfigService.detail(loginBusinessInfo.getEnterprOrganizationId());
        if (merchantConfig == null || merchantConfig.getActivityEnable() == null) {
            logger.error("ActivityServiceImpl.getMerchantActivityAuditConfig：查询配置信息为null");
            // 查询不到配置信息，默认需要审核
            return true;
        }
        logger.info("ActivityServiceImpl.getMerchantActivityAuditConfig：获取到的企业{}活动审核配置值为 {}",loginBusinessInfo.getEnterprOrganizationId(),merchantConfig.getActivityEnable().toString());
        return merchantConfig.getActivityEnable();
    }

    /**
     * 新会员活动-校验活动时间是否重叠（重叠则抛异常）
     *
     * @param activityId 活动ID
     * @param type       活动类型
     * @param startTime  活动开始时间
     * @param endTime    活动结束时间
     * @param status     活动状态
     */
    private void checkOverlapping(String activityId, Integer type, Date startTime, Date endTime, Integer status) {
        if (Objects.equals(ActivityTypeEnum.NEW_MEMBER_ACTIVITY.getState(), type)) {
            if (Objects.equals(ActivityStatusEnum.PENDING_START.getState(), status)
                    || Objects.equals(ActivityStatusEnum.PROCESSING.getState(), status)) {
                List<Integer> statusList = Lists.newArrayList();
                statusList.add(ActivityStatusEnum.PENDING_START.getState());
                statusList.add(ActivityStatusEnum.PROCESSING.getState());
                List<String> activityIdList = activityMapper.selectOverlappingActivityIdList(
                        appRuntimeEnv.getTenantId(), type, startTime, endTime, statusList);
                boolean notOverlapping = CollectionUtil.isEmpty(activityIdList) || (activityIdList.size() == 1 && activityIdList.contains(activityId));
                if (!notOverlapping) {
                    // 活动时间重叠
                    logger.error("ActivityServiceImpl.checkOverlapping：存在活动时间重叠的活动：[{}]", activityIdList.toString());
                    throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动时间重叠"));
                }
            }
        }
    }

    /**
     * 根据前端类目ID查询关联的活动ID列表
     *
     * @param frontCategoryId 前端类目ID
     * @return List
     */
    private List<String> findIdListByFrontCategoryId(String frontCategoryId) {
        // 获取当前类目及其子类目ID
        List<String> frontCategoryIdList = frontCategoryService.findIdListRecursive(frontCategoryId);
        // 获取与其关联的后端类目ID列表
        List<String> categoryIdList = categoryRelationService.findCategoryIdList(frontCategoryIdList);
        if (CollectionUtil.isEmpty(categoryIdList)) {
            return null;
        }
        // 查询后端类目关联的优惠券/组合包ID列表
        List<String> typeIdList = categoryConnectRelationService.findTypeIdList(categoryIdList);
        if (CollectionUtil.isEmpty(typeIdList)) {
            return null;
        }
        // 查询优惠券/组合包ID列表关联的活动ID列表
        return activityCouponRelationService.findActivityIdList(typeIdList);
    }

    /**
     * 复制对应字段
     *
     * @param list 数据
     * @return List
     */
    private List<IdNameDto> trans(List<com.deepexi.equity.domain.dto.IdNameDto> list) {
        List<IdNameDto> response = Lists.newArrayList();
        if (CollectionUtil.isEmpty(list)) {
            return response;
        }
        for (com.deepexi.equity.domain.dto.IdNameDto dto : list) {
            IdNameDto idNameDto = new IdNameDto();
            BeanUtils.copyProperties(dto, idNameDto);
            response.add(idNameDto);
        }
        return response;
    }


    /**
     * 活动审核列表
     *
     * @param query
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageBean findAuditPage(ActivityQueryDto query, Integer page, Integer size) {
        PageBean<ActivityListDto> pageBean = this.selectAuditPage(query, page, size);
        List<ActivityListDto> list = pageBean.getContent();
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        for (ActivityListDto activityListDto : list) {
            List<ActivityOrganization> activityOrganizations = activityOrganizationService.selectByActivityId(activityListDto.getId());
            //审核状态字段
            if (activityListDto.getStatus().equals(ActivityStatusEnum.PENDING_REVIEW.getState())) {
                activityListDto.setAuditStatus(ActivityAuditStatusEnum.PENDING_REVIEW.getState());
            } else if (activityListDto.getStatus().equals(ActivityStatusEnum.PENDING_START.getState())
                    || activityListDto.getStatus().equals(ActivityStatusEnum.PROCESSING.getState())) {
                activityListDto.setAuditStatus(ActivityAuditStatusEnum.SUBMIT.getState());
            } else if (activityListDto.getStatus().equals(ActivityStatusEnum.REJECT.getState())) {
                activityListDto.setAuditStatus(ActivityAuditStatusEnum.REJECT.getState());
            }
            if (CollectionUtil.isEmpty(activityOrganizations)) {
                logger.error("ActivityServiceImpl.findAuditPage：活动[{}]查询不到[创建企业]字段", activityListDto.getId());
                continue;
            }
            //创建企业字段
            activityListDto.setCreateEnterprise(enterprService.getByOrganizationId(activityOrganizations.get(0).getEnterpriseId()).getShortName());
        }
        return pageBean;
    }

    public PageBean<ActivityListDto> selectAuditPage(ActivityQueryDto query, Integer page, Integer size) {
        query.setTenantCode(appRuntimeEnv.getTenantId());
        //判断审核状态
        if (query.getAuditStatus() != null) {
            if (query.getAuditStatus().equals(ActivityAuditStatusEnum.PENDING_REVIEW.getState())) {
                query.setStatusList(Lists.newArrayList(ActivityStatusEnum.PENDING_REVIEW.getState()));
            } else if (query.getAuditStatus().equals(ActivityAuditStatusEnum.SUBMIT.getState())) {
                query.setStatusList(Lists.newArrayList(ActivityStatusEnum.PENDING_START.getState(), ActivityStatusEnum.PROCESSING.getState()));
            } else if (query.getAuditStatus().equals(ActivityAuditStatusEnum.REJECT.getState())) {
                query.setStatusList(Lists.newArrayList(ActivityStatusEnum.REJECT.getState()));
            }
        }
        PageHelper.startPage(page, size);
        List<ActivityListDto> list = activityMapper.findAuditList(query);
        return new PageBean<>(list);
    }
}