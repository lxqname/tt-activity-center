package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.delayed.DelayedSender;
import com.deepexi.activity.domain.dto.IdNumDto;
import com.deepexi.activity.domain.dto.MemberCouponAndPackageDto;
import com.deepexi.activity.domain.dto.MemberCouponH5DetailDto;
import com.deepexi.activity.domain.dto.MemberCouponH5Dto;
import com.deepexi.activity.domain.dto.MemberCouponRelationListDto;
import com.deepexi.activity.domain.dto.MemberCouponRelationQueryDto;
import com.deepexi.activity.domain.dto.NumAmountDto;
import com.deepexi.activity.domain.dto.TargetDto;
import com.deepexi.activity.domain.dto.VerificationStatusCountDto;
import com.deepexi.activity.domain.eo.Activity;
import com.deepexi.activity.domain.eo.ActivityCouponRelation;
import com.deepexi.activity.domain.eo.MemberCouponRelation;
import com.deepexi.activity.enums.ActivityTypeEnum;
import com.deepexi.activity.enums.ReceiveTypeEnum;
import com.deepexi.activity.enums.SortTypeEnum;
import com.deepexi.activity.enums.VerificationStatusEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.MemberCouponRelationMapper;
import com.deepexi.activity.service.ActivityCouponRelationService;
import com.deepexi.activity.service.ActivityService;
import com.deepexi.activity.service.ActivityTargetService;
import com.deepexi.activity.service.MemberCouponRelationService;
import com.deepexi.activity.service.MemberEnterpriseService;
import com.deepexi.activity.service.MemberService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.activity.utils.FieldCopyUtils;
import com.deepexi.activity.utils.PageBeanUtils;
import com.deepexi.business.domain.dto.StoreQueryDto;
import com.deepexi.business.domain.eo.Store;
import com.deepexi.business.domain.vo.StorePopupVo;
import com.deepexi.business.service.StoreService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.enums.YesNoEnum;
import com.deepexi.common.util.DateUtils;
import com.deepexi.equity.domain.dto.CouponAndPackageDto;
import com.deepexi.equity.domain.dto.CouponDto;
import com.deepexi.equity.domain.dto.CouponQueryDto;
import com.deepexi.equity.domain.eo.Coupon;
import com.deepexi.equity.domain.eo.CouponPackageRelation;
import com.deepexi.equity.domain.eo.EquityOrganization;
import com.deepexi.equity.enums.ConnectTypeEnum;
import com.deepexi.equity.enums.CouponUseTimeFlagEnum;
import com.deepexi.equity.enums.CouponVerificationStoreEnum;
import com.deepexi.equity.service.CouponEquityService;
import com.deepexi.equity.service.CouponPackageRelationService;
import com.deepexi.equity.service.CouponPackageService;
import com.deepexi.equity.service.CouponService;
import com.deepexi.equity.service.CouponStoreRelationService;
import com.deepexi.equity.service.CouponUniqueCodeService;
import com.deepexi.equity.service.EquityOrganizationService;
import com.deepexi.member.domain.eo.Member;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.JsonUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 会员优惠券服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class MemberCouponRelationServiceImpl implements MemberCouponRelationService {

    private static final Logger logger = LoggerFactory.getLogger(MemberCouponRelationServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private MemberCouponRelationMapper memberCouponRelationMapper;

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Resource
    private DelayedSender sender;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberEnterpriseService memberEnterpriseService;

    @Resource
    private ActivityCouponRelationService activityCouponRelationService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityTargetService activityTargetService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponPackageRelationService couponPackageRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponPackageService couponPackageService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponService couponService;

    @Reference(version = "${demo.service.version}", check = false)
    private StoreService storeService;

    @Reference(version = "${demo.service.version}", check = false)
    private EquityOrganizationService equityOrganizationService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponEquityService couponEquityService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponStoreRelationService couponStoreRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponUniqueCodeService couponUniqueCodeService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Override
    public void createByCoupon(String memberId, String couponId, String activityCouponRelationId, String activityId, Integer verificationStatus, Integer receiveType) {
        MemberCouponRelation memberCouponRelation = new MemberCouponRelation();
        memberCouponRelation.setMemberId(memberId);
        memberCouponRelation.setCouponId(couponId);
        // 查询优惠券信息
        Coupon coupon = this.getCoupon(couponId, null);
        FieldCopyUtils.copy(coupon, memberCouponRelation);
        // 锁定优惠券唯一编码
        memberCouponRelation.setCouponUniqueCode(couponUniqueCodeService.lockUniqueCode(couponId));
        memberCouponRelation.setActivityCouponRelationId(activityCouponRelationId);
        memberCouponRelation.setActivityId(activityId);
        memberCouponRelation.setVerificationStatus(verificationStatus);
        memberCouponRelation.setReceiveType(receiveType);
        if (Objects.equals(VerificationStatusEnum.PENDING_USE.getState(), verificationStatus)) {
            memberCouponRelation.setReceiveTime(new Date());
        }
        this.insert(memberCouponRelation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createByCouponPackage(String memberId, String couponPackageId, String activityCouponRelationId, String activityId, Integer verificationStatus, Integer receiveType) {
        // 查询优惠券组合包关系
        List<CouponPackageRelation> relationList = couponPackageRelationService.findByCouponPackageId(couponPackageId);
        if (CollectionUtil.isEmpty(relationList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询优惠券与组合包关系数据失败"));
        }
        Map<String, Coupon> couponMap = Maps.newHashMap();
        for (CouponPackageRelation relation : relationList) {
            for (int i = 0; i < relation.getNum(); i++) {
                MemberCouponRelation memberCouponRelation = new MemberCouponRelation();
                memberCouponRelation.setMemberId(memberId);
                memberCouponRelation.setCouponId(relation.getCouponId());
                // 查询优惠券信息
                Coupon coupon = this.getCoupon(relation.getCouponId(), couponMap);
                FieldCopyUtils.copy(coupon, memberCouponRelation);
                // 锁定优惠券唯一编码
                memberCouponRelation.setCouponUniqueCode(couponUniqueCodeService.lockUniqueCode(memberCouponRelation.getCouponId()));
                memberCouponRelation.setCouponPackageId(relation.getCouponPackageId());
                memberCouponRelation.setActivityCouponRelationId(activityCouponRelationId);
                memberCouponRelation.setActivityId(activityId);
                memberCouponRelation.setVerificationStatus(verificationStatus);
                memberCouponRelation.setReceiveType(receiveType);
                if (Objects.equals(VerificationStatusEnum.PENDING_USE.getState(), verificationStatus)) {
                    memberCouponRelation.setReceiveTime(new Date());
                }
                this.insert(memberCouponRelation);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createForTrade(String memberId, Integer type, String typeId, String productId, String orderId, String orderAwardId, Integer verificationStatus, Integer receiveType) {
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        MemberCouponRelation memberCouponRelation = new MemberCouponRelation();
        memberCouponRelation.setMemberId(memberId);
        memberCouponRelation.setProductId(productId);
        memberCouponRelation.setOrderId(orderId);
        memberCouponRelation.setOrderAwardId(orderAwardId);
        memberCouponRelation.setVerificationStatus(verificationStatus);
        memberCouponRelation.setReceiveType(receiveType);
        if (Objects.equals(VerificationStatusEnum.PENDING_USE.getState(), verificationStatus)) {
            memberCouponRelation.setReceiveTime(new Date());
        }
        if (Objects.equals(ConnectTypeEnum.COUPON.getState(), type)) {
            // 优惠券
            memberCouponRelation.setCouponId(typeId);
            // 查询优惠券信息
            Coupon coupon = this.getCoupon(typeId, null);
            FieldCopyUtils.copy(coupon, memberCouponRelation);
            // 锁定优惠券唯一编码
            memberCouponRelation.setCouponUniqueCode(couponUniqueCodeService.lockUniqueCode(memberCouponRelation.getCouponId()));
            this.insert(memberCouponRelation);
        } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), type)) {
            // 组合包，查询优惠券组合包关系
            List<CouponPackageRelation> relationList = couponPackageRelationService.findByCouponPackageId(typeId);
            if (CollectionUtil.isEmpty(relationList)) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询优惠券与组合包关系数据失败"));
            }
            Map<String, Coupon> couponMap = Maps.newHashMap();
            for (CouponPackageRelation relation : relationList) {
                for (int i = 0; i < relation.getNum(); i++) {
                    if (StringUtils.isNotBlank(memberCouponRelation.getId())) {
                        memberCouponRelation.setId(null);
                    }
                    memberCouponRelation.setCouponId(relation.getCouponId());
                    // 查询优惠券信息
                    Coupon coupon = this.getCoupon(relation.getCouponId(), couponMap);
                    FieldCopyUtils.copy(coupon, memberCouponRelation);
                    // 锁定优惠券唯一编码
                    memberCouponRelation.setCouponUniqueCode(couponUniqueCodeService.lockUniqueCode(memberCouponRelation.getCouponId()));
                    memberCouponRelation.setCouponPackageId(relation.getCouponPackageId());
                    this.insert(memberCouponRelation);
                }
            }
        } else {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "关联类型参数异常"));
        }
    }

    @Override
    public Boolean updateById(MemberCouponRelation memberCouponRelation) {
        if (memberCouponRelation == null) {
            logger.error("MemberCouponRelationServiceImpl.updateById：入参为null");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常!"));
        }
        int updateResult = memberCouponRelationMapper.updateById(memberCouponRelation);
        return updateResult == 1;
    }

    @Override
    public Boolean delete(List<String> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员优惠券关系删除入参为null"));
        }
        int deleteResult = memberCouponRelationMapper.deleteByIds(idList, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (deleteResult != idList.size()) {
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        return Boolean.TRUE;
    }

    @Override
    public Integer count(MemberCouponRelationQueryDto query) {
        if (query == null) {
            query = new MemberCouponRelationQueryDto();
        }
        query.setTenantCode(appRuntimeEnv.getTenantId());
        return memberCouponRelationMapper.count(query);
    }

    @Override
    public List<MemberCouponAndPackageDto> findActivityAwardList(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        // 查询会员信息
        String loginMemberId = memberService.getLoginMemberId();

        MemberCouponRelationQueryDto queryDto = new MemberCouponRelationQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setMemberId(loginMemberId);
        queryDto.setActivityId(activityId);
        List<MemberCouponAndPackageDto> list = memberCouponRelationMapper.selectActivityAwardList(queryDto);
        list.parallelStream().forEach(dto -> {
                    com.deepexi.equity.domain.dto.CouponAndPackageDto couponAndPackageDto =
                            couponPackageService.queryCouponAndPackage(dto.getType(), dto.getTypeId());
                    dto.setTypeDetail(FieldCopyUtils.copy(couponAndPackageDto));
                }
        );
        return list;
    }

    @Override
    public PageBean<MemberCouponAndPackageDto> findAwardPage(Integer page, Integer size) {
        // 查询会员信息
        String loginMemberId = memberService.getLoginMemberId();

        MemberCouponRelationQueryDto queryDto = new MemberCouponRelationQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setMemberId(loginMemberId);
        queryDto.setVerificationStatus(VerificationStatusEnum.PENDING_RECEIVE.getState());
        PageHelper.startPage(page, size);
        List<MemberCouponAndPackageDto> list = memberCouponRelationMapper.selectActivityAwardList(queryDto);
        PageBean<MemberCouponAndPackageDto> pageBean = new PageBean<>(list);
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        list.parallelStream().forEach(dto -> {
                    com.deepexi.equity.domain.dto.CouponAndPackageDto couponAndPackageDto =
                            couponPackageService.queryCouponAndPackage(dto.getType(), dto.getTypeId());
                    dto.setTypeDetail(FieldCopyUtils.copy(couponAndPackageDto));
                    // 查询活动名称
                    if (StringUtils.isNotBlank(dto.getActivityId())) {
                        Activity activity = activityService.selectById(dto.getActivityId(), true);
                        dto.setActivityName(activity.getName());
                    }
                }
        );
        return pageBean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean receive(List<String> idList) {
        // 查询会员信息
        String loginMemberId = memberService.getLoginMemberId();

        int receiveResult = memberCouponRelationMapper.receive(idList, loginMemberId, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (receiveResult != idList.size()) {
            logger.error("MemberCouponRelationServiceImpl.receive：领取失败，主键ID列表为[{}]，返回处理结果为[{}]",
                    idList.toString(), receiveResult);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，领取失败"));
        }

        // 新增会员与企业关系
        memberEnterpriseService.createByMemberCouponRelationId(idList);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReservationByReceiveType(String activityId, String memberId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        // 根据会员ID、活动ID查询预定奖品信息
        MemberCouponRelationQueryDto query = new MemberCouponRelationQueryDto();
        query.setTenantCode(appRuntimeEnv.getTenantId());
        query.setMemberId(memberId);
        query.setActivityId(activityId);
        query.setVerificationStatus(VerificationStatusEnum.RESERVATION.getState());
        List<MemberCouponRelationListDto> list = memberCouponRelationMapper.findList(query);
        if (CollectionUtil.isEmpty(list)) {
            logger.info("MemberCouponRelationServiceImpl.updateReservationByReceiveType：查询预定会员优惠券信息为null，活动ID为[{}]，会员ID为[{}]", activityId, memberId);
            return;
        }
        for (MemberCouponRelationListDto dto : list) {
            int updateResult = 0;
            if (Objects.equals(ReceiveTypeEnum.GET_ON_SYSTEM.getState(), dto.getReceiveType())) {
                // 系统发放
                updateResult = memberCouponRelationMapper.updateReservation(dto.getId(), VerificationStatusEnum.PENDING_USE.getState(),
                        BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
            } else if (Objects.equals(ReceiveTypeEnum.GET_ON_LINE.getState(), dto.getReceiveType())) {
                // 线上领取
                updateResult = memberCouponRelationMapper.updateReservation(dto.getId(), VerificationStatusEnum.PENDING_RECEIVE.getState(),
                        BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
            }
            if (updateResult != 1) {
                logger.error("MemberCouponRelationServiceImpl.updateReservation：更新数据操作失败，返回值为[{}]，主键ID为[{}]",
                        updateResult, dto.getId());
                throw new ApplicationException(ResultEnum.DATABASE_ERROR);
            }
            if (Objects.equals(ReceiveTypeEnum.GET_ON_SYSTEM.getState(), dto.getReceiveType())) {
                // 新增会员与企业关系
                memberEnterpriseService.create(dto.getMemberId(), dto.getCouponId(), dto.getActivityId());
            }
        }
    }

    @Override
    public List<MemberCouponRelationListDto> findAllWithOnlyEo(MemberCouponRelationQueryDto query) {
        if (query == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，查询条件为null"));
        }
        if (StringUtils.isBlank(query.getTenantCode())) {
            query.setTenantCode(appRuntimeEnv.getTenantId());
        }
        return memberCouponRelationMapper.findList(query);
    }

    @Override
    public PageBean<MemberCouponRelationListDto> findPage(MemberCouponRelationQueryDto query, Integer page, Integer size) {
        Map<String, Member> memberMap = Maps.newHashMap();
        Map<String, Coupon> couponMap = Maps.newHashMap();
        if (StringUtils.isNotBlank(query.getMemberMobile())) {
            memberMap = memberService.findAllByMobile(query.getMemberMobile());
            if (CollectionUtil.isEmpty(memberMap)) {
                return PageBeanUtils.getDefaultPageBean();
            }
            query.setMemberIdList(Lists.newArrayList(memberMap.keySet()));
        }
        if (StringUtils.isNotBlank(query.getCouponName())) {
            couponMap = this.findCouponMap(query.getCouponName());
            if (CollectionUtil.isEmpty(couponMap)) {
                return PageBeanUtils.getDefaultPageBean();
            }
            query.setCouponIdList(Lists.newArrayList(couponMap.keySet()));
        }
        PageBean<MemberCouponRelationListDto> pageBean = this.findPageWithOnlyEo(query, page, size);
        List<MemberCouponRelationListDto> list = pageBean.getContent();
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        for (MemberCouponRelationListDto dto : list) {
            Member member = memberMap.get(dto.getMemberId());
            if (member == null) {
                member = memberService.getMemberById(dto.getMemberId());
                memberMap.put(member.getId(), member);
            }
            dto.setMemberMobile(member.getMobile());
            Coupon coupon = this.getCoupon(dto.getCouponId(), couponMap);
            dto.setCouponName(coupon.getName());
            dto.setCouponFaceValue(coupon.getFaceValue());
            dto.setCouponUseLimitFlag(coupon.getUseLimitFlag());
            dto.setCouponUseDivRemark(coupon.getUseDivRemark());
            dto.setCouponFullAmount(coupon.getFullAmount());
            if (StringUtils.isNotBlank(dto.getVerificationStoreId())) {
                Store store = storeService.detail(dto.getVerificationStoreId());
                dto.setVerificationStoreName(store != null ? store.getName() : null);
            }
            if (Objects.equals(YesNoEnum.YES.getState(), query.getQueryActivityCouponRelation())) {
                ActivityCouponRelation activityCouponRelation = activityCouponRelationService.selectById(dto.getActivityCouponRelationId());
                dto.setActivityCouponRelationTitle(activityCouponRelation.getTitle());
            }
        }
        return pageBean;
    }

    @Override
    public VerificationStatusCountDto countNumAndAmountByStatus(String activityId) {
        VerificationStatusCountDto response = new VerificationStatusCountDto();
        Map<String, Coupon> couponMap = Maps.newHashMap();
        // 待领取
        NumAmountDto pendingReceive = this.countNumAndAmountByStatus(activityId, VerificationStatusEnum.PENDING_RECEIVE.getState(), couponMap);
        response.setPendingReceiveNum(pendingReceive.getNum());
        response.setPendingReceiveAmount(pendingReceive.getAmount());
        // 待核销
        NumAmountDto pendingUse = this.countNumAndAmountByStatus(activityId, VerificationStatusEnum.PENDING_USE.getState(), couponMap);
        response.setPendingUseNum(pendingUse.getNum());
        response.setPendingUseAmount(pendingUse.getAmount());
        // 已核销
        NumAmountDto used = this.countNumAndAmountByStatus(activityId, VerificationStatusEnum.USED.getState(), couponMap);
        response.setUsedNum(used.getNum());
        response.setUsedAmount(used.getAmount());
        // 已过期
        NumAmountDto expired = this.countNumAndAmountByStatus(activityId, VerificationStatusEnum.EXPIRED.getState(), couponMap);
        response.setExpiredNum(expired.getNum());
        response.setExpiredAmount(expired.getAmount());
        // 已领取 = 待核销 + 已核销 + 已过期
        response.setReceiveNum(response.getPendingUseNum() + response.getUsedNum() + response.getExpiredNum());
        response.setReceiveAmount(response.getPendingUseAmount().add(response.getUsedAmount()).add(response.getExpiredAmount()));
        return response;
    }

    @Override
    public Boolean sendCoupon(String memberId, String couponId) {
        // 验证会员必须存在
        memberService.getMemberById(memberId);

        int sendNum = 1;
        List<String> couponIds = occupyCouponOrCouponPackage(couponId, sendNum);
        if (CollectionUtil.isEmpty(couponIds)) {
            return false;
        }
        Map<String, Coupon> couponMap = Maps.newHashMap();
        couponIds.forEach(c->{
            MemberCouponRelation memberCouponRelation = new MemberCouponRelation();
            memberCouponRelation.setMemberId(memberId);
            memberCouponRelation.setVerificationStatus(VerificationStatusEnum.PENDING_USE.getState());
            memberCouponRelation.setReceiveType(ReceiveTypeEnum.GET_ON_SYSTEM.getState());
            memberCouponRelation.setReceiveTime(new Date());
            memberCouponRelation.setCouponId(c);
            // 查询优惠券信息
            Coupon coupon = this.getCoupon(c, couponMap);
            FieldCopyUtils.copy(coupon, memberCouponRelation);
            memberCouponRelationMapper.insert(memberCouponRelation);
        });

        return true;
    }

    /**
     * 发送优惠卷
     * @param couponId
     * @param sendNum
     * @return 优惠卷ID list
     */
    private List<String> occupyCouponOrCouponPackage(String couponId, int sendNum) {
        List<String> couponIds = new ArrayList<>();
        Coupon coupon = couponService.selectById(couponId, false);
        if (coupon != null){
            try {
                couponService.subRemainNum(couponId, sendNum);
                couponIds.add(couponId);
            } catch (Exception e) {
                logger.error("库存不足，锁定优惠卷失败,异常【{}】",e.getMessage());
            }
        }else {
            try {
                couponPackageService.occupyCouponNum(couponId, sendNum);
                List<String> couponIdList = couponPackageRelationService.findCouponIdList(Arrays.asList(couponId));
                couponIds.addAll(couponIdList);
            } catch (Exception e) {
                logger.error("库存不足，锁定组合优惠包失败,异常【{}】",e.getMessage());
            }
        }
        return couponIds;
    }

    @Override
    public PageBean<MemberCouponH5Dto> findMyCoupon(MemberCouponRelationQueryDto query, Integer page, Integer size) {
        // 查询登录会员ID
        query.setMemberId(memberService.getLoginMemberId());
        query.setTenantCode(appRuntimeEnv.getTenantId());
        PageHelper.startPage(page, size);
        List<MemberCouponH5Dto> myCouponList = memberCouponRelationMapper.findMyCouponList(query);
        PageBean<MemberCouponH5Dto> pageBean = new PageBean<>(myCouponList);
        if (CollectionUtil.isEmpty(myCouponList)) {
            return pageBean;
        }
        Map<String, Object> couponMap = Maps.newHashMap();
        for (MemberCouponH5Dto memberCouponH5Dto : myCouponList) {
            Object coupon = couponMap.get(memberCouponH5Dto.getCouponId());
            if (coupon == null) {
                coupon = couponService.queryCouponAndPackage(memberCouponH5Dto.getCouponId());
                couponMap.put(memberCouponH5Dto.getCouponId(), coupon);
            }
            memberCouponH5Dto.setCoupon(coupon);
        }
        return pageBean;
    }

    @Override
    public MemberCouponH5DetailDto myCouponDetail(String id) {
        MemberCouponRelation memberCouponRelation = this.selectById(id, true);
        MemberCouponH5DetailDto response = new MemberCouponH5DetailDto();
        BeanUtils.copyProperties(memberCouponRelation, response);
        // 查询优惠券信息
        CouponDto couponDto = couponService.queryAllInfo(memberCouponRelation.getCouponId());
        response.setCoupon(couponDto);
        if (StringUtils.isNotBlank(response.getCouponPackageId())) {
            // 查询优惠券组合包信息
            CouponAndPackageDto couponPackageInfo = couponPackageService.queryCouponAndPackageOnlyEo(ConnectTypeEnum.COUPON_PACKAGE.getState(), response.getCouponPackageId());
            response.setCouponPackageCode(couponPackageInfo.getCode());
        }
        // 计算使用时间范围
        if (Objects.equals(CouponUseTimeFlagEnum.APPOINT_TIME.getState(), memberCouponRelation.getCouponUseTimeFlag())) {
            response.setUseStartTime(memberCouponRelation.getCouponUseStartTime());
            response.setUseEndTime(memberCouponRelation.getCouponUseEndTime());
        } else if (Objects.equals(CouponUseTimeFlagEnum.TIME_AFTER_RECEIVE.getState(), memberCouponRelation.getCouponUseTimeFlag())) {
            response.setUseStartTime(memberCouponRelation.getReceiveTime());
            Date receiveTime = memberCouponRelation.getReceiveTime() == null ? new Date() : memberCouponRelation.getReceiveTime();
            response.setUseEndTime(DateUtils.addDay(receiveTime, memberCouponRelation.getCouponValidDay()));
        }
        // 查询活动信息
        if (StringUtils.isNotBlank(memberCouponRelation.getActivityId())) {
            Activity activity = activityService.selectById(memberCouponRelation.getActivityId(), true);
            response.setActivityType(activity.getType());
            response.setActivityName(activity.getName());
            if (Objects.equals(ActivityTypeEnum.RECEIVE_COUPON_ACTIVITY.getState(), activity.getType())) {
                // 查询领取条件
                TargetDto targetDto = activityTargetService.queryTargetInfo(activity.getId());
                if (targetDto != null) {
                    response.setTargetType(targetDto.getTargetType());
                }
            }
        }
        return response;
    }

    @Override
    public Boolean checkUseStore(String id, String storeId) {
        MemberCouponRelation memberCouponRelation = this.selectById(id, true);
        // 查询优惠券
        Coupon coupon = couponService.selectById(memberCouponRelation.getCouponId(), true);
        return this.checkUseStore(coupon.getId(), coupon.getVerificationStoreType(), storeId);
    }

    /**
     * 校验当前门店是否为可核销门店
     *
     * @param couponId                    优惠券ID
     * @param couponVerificationStoreType 优惠券核销门店类型
     * @param storeId                     门店ID
     * @return Boolean
     */
    private Boolean checkUseStore(String couponId, Integer couponVerificationStoreType, String storeId) {
        if (Objects.equals(CouponVerificationStoreEnum.All.getState(), couponVerificationStoreType)) {
            // 查询关联权益ID
            String equityId = couponEquityService.findEquityId(couponId);
            // 查询权益关联的部门
            EquityOrganization equityOrganization = equityOrganizationService.selectByEquityId(equityId);
            // 查询门店列表
            StoreQueryDto storeQueryDto = new StoreQueryDto();
            storeQueryDto.setEnterprOrganizationId(equityOrganization.getEnterpriseId());
            storeQueryDto.setDepartmentOrganizationId(equityOrganization.getOrganizationId());
            List<StorePopupVo> storePopupVoList = storeService.listByEnterprDepartment(storeQueryDto);
            if (CollectionUtil.isEmpty(storePopupVoList)) {
                logger.info("MemberCouponRelationServiceImpl.checkUseStore：查询不到企业[{}]部门[{}]下的门店列表",
                        equityOrganization.getEnterpriseId(), equityOrganization.getOrganizationId());
                return Boolean.FALSE;
            }
            for (StorePopupVo storePopupVo : storePopupVoList) {
                if (Objects.equals(storePopupVo.getId(), storeId)) {
                    return Boolean.TRUE;
                }
            }
        } else if (Objects.equals(CouponVerificationStoreEnum.PART.getState(), couponVerificationStoreType)) {
            List<String> storeIdList = couponStoreRelationService.findStoreIdList(couponId);
            if (CollectionUtil.isEmpty(storeIdList)) {
                logger.info("MemberCouponRelationServiceImpl.checkUseStore：查询不到优惠券[{}]关联的门店列表", couponId);
                return Boolean.FALSE;
            }
            for (String s : storeIdList) {
                if (Objects.equals(s, storeId)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 校验优惠券使用时间有效性
     *
     * @param useTimeFlag  使用时间类型
     * @param useStartTime 使用开始时间
     * @param useEndTime   使用结束时间
     * @param validDay     有效天数
     * @param receiveTime  领取时间
     * @return Boolean true有效，false无效
     */
    private Boolean checkCouponValid(Integer useTimeFlag, Date useStartTime, Date useEndTime, Integer validDay, Date receiveTime) {
        Date now = new Date();
        if (Objects.equals(useTimeFlag, CouponUseTimeFlagEnum.NO_LIMIT.getState())) {
            // 不限制使用时间
            return Boolean.TRUE;
        } else if (Objects.equals(useTimeFlag, CouponUseTimeFlagEnum.APPOINT_TIME.getState())) {
            // 指定时间
            if (useStartTime == null) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，优惠券使用开始时间为null"));
            }
            if (useEndTime == null) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，优惠券使用结束时间为null"));
            }
            return useStartTime.before(now) && now.before(useEndTime);
        } else if (Objects.equals(useTimeFlag, CouponUseTimeFlagEnum.TIME_AFTER_RECEIVE.getState())) {
            // 自领取后天数
            if (validDay == null) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，优惠券有效天数为null"));
            }
            if (receiveTime == null) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，优惠券领取时间为null"));
            }
            Date endTime = DateUtils.addDay(receiveTime, validDay);
            return receiveTime.before(now) && now.before(endTime);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean use(String id, String storeId) {
        MemberCouponRelation memberCouponRelation = this.selectById(id, true);
        if (!Objects.equals(VerificationStatusEnum.PENDING_USE.getState(), memberCouponRelation.getVerificationStatus())) {
            logger.error("MemberCouponRelationServiceImpl.use：会员奖品项关系[{}]当前核销状态为[{}]，不可进行核销",
                    id, memberCouponRelation.getVerificationStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.DATABASE_ERROR, "当前状态不可进行核销"));
        }
        // 查询优惠券
        Coupon coupon = couponService.selectById(memberCouponRelation.getCouponId(), true);
        // 优惠券使用时间校验
        if (!this.checkCouponValid(coupon.getUseTimeFlag(), coupon.getUseStartTime(), coupon.getUseEndTime(), coupon.getValidDay(), memberCouponRelation.getReceiveTime())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "优惠券已失效"));
        }
        // 校验是否可核销门店
        if (!this.checkUseStore(coupon.getId(), coupon.getVerificationStoreType(), storeId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请到正确门店核销优惠券"));
        }

        // 核销
        int useResult = memberCouponRelationMapper.use(id, storeId, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (useResult != 1) {
            logger.error("MemberCouponRelationServiceImpl.use：核销失败，主键ID为[{}]，返回处理结果为[{}]", id, useResult);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，核销失败"));
        }
        // 新增会员与企业关系
        memberEnterpriseService.createByUse(memberCouponRelation.getMemberId(), memberCouponRelation.getCouponId(),
                memberCouponRelation.getActivityId(), memberCouponRelation.getProductId(), storeId);
        return Boolean.TRUE;
    }

    @Override
    public PageBean<MemberCouponRelationListDto> findVerificationRecordPage(Date startTime, Date endTime, Integer page, Integer size) {
        String loginAccountId = BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken());
        if (StringUtils.isBlank(loginAccountId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "登录账号异常"));
        }
        MemberCouponRelationQueryDto queryDto = new MemberCouponRelationQueryDto();
        queryDto.setVerificationStatus(VerificationStatusEnum.USED.getState());
        queryDto.setVerificationTimeLeftRange(startTime);
        queryDto.setVerificationTimeRightRange(endTime);
        queryDto.setSortType(SortTypeEnum.VERIFICATION_TIME_DESC.getState());
        PageBean<MemberCouponRelationListDto> pageBean = this.findPageWithOnlyEo(queryDto, page, size);
        List<MemberCouponRelationListDto> list = pageBean.getContent();
        if (CollectionUtil.isEmpty(list)) {
            return pageBean;
        }
        Map<String, Coupon> couponMap = Maps.newHashMap();
        for (MemberCouponRelationListDto dto : list) {
            Coupon coupon = this.getCoupon(dto.getCouponId(), couponMap);
            dto.setCouponName(coupon.getName());
            dto.setCouponFaceValue(coupon.getFaceValue());
            // 查询账号名称
            if (StringUtils.isNotBlank(dto.getVerificationPersonAccountId())) {
                String accountUsername = accountService.getUsernameById(dto.getVerificationPersonAccountId());
                dto.setVerificationPersonAccountName(StringUtils.isBlank(accountUsername) ? "" : accountUsername);
            }
            // 查询门店名称
            if (StringUtils.isNotBlank(dto.getVerificationStoreId())) {
                Store store = storeService.detail(dto.getVerificationStoreId());
                dto.setVerificationStoreName(store != null ? store.getName() : null);
            }
        }
        return pageBean;
    }

    @Override
    public MemberCouponH5Dto detailForMerchant(String id) {
        MemberCouponRelation memberCouponRelation = this.selectById(id, true);
        MemberCouponH5Dto response = new MemberCouponH5Dto();
        response.copy(memberCouponRelation);
        // 计算使用时间
        if (Objects.equals(CouponUseTimeFlagEnum.APPOINT_TIME.getState(), memberCouponRelation.getCouponUseTimeFlag())) {
            response.setUseStartTime(memberCouponRelation.getCouponUseStartTime());
            response.setUseEndTime(memberCouponRelation.getCouponUseEndTime());
        } else if (Objects.equals(CouponUseTimeFlagEnum.TIME_AFTER_RECEIVE.getState(), memberCouponRelation.getCouponUseTimeFlag())) {
            response.setUseStartTime(memberCouponRelation.getReceiveTime());
            response.setUseEndTime(DateUtils.addDay(memberCouponRelation.getReceiveTime(), memberCouponRelation.getCouponValidDay()));
        }
        // 查询优惠券信息
        response.setCoupon(couponService.queryCouponAndPackage(memberCouponRelation.getCouponId()));
        return response;
    }

    @Override
    public Date calculateCouponExpireTime(Integer couponUseTimeFlag, Date couponUseEndTime, Integer couponValidDay, Date receiveTime) {
        if (Objects.equals(CouponUseTimeFlagEnum.APPOINT_TIME.getState(), couponUseTimeFlag)) {
            return couponUseEndTime;
        } else if (Objects.equals(CouponUseTimeFlagEnum.TIME_AFTER_RECEIVE.getState(), couponUseTimeFlag)) {
            return DateUtils.addDay(receiveTime, couponValidDay);
        }
        return null;
    }

    @Override
    public Integer couponExpireSoonRemind() {
        // 查询优惠券有效期在当前时间后的三天到四天之间有效的数据
        List<MemberCouponRelation> expireSoonList = memberCouponRelationMapper.findExpireSoonList();
        if (CollectionUtil.isEmpty(expireSoonList)) {
            logger.info("查询不到即将过期的优惠券信息");
            return 0;
        }
        for (MemberCouponRelation relation : expireSoonList) {
            // 过期时间
            Date expireTime = this.calculateCouponExpireTime(relation.getCouponUseTimeFlag(), relation.getCouponUseEndTime(), relation.getCouponValidDay(), relation.getReceiveTime());
            // 获取提醒的时间点
            Date remindTime = DateUtils.addDay(expireTime, -3);
            if (remindTime.getTime() > System.currentTimeMillis()) {
                sender.send(JsonUtil.bean2JsonString(relation), remindTime.getTime() - System.currentTimeMillis());
            } else {
                sender.send(JsonUtil.bean2JsonString(relation), 0);
            }
        }
        return expireSoonList.size();
    }

    @Override
    public Integer expire() {
        int effectRows = 0;
        // 查询已过期会员优惠券
        List<MemberCouponRelation> expiredList = memberCouponRelationMapper.selectUnusedExpiredList();
        if (CollectionUtil.isEmpty(expiredList)) {
            return effectRows;
        }
        for (MemberCouponRelation expiredMemberCoupon : expiredList) {
            appRuntimeEnv.setTenantId(expiredMemberCoupon.getTenantCode());
            // 各个会员优惠券过期互不影响
            try {
                Boolean result = memberCouponRelationService.expire(expiredMemberCoupon);
                if (result) {
                    effectRows = effectRows + 1;
                }
            } catch (Exception e) {
                logger.error("MemberCouponRelationServiceImpl.expire()：会员优惠券过期操作失败，异常信息如下：", e);
            }
        }
        return effectRows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean expire(MemberCouponRelation expiredMemberCoupon) {
        if (null == expiredMemberCoupon) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "入参会员优惠券数据为null"));
        }
        int updateResult = memberCouponRelationMapper.expire(expiredMemberCoupon.getId(), BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (updateResult != 1) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "更新会员优惠券状态失败"));
        }

        // 返还权益金额
        couponService.returnEquityAmount(expiredMemberCoupon.getCouponId(), 1);
        return Boolean.TRUE;
    }

    @Override
    public List<String> findIdList(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        MemberCouponRelationQueryDto queryDto = new MemberCouponRelationQueryDto();
        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        queryDto.setMemberId(memberService.getLoginMemberId());
        queryDto.setActivityId(activityId);
        return memberCouponRelationMapper.findIdList(queryDto);
    }

    /**
     * 根据活动ID及核销状态统计数量及金额
     *
     * @param activityId         活动ID
     * @param verificationStatus 核销状态
     * @param couponMap          优惠券Map
     * @return NumAmountDto
     */
    private NumAmountDto countNumAndAmountByStatus(String activityId, Integer verificationStatus, Map<String, Coupon> couponMap) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        if (verificationStatus == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，核销状态为null"));
        }
        int num = 0;
        BigDecimal amount = BigDecimal.ZERO;
        List<IdNumDto> list = memberCouponRelationMapper.countNumByStatus(appRuntimeEnv.getTenantId(), activityId, verificationStatus);
        if (CollectionUtil.isNotEmpty(list)) {
            for (IdNumDto idNumDto : list) {
                num = num + idNumDto.getNum();
                Coupon coupon = couponMap.get(idNumDto.getId());
                if (coupon == null) {
                    coupon = couponService.selectById(idNumDto.getId(), true);
                    couponMap.put(coupon.getId(), coupon);
                }
                amount = amount.add(coupon.getFaceValue().multiply(new BigDecimal(idNumDto.getNum())));
            }
        }
        NumAmountDto response = new NumAmountDto();
        response.setNum(num);
        response.setAmount(amount);
        return response;
    }

    /**
     * 数据入库
     *
     * @param memberCouponRelation 数据
     */
    private void insert(MemberCouponRelation memberCouponRelation) {
        if (memberCouponRelation == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，保存数据为null!"));
        }
        int insertResult = memberCouponRelationMapper.insert(memberCouponRelation);
        if (insertResult != 1) {
            logger.error("MemberCouponRelationServiceImpl.insert：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    /**
     * 根据ID查询
     *
     * @param id             主键ID
     * @param throwException 查询为null是否抛异常
     * @return eo数据
     */
    private MemberCouponRelation selectById(String id, boolean throwException) {
        MemberCouponRelation memberCouponRelation = memberCouponRelationMapper.selectById(id);
        if (memberCouponRelation == null && throwException) {
            logger.error("MemberCouponRelationServiceImpl.selectById：查询会员优惠券数据失败，ID为[{}]", id);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询会员优惠券数据失败"));
        }
        return memberCouponRelation;
    }

    /**
     * 分页查询-仅包含MemberCouponRelation数据
     *
     * @param query 查询条件
     * @param page  页码
     * @param size  页大小
     * @return PageBean
     */
    private PageBean<MemberCouponRelationListDto> findPageWithOnlyEo(MemberCouponRelationQueryDto query, Integer page, Integer size) {
        query.setTenantCode(appRuntimeEnv.getTenantId());
        PageHelper.startPage(page, size);
        List<MemberCouponRelationListDto> list = memberCouponRelationMapper.findList(query);
        return new PageBean<>(list);
    }

    /**
     * 根据优惠券名称查询优惠券信息
     *
     * @param couponName 优惠券名称
     * @return Map[优惠券ID, 优惠券信息]
     */
    private Map<String, Coupon> findCouponMap(String couponName) {
        CouponQueryDto query = new CouponQueryDto();
        query.setName(couponName);
        List<Coupon> couponList = couponService.findEoList(query);
        if (CollectionUtil.isEmpty(couponList)) {
            return null;
        }
        Map<String, Coupon> couponMap = new HashMap<>(couponList.size());
        for (Coupon coupon : couponList) {
            couponMap.put(coupon.getId(), coupon);
        }
        return couponMap;
    }

    /**
     * 获取优惠券数据
     *
     * @param couponId  优惠券ID
     * @param couponMap 优惠券Map[ID，数据] 可不传
     * @return Coupon
     */
    private Coupon getCoupon(String couponId, Map<String, Coupon> couponMap) {
        if (StringUtils.isBlank(couponId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，优惠券ID为null"));
        }
        if (couponMap == null) {
            return couponService.selectById(couponId, true);
        } else {
            Coupon coupon = couponMap.get(couponId);
            if (coupon == null) {
                coupon = couponService.selectById(couponId, true);
                couponMap.put(couponId, coupon);
            }
            return coupon;
        }
    }
}