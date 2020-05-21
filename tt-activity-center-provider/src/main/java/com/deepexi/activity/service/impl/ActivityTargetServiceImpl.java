package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityConditionDto;
import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.activity.domain.dto.ActivityListDto;
import com.deepexi.activity.domain.dto.IdNameDto;
import com.deepexi.activity.domain.dto.TargetDto;
import com.deepexi.activity.domain.eo.ActivityOrganization;
import com.deepexi.activity.domain.eo.ActivityTarget;
import com.deepexi.activity.enums.ActivityApplicationTypeEnum;
import com.deepexi.activity.enums.ActivityTargetTypeEnum;
import com.deepexi.activity.enums.ParticipateTypeEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityTargetMapper;
import com.deepexi.activity.service.ActivityOrganizationService;
import com.deepexi.activity.service.ActivityTargetService;
import com.deepexi.activity.service.MemberService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.tag.api.MemberGroupService;
import com.deepexi.tag.api.TagService;
import com.deepexi.tag.domain.dto.paramDto.MemberGroupQueryDto;
import com.deepexi.tag.domain.dto.paramDto.TagQueryDto;
import com.deepexi.tag.domain.eo.MemberGroup;
import com.deepexi.tag.domain.eo.Tag;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.wechat.domain.eo.MpAccount;
import com.deepexi.wechat.service.mp.MpAccountService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 活动对象服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class ActivityTargetServiceImpl implements ActivityTargetService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityTargetServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private ActivityTargetMapper activityTargetMapper;

    @Resource
    private MemberService memberService;

    @Resource
    private ActivityOrganizationService activityOrganizationService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Reference(version = "${demo.service.version}", check = false)
    private MpAccountService mpAccountService;

    @Reference(version = "${demo.service.version}", check = false)
    private TagService tagService;

    @Reference(version = "${demo.service.version}", check = false)
    private MemberGroupService memberGroupService;


    @Override
    public void create(String activityId, Integer participateType, Integer type, String typeId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        ActivityTarget activityTarget = new ActivityTarget();
        activityTarget.setActivityId(activityId);
        activityTarget.setParticipateType(participateType);
        activityTarget.setType(type);
        activityTarget.setTypeId(typeId);
        int insertResult = activityTargetMapper.insert(activityTarget);
        if (insertResult != 1) {
            logger.error("ActivityTargetServiceImpl.create：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String activityId, Integer participateType, Integer type, List<String> typeIdList) {
        if (Objects.equals(ActivityTargetTypeEnum.All.getState(), type)) {
            this.create(activityId, participateType, type, (String) null);
        } else if (Objects.equals(ActivityTargetTypeEnum.MEMBER_GROUP.getState(), type)
                || Objects.equals(ActivityTargetTypeEnum.ATTENTION_PUBLIC.getState(), type)
                || Objects.equals(ActivityTargetTypeEnum.MEMBER_TAG.getState(), type)) {
            if (CollectionUtil.isEmpty(typeIdList)) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动对象类型ID列表为null"));
            }
            for (String typeId : typeIdList) {
                if (StringUtils.isBlank(typeId)) {
                    throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动对象类型ID为null"));
                }
                this.create(activityId, participateType, type, typeId);
            }
        } else {
            logger.error("ActivityTargetServiceImpl.create：活动对象类型[{}]异常", type);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动对象类型异常"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String activityId, Integer participateType, ActivityConditionDto activityConditionDto) {
        List<String> memberGroupIdList = activityConditionDto.getMemberGroupIdList();
        List<String> weChatNumberIdList = activityConditionDto.getWeChatNumberIdList();
        if (CollectionUtil.isNotEmpty(memberGroupIdList)) {
            this.create(activityId, participateType, ActivityTargetTypeEnum.MEMBER_GROUP.getState(), memberGroupIdList);
        }
        if (CollectionUtil.isNotEmpty(weChatNumberIdList)) {
            this.create(activityId, participateType, ActivityTargetTypeEnum.ATTENTION_PUBLIC.getState(), weChatNumberIdList);
        }
    }

    @Override
    public void deleteByActivityId(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID数据为null"));
        }
        activityTargetMapper.deleteByActivityId(activityId, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
    }

    @Override
    public void fillUpTargetInfo(List<ActivityListDto> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            for (ActivityListDto dto : list) {
                List<ActivityTarget> activityTargetList = activityTargetMapper.selectByActivityId(
                        appRuntimeEnv.getTenantId(), dto.getId(), ParticipateTypeEnum.PARTICIPATE.getState());
                if (CollectionUtil.isNotEmpty(activityTargetList)) {
                    dto.setTargetType(activityTargetList.get(0).getType());
                }
            }
        }
    }

    @Override
    public void fillUpTargetInfo(ActivityDetailDto activityDetailDto) {
        List<ActivityTarget> activityTargetList = activityTargetMapper.selectByActivityId(
                appRuntimeEnv.getTenantId(), activityDetailDto.getId(), ParticipateTypeEnum.PARTICIPATE.getState());
        if (CollectionUtil.isNotEmpty(activityTargetList)) {
            activityDetailDto.setTargetType(activityTargetList.get(0).getType());
            if (!Objects.equals(ActivityTargetTypeEnum.All.getState(), activityDetailDto.getTargetType())) {
                List<String> targetIdList = new ArrayList<>(activityTargetList.size());
                for (ActivityTarget activityTarget : activityTargetList) {
                    if (Objects.equals(activityTarget.getType(), activityDetailDto.getTargetType())) {
                        targetIdList.add(activityTarget.getTypeId());
                    }
                }
                // 查询名称
                if (Objects.equals(ActivityTargetTypeEnum.MEMBER_GROUP.getState(), activityDetailDto.getTargetType())) {
                    // 查询会员分组信息
                    activityDetailDto.setTargetList(this.getMemberGroupTargetList(targetIdList));
                }else if (Objects.equals(ActivityTargetTypeEnum.ATTENTION_PUBLIC.getState(), activityDetailDto.getTargetType())) {
                    // 查询公众号信息
                    activityDetailDto.setTargetList(this.getAttentionPublicTargetList(targetIdList));
                }else if (Objects.equals(ActivityTargetTypeEnum.MEMBER_TAG.getState(), activityDetailDto.getTargetType())) {
                    // 查询会员标签信息
                    activityDetailDto.setTargetList(this.getMemberTagTargetList(targetIdList));
                }
            }
        }
    }

    /**
     * 查询会员分组信息
     *
     * @param targetIdList 会员分组ID列表
     * @return List
     */
    private List<IdNameDto> getMemberGroupTargetList(List<String> targetIdList) {
        List<IdNameDto> response = Lists.newArrayList();
        if (CollectionUtil.isEmpty(targetIdList)) {
            return response;
        }
        MemberGroupQueryDto queryDto = new MemberGroupQueryDto();
        queryDto.setIds(targetIdList);
        List<MemberGroup> memberGroupList = memberGroupService.selectFindAll(queryDto);
        if (CollectionUtil.isEmpty(memberGroupList)) {
            logger.info("ActivityTargetServiceImpl.getMemberGroupTargetList:查询会员分组信息返回null，ID列表为[{}]", targetIdList.toString());
            return response;
        }
        for (MemberGroup memberGroup : memberGroupList) {
            IdNameDto idNameDto = new IdNameDto();
            idNameDto.setId(memberGroup.getId());
            idNameDto.setName(memberGroup.getName());
            response.add(idNameDto);
        }
        return response;
    }

    /**
     * 根据公众号ID列表查询公众号信息
     *
     * @param targetIdList 公众号ID列表
     * @return List
     */
    private List<IdNameDto> getAttentionPublicTargetList(List<String> targetIdList) {
        List<IdNameDto> response = Lists.newArrayList();
        if (CollectionUtil.isEmpty(targetIdList)) {
            return response;
        }
        List<MpAccount> mpAccountList = mpAccountService.findByAccount(targetIdList);
        if (CollectionUtil.isEmpty(mpAccountList)) {
            logger.info("ActivityTargetServiceImpl.fillUpTargetInfo:查询公众号信息返回null，ID列表为[{}]", targetIdList.toString());
            return response;
        }
        for (MpAccount mpAccount : mpAccountList) {
            IdNameDto idNameDto = new IdNameDto();
            idNameDto.setId(mpAccount.getAccount());
            idNameDto.setName(mpAccount.getNickName());
            response.add(idNameDto);
        }
        return response;
    }

    /**
     * 查询会员标签信息
     *
     * @param targetIdList 会员标签ID列表
     * @return List
     */
    private List<IdNameDto> getMemberTagTargetList(List<String> targetIdList) {
        List<IdNameDto> response = Lists.newArrayList();
        if (CollectionUtil.isEmpty(targetIdList)) {
            return response;
        }
        TagQueryDto queryDto = new TagQueryDto();
        queryDto.setIds(targetIdList);
        List<Tag> tagList = tagService.selectFindAll(queryDto);
        if (CollectionUtil.isEmpty(tagList)) {
            logger.info("ActivityTargetServiceImpl.getMemberTagTargetList:查询会员标签信息返回null，ID列表为[{}]", targetIdList.toString());
            return response;
        }
        for (Tag tag : tagList) {
            IdNameDto idNameDto = new IdNameDto();
            idNameDto.setId(tag.getId());
            idNameDto.setName(tag.getName());
            response.add(idNameDto);
        }
        return response;
    }

    @Override
    public ActivityConditionDto findActivityConditionDto(String activityId, Integer participateType) {
        ActivityConditionDto response = new ActivityConditionDto();
        if (StringUtils.isBlank(activityId)) {
            logger.info("ActivityTargetServiceImpl.findActivityConditionDto：入参活动ID为[{}]", activityId);
            return response;
        }
        List<ActivityTarget> activityTargetList = activityTargetMapper.selectByActivityId(
                appRuntimeEnv.getTenantId(), activityId, participateType);
        if (CollectionUtil.isEmpty(activityTargetList)) {
            logger.info("ActivityTargetServiceImpl.findActivityConditionDto：根据活动ID[{}]及参与类型[{}]查询列表为null", activityId, participateType);
            return response;
        }
        List<String> memberGroupIdList = Lists.newArrayList();
        List<String> weChatNumberIdList = Lists.newArrayList();
        for (ActivityTarget activityTarget : activityTargetList) {
            if (Objects.equals(ActivityTargetTypeEnum.MEMBER_GROUP.getState(), activityTarget.getType())) {
                memberGroupIdList.add(activityTarget.getTypeId());
            } else if (Objects.equals(ActivityTargetTypeEnum.ATTENTION_PUBLIC.getState(), activityTarget.getType())) {
                weChatNumberIdList.add(activityTarget.getTypeId());
            } else {
                logger.error("ActivityTargetServiceImpl.findActivityConditionDto：活动对象类型异常，ID主键为[{}]", activityTarget.getId());
            }
        }
        response.setMemberGroupIdList(memberGroupIdList);
        response.setWeChatNumberIdList(weChatNumberIdList);
        return response;
    }

    @Override
    public TargetDto queryTargetInfo(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        List<ActivityTarget> activityTargetList = activityTargetMapper.selectByActivityId(
                appRuntimeEnv.getTenantId(), activityId, ParticipateTypeEnum.PARTICIPATE.getState());
        TargetDto response = new TargetDto();
        if (CollectionUtil.isNotEmpty(activityTargetList)) {
            response.setTargetType(activityTargetList.get(0).getType());
            if (!Objects.equals(ActivityTargetTypeEnum.All.getState(), response.getTargetType())) {
                List<String> targetIdList = new ArrayList<>(activityTargetList.size());
                for (ActivityTarget activityTarget : activityTargetList) {
                    if (Objects.equals(activityTarget.getType(), response.getTargetType())) {
                        targetIdList.add(activityTarget.getTypeId());
                    }
                }
                response.setTargetIdList(targetIdList);
            }
        }
        return response;
    }

    @Override
    public List<String> findTargetMemberIdList(String activityId, Integer applicationType) {
        List<String> memberIdList;
        TargetDto targetDto = this.queryTargetInfo(activityId);
        if (Objects.equals(ActivityTargetTypeEnum.All.getState(), targetDto.getTargetType())) {
            if (Objects.equals(ActivityApplicationTypeEnum.BUSINESS_PLATFORM.getState(), applicationType)) {
                // 查询企业ID
                ActivityOrganization activityOrganization = activityOrganizationService.queryByActivityId(activityId);
                memberIdList = memberService.findMemberIdList(applicationType, activityOrganization.getEnterpriseId());
            } else {
                memberIdList = memberService.findMemberIdList(applicationType, null);
            }
        } else if (Objects.equals(ActivityTargetTypeEnum.MEMBER_GROUP.getState(), targetDto.getTargetType())) {
            memberIdList = memberService.findMemberIdListByMemberGroupId(targetDto.getTargetIdList());
        } else if (Objects.equals(ActivityTargetTypeEnum.MEMBER_TAG.getState(), targetDto.getTargetType())) {
            memberIdList = memberService.findMemberIdListByMemberTagId(targetDto.getTargetIdList());
        } else {
            logger.error("ActivityServiceImpl.issueCoupon：活动对象类型异常为[{}]", targetDto.getTargetType());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动对象类型异常"));
        }
        return memberIdList;
    }
}