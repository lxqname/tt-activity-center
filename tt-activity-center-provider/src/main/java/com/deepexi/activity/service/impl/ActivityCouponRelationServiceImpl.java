package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityAwardDto;
import com.deepexi.activity.domain.dto.ActivityAwardSaveDto;
import com.deepexi.activity.domain.dto.ActivityListDto;
import com.deepexi.activity.domain.eo.ActivityCouponRelation;
import com.deepexi.activity.enums.VerificationStatusEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityCouponRelationMapper;
import com.deepexi.activity.service.ActivityCouponRelationService;
import com.deepexi.activity.service.MemberCouponRelationService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.activity.utils.FieldCopyUtils;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.equity.domain.dto.CouponAndPackageDto;
import com.deepexi.equity.domain.dto.CouponPackageQueryDto;
import com.deepexi.equity.domain.dto.CouponQueryDto;
import com.deepexi.equity.domain.eo.Coupon;
import com.deepexi.equity.domain.eo.CouponPackage;
import com.deepexi.equity.enums.ConnectTypeEnum;
import com.deepexi.equity.service.CouponPackageService;
import com.deepexi.equity.service.CouponService;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.boot.autoconfigure.klock.model.LockTimeoutStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 活动-优惠券/组合包关系服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class ActivityCouponRelationServiceImpl implements ActivityCouponRelationService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityCouponRelationServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private ActivityCouponRelationMapper activityCouponRelationMapper;

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponService couponService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponPackageService couponPackageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String activityId, ActivityAwardSaveDto saveDto) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        ActivityCouponRelation activityCouponRelation = new ActivityCouponRelation();
        activityCouponRelation.setActivityId(activityId);
        activityCouponRelation.setType(saveDto.getType());
        activityCouponRelation.setTypeId(saveDto.getTypeId());
        activityCouponRelation.setTitle(StringUtils.isBlank(saveDto.getTitle()) ? "" : saveDto.getTitle());
        activityCouponRelation.setCompleteNum(saveDto.getCompleteNum());
        activityCouponRelation.setNum(saveDto.getNum());
        activityCouponRelation.setRemainNum(saveDto.getNum());
        activityCouponRelation.setReceiveType(saveDto.getReceiveType());
        activityCouponRelation.setReceiveDescription(StringUtils.isBlank(saveDto.getReceiveDescription()) ? "" : saveDto.getReceiveDescription());
        activityCouponRelation.setReceiveLimitNum(saveDto.getReceiveLimitNum() == null ? 1 : saveDto.getReceiveLimitNum());
        int insertResult = activityCouponRelationMapper.insert(activityCouponRelation);
        if (insertResult != 1) {
            logger.error("ActivityCouponRelationServiceImpl.create：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }

        // 占用优惠券数量
        this.occupyCouponNum(activityCouponRelation.getType(), activityCouponRelation.getTypeId(), activityCouponRelation.getNum());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String activityId, List<ActivityAwardSaveDto> saveDtoList) {
        if (CollectionUtil.isEmpty(saveDtoList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动奖品保存数据为null"));
        }
        for (ActivityAwardSaveDto saveDto : saveDtoList) {
            this.create(activityId, saveDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNum(String id, Integer num) {
        if (StringUtils.isBlank(id)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动奖品ID数据为null"));
        }
        if (num == null || num <= 0) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，数量数据异常"));
        }
        int updateResult = activityCouponRelationMapper.addNum(id, num, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (updateResult != 1) {
            logger.error("ActivityCouponRelationServiceImpl.addNum：更新数据操作失败，addNum方法返回值为[]", updateResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        // 查询数据
        ActivityCouponRelation activityCouponRelation = this.selectById(id);

        // 占用优惠券数量
        this.occupyCouponNum(activityCouponRelation.getType(), activityCouponRelation.getTypeId(), num);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByActivityId(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID数据为null"));
        }
        // 返还优惠券数量
        this.returnCouponNum(activityId);

        // 删除关系
        activityCouponRelationMapper.deleteByActivityId(activityId, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
    }

    @Override
    public ActivityCouponRelation selectById(String id) {
        ActivityCouponRelation activityCouponRelation = activityCouponRelationMapper.selectById(id);
        if (activityCouponRelation == null) {
            logger.error("ActivityCouponRelationServiceImpl.selectById：查询数据失败，ID为[{}]", id);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "查询活动奖品数据失败"));
        }
        return activityCouponRelation;
    }

    @Override
    public void fillUpAwardInfo(List<ActivityListDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        List<String> couponIdList = Lists.newArrayList();
        List<String> couponPackageIdList = Lists.newArrayList();
        for (ActivityListDto dto : list) {
            List<ActivityAwardDto> awardDtoList = activityCouponRelationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), dto.getId());
            if (CollectionUtil.isEmpty(awardDtoList)) {
                awardDtoList = Lists.newArrayList();
            }
            dto.setAwardList(awardDtoList);
            for (ActivityAwardDto awardDto : awardDtoList) {
                if (Objects.equals(ConnectTypeEnum.COUPON.getState(), awardDto.getType())) {
                    if (!couponIdList.contains(awardDto.getTypeId())) {
                        couponIdList.add(awardDto.getTypeId());
                    }
                } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), awardDto.getType())) {
                    if (!couponPackageIdList.contains(awardDto.getTypeId())) {
                        couponPackageIdList.add(awardDto.getTypeId());
                    }
                }
            }
        }
        // 查询优惠券列表
        Map<String, Coupon> couponMap = this.queryCouponMap(couponIdList);
        // 查询优惠券组合包列表
        Map<String, CouponPackage> couponPackageMap = this.queryCouponPackageMap(couponPackageIdList);
        for (ActivityListDto dto : list) {
            for (ActivityAwardDto awardDto : dto.getAwardList()) {
                if (Objects.equals(ConnectTypeEnum.COUPON.getState(), awardDto.getType())) {
                    Coupon coupon = couponMap.get(awardDto.getTypeId());
                    if (coupon != null) {
                        awardDto.setTypeName(coupon.getName());
                    }
                } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), awardDto.getType())) {
                    CouponPackage couponPackage = couponPackageMap.get(awardDto.getTypeId());
                    if (couponPackage != null) {
                        awardDto.setTypeName(couponPackage.getName());
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnCouponNum(String activityId) {
        // 查询活动关联奖品数据
        List<ActivityAwardDto> list = activityCouponRelationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activityId);
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for (ActivityAwardDto awardDto : list) {
            this.returnCouponNum(awardDto.getType(), awardDto.getTypeId(), awardDto.getRemainNum());
        }
    }

    /**
     * 占用优惠券数量
     *
     * @param type   类型（1-优惠券、2-优惠券组合包）
     * @param typeId 类型ID
     * @param num    数量
     */
    private void occupyCouponNum(Integer type, String typeId, Integer num) {
        if (Objects.equals(ConnectTypeEnum.COUPON.getState(), type)) {
            couponService.subRemainNum(typeId, num);
        } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), type)) {
            couponPackageService.occupyCouponNum(typeId, num);
        }
    }

    /**
     * 返还优惠券数量
     *
     * @param type   类型（1-优惠券、2-优惠券组合包）
     * @param typeId 类型ID
     * @param num    数量
     */
    private void returnCouponNum(Integer type, String typeId, Integer num) {
        if (num == null || num <= 0) {
            return;
        }
        if (Objects.equals(ConnectTypeEnum.COUPON.getState(), type)) {
            couponService.addRemainNum(typeId, num);
        } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), type)) {
            couponPackageService.returnCouponNum(typeId, num);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnEquityAmount(String activityId) {
        // 查询活动关联奖品数据
        List<ActivityAwardDto> list = activityCouponRelationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activityId);
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for (ActivityAwardDto awardDto : list) {
            this.returnEquityAmount(awardDto.getType(), awardDto.getTypeId(), awardDto.getRemainNum());
        }
    }

    @Override
    public void returnEquityAmount(Integer type, String typeId, Integer num) {
        if (num == null || num <= 0) {
            return;
        }
        if (Objects.equals(ConnectTypeEnum.COUPON.getState(), type)) {
            couponService.returnEquityAmount(typeId, num);
        } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), type)) {
            couponPackageService.returnEquityAmount(typeId, num);
        }
    }

    @Override
    public List<ActivityAwardDto> getAwardList(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        return activityCouponRelationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activityId);
    }

    @Override
    public List<ActivityAwardDto> getAwardListWithCouponInfo(String activityId) {
        List<ActivityAwardDto> list = this.getAwardList(activityId);
        if (CollectionUtil.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        for (ActivityAwardDto awardDto : list) {
            com.deepexi.equity.domain.dto.CouponAndPackageDto couponAndPackageDto =
                    couponPackageService.queryCouponAndPackage(awardDto.getType(), awardDto.getTypeId());
            awardDto.setTypeDetail(FieldCopyUtils.copy(couponAndPackageDto));
        }
        return list;
    }

    @Override
    public List<String> findTypeIdList(Integer type) {
        return activityCouponRelationMapper.selectTypeIdList(appRuntimeEnv.getTenantId(), type);
    }

    @Override
    public Integer countByTypeAndTypeId(Integer type, String typeId) {
        if (!Objects.equals(ConnectTypeEnum.COUPON.getState(), type)
                && !Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), type)) {
            logger.error("ActivityCouponRelationServiceImpl.countByTypeAndTypeId：参数异常，类型为[{}]", type);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "类型参数异常"));
        }
        if (StringUtils.isBlank(typeId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，类型ID数据为null"));
        }
        return activityCouponRelationMapper.countByTypeAndTypeId(appRuntimeEnv.getTenantId(), type, typeId);
    }

    @Override
    public List<String> findActivityIdList(List<String> typeIdList) {
        if (CollectionUtil.isEmpty(typeIdList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "类型ID列表为null"));
        }
        return activityCouponRelationMapper.selectActivityIdList(appRuntimeEnv.getTenantId(), typeIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Klock(name = "activityCouponRelation", keys = {"#activityId"}, lockTimeoutStrategy = LockTimeoutStrategy.FAIL_FAST)
    public void receive(String memberId, String activityId) {
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        // 查询活动奖品关系
        List<ActivityAwardDto> list = activityCouponRelationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activityId);
        for (ActivityAwardDto awardDto : list) {
            // 扣减奖品数量
            Integer updateResult = this.subRemainNum(awardDto.getId(), 1);
            if (updateResult != 1) {
                logger.error("ActivityCouponRelationServiceImpl.receive：减少剩余数量操作失败，subRemainNum方法返回值为[{}]，主键ID为[{}]，数量为[{}]",
                        updateResult, awardDto.getId(), 1);
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "奖品数量不足"));
            }
            if (Objects.equals(ConnectTypeEnum.COUPON.getState(), awardDto.getType())) {
                // 优惠券
                memberCouponRelationService.createByCoupon(memberId, awardDto.getTypeId(), awardDto.getId(), activityId, VerificationStatusEnum.PENDING_USE.getState(), awardDto.getReceiveType());
            } else if (Objects.equals(ConnectTypeEnum.COUPON_PACKAGE.getState(), awardDto.getType())) {
                // 优惠券组合包
                memberCouponRelationService.createByCouponPackage(memberId, awardDto.getTypeId(), awardDto.getId(), activityId, VerificationStatusEnum.PENDING_USE.getState(), awardDto.getReceiveType());
            }
        }
    }

    @Override
    public Integer subRemainNum(String id, Integer num) {
        if (StringUtils.isBlank(id)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，主键ID为null"));
        }
        if (num == null || num <= 0) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "数量参数异常"));
        }
        return activityCouponRelationMapper.subRemainNum(id, num, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
    }

    @Override
    public Integer addRemainNum(String id, Integer num) {
        if (StringUtils.isBlank(id)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，主键ID为null"));
        }
        if (num == null || num <= 0) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "数量参数异常"));
        }
        return activityCouponRelationMapper.addRemainNum(id, num, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
    }

    @Override
    public ActivityAwardDto queryLatestCompleteRelation(String activityId, Integer completeNum) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        if (completeNum == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，达标数量为null"));
        }
        return activityCouponRelationMapper.selectLatestComplete(appRuntimeEnv.getTenantId(), activityId, completeNum);
    }

    @Override
    public ActivityAwardDto queryTopRelation(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        return activityCouponRelationMapper.selectLatestComplete(appRuntimeEnv.getTenantId(), activityId, null);
    }

    @Override
    public ActivityAwardDto queryLowestUnCompleteRelation(String activityId, Integer completeNum) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        if (completeNum == null) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，达标数量为null"));
        }
        return activityCouponRelationMapper.selectLowestUnComplete(appRuntimeEnv.getTenantId(), activityId, completeNum);
    }

    @Override
    public String queryTypeName(Integer type, String typeId) {
        CouponAndPackageDto couponAndPackageDto = couponPackageService.queryCouponAndPackageOnlyEo(type, typeId);
        return couponAndPackageDto.getName();
    }

    @Override
    public List<ActivityCouponRelation> findAllCompleteRelation(String memberId, String activityId) {
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        return activityCouponRelationMapper.selectAllCompleteRelation(appRuntimeEnv.getTenantId(), memberId, activityId);
    }

    /**
     * 根据优惠券ID查询形成优惠券Map
     *
     * @param couponIdList ID列表
     * @return Map
     */
    private Map<String, Coupon> queryCouponMap(List<String> couponIdList) {
        Map<String, Coupon> couponMap = Maps.newHashMap();
        if (CollectionUtil.isEmpty(couponIdList)) {
            return couponMap;
        }
        CouponQueryDto couponQueryDto = new CouponQueryDto();
        couponQueryDto.setIdList(couponIdList);
        List<Coupon> couponList = couponService.findEoList(couponQueryDto);
        if (CollectionUtil.isNotEmpty(couponList)) {
            for (Coupon coupon : couponList) {
                couponMap.put(coupon.getId(), coupon);
            }
        }
        return couponMap;
    }

    /**
     * 根据组合包ID查询形成组合包Map
     *
     * @param couponPackageIdList ID列表
     * @return Map
     */
    private Map<String, CouponPackage> queryCouponPackageMap(List<String> couponPackageIdList) {
        Map<String, CouponPackage> couponPackageMap = Maps.newHashMap();
        if (CollectionUtil.isEmpty(couponPackageIdList)) {
            return couponPackageMap;
        }
        CouponPackageQueryDto couponPackageQueryDto = new CouponPackageQueryDto();
        couponPackageQueryDto.setIdList(couponPackageIdList);
        List<CouponPackage> couponPackageList = couponPackageService.findEoList(couponPackageQueryDto);
        if (CollectionUtil.isNotEmpty(couponPackageList)) {
            for (CouponPackage couponPackage : couponPackageList) {
                couponPackageMap.put(couponPackage.getId(), couponPackage);
            }
        }
        return couponPackageMap;
    }
}