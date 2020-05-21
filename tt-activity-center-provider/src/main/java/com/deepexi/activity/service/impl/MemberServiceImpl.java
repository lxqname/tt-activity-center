package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.enums.ActivityApplicationTypeEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.service.MemberService;
import com.deepexi.activity.utils.FieldCopyUtils;
import com.deepexi.common.enums.PlatformTypeEnum;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.enums.YesNoEnum;
import com.deepexi.member.api.TcTagMemberRelationService;
import com.deepexi.member.domain.dto.MemberDto;
import com.deepexi.member.domain.eo.Member;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 会员服务实现
 *
 * @author 蝈蝈
 * @since 2019年10月12日 20:51
 */
@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Reference(version = "${demo.service.version}", check = false)
    private com.deepexi.member.api.MemberService memberService;

    @Reference(version = "${demo.service.version}", check = false)
    private TcTagMemberRelationService tagMemberRelationService;

    @Override
    public String getLoginMemberId() {
        return this.getLoginMember().getId();
    }

    @Override
    public Member getLoginMember() {
        Member member = memberService.getLoginInfo();
        if (member == null) {
            logger.error("MemberServiceImpl.getLoginMember：查询会员信息为null，token为[{}]", appRuntimeEnv.getToken());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "获取会员信息失败"));
        }
        return member;
    }

    @Override
    public Member getMemberById(String memberId) {
        if (StringUtils.isBlank(memberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员ID为null"));
        }
        Member member = memberService.detailById(memberId);
        if (member == null) {
            logger.error("MemberServiceImpl.getMemberById：查询会员信息为null，主键ID为[{}]", memberId);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "获取会员信息失败"));
        }
        return member;
    }

    @Override
    public Map<String, Member> findAllByMobile(String mobile) {
        MemberDto query = new MemberDto();
        query.setMobile(mobile);
        List<Member> memberList = memberService.findAll(query);
        if (CollectionUtil.isEmpty(memberList)) {
            return null;
        }
        Map<String, Member> memberMap = new HashMap<>(memberList.size());
        for (Member member : memberList) {
            memberMap.put(member.getId(), member);
        }
        return memberMap;
    }

    @Override
    public List<String> findMemberIdList(Integer applicationType, String enterpriseId) {
        MemberDto memberDto = new MemberDto();
        String tenantCode = appRuntimeEnv.getTenantId();
        Integer platformType = null;
        if (Objects.equals(ActivityApplicationTypeEnum.MARKET_PLATFORM.getState(), applicationType)) {
            platformType = PlatformTypeEnum.INTERNAL_ACCOUNT.getValue();
        } else if (Objects.equals(ActivityApplicationTypeEnum.BUSINESS_PLATFORM.getState(), applicationType)) {
            platformType = PlatformTypeEnum.BUSINESS_ACCOUNT.getValue();
            if (StringUtils.isBlank(enterpriseId)) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，企业ID为null"));
            }
        } else {
            logger.error("MemberServiceImpl.findMemberIdList：创建平台类型异常为[{}]", applicationType);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，创建平台类型异常"));
        }
        List<String> memberIdList = Lists.newArrayList();
        int page = 1;
        int size = 1000;
        int totalPages;
        do {
            PageBean<Member> memberPageBean = memberService.findPageCommonByObviousParam(memberDto, page, size, platformType, enterpriseId, tenantCode);
            if (memberPageBean == null) {
                return memberIdList;
            }
            List<Member> memberList = memberPageBean.getContent();
            if (CollectionUtil.isNotEmpty(memberList)) {
                for (Member member : memberList) {
                    if (!memberIdList.contains(member.getId())) {
                        memberIdList.add(member.getId());
                    }
                }
            }
            totalPages = memberPageBean.getTotalPages();
            page = page + 1;
        } while (totalPages >= page);
        return memberIdList;
    }

    @Override
    public List<String> findMemberIdListByMemberTagId(String memberTagId) {
        if (StringUtils.isBlank(memberTagId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员标签ID为null"));
        }
        List<String> memberIdList = Lists.newArrayList();
        int page = 1;
        int size = 1000;
        int totalPages;
        do {
            PageBean<Member> memberPageBean = tagMemberRelationService.getMemberListByTagId(memberTagId, page, size);
            if (memberPageBean == null) {
                return memberIdList;
            }
            List<Member> memberList = memberPageBean.getContent();
            if (CollectionUtil.isNotEmpty(memberList)) {
                for (Member member : memberList) {
                    if (!memberIdList.contains(member.getId())) {
                        memberIdList.add(member.getId());
                    }
                }
            }
            totalPages = memberPageBean.getTotalPages();
            page = page + 1;
        } while (totalPages >= page);
        return memberIdList;
    }

    @Override
    public List<String> findMemberIdListByMemberTagId(List<String> memberTagIdList) {
        if (CollectionUtil.isEmpty(memberTagIdList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员标签ID列表为null"));
        }
        List<String> memberIdList = Lists.newArrayList();
        for (String memberTagId : memberTagIdList) {
            memberIdList = FieldCopyUtils.unionList(memberIdList, this.findMemberIdListByMemberTagId(memberTagId));
        }
        return memberIdList;
    }

    @Override
    public List<String> findMemberIdListByMemberGroupId(String memberGroupId) {
        if (StringUtils.isBlank(memberGroupId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员分组ID为null"));
        }
        List<String> memberIdList = Lists.newArrayList();
        int page = 1;
        int size = 1000;
        int totalPages;
        do {
            PageBean<Member> memberPageBean = tagMemberRelationService.getMemberListByMemberGroupId(memberGroupId, page, size, YesNoEnum.NO.getState());
            if (memberPageBean == null) {
                return memberIdList;
            }
            List<Member> memberList = memberPageBean.getContent();
            if (CollectionUtil.isNotEmpty(memberList)) {
                for (Member member : memberList) {
                    if (!memberIdList.contains(member.getId())) {
                        memberIdList.add(member.getId());
                    }
                }
            }
            totalPages = memberPageBean.getTotalPages();
            page = page + 1;
        } while (totalPages >= page);
        return memberIdList;
    }

    @Override
    public List<String> findMemberIdListByMemberGroupId(List<String> memberGroupIdList) {
        if (CollectionUtil.isEmpty(memberGroupIdList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员分组ID列表为null"));
        }
        List<String> memberIdList = Lists.newArrayList();
        for (String memberGroupId : memberGroupIdList) {
            memberIdList = FieldCopyUtils.unionList(memberIdList, this.findMemberIdListByMemberGroupId(memberGroupId));
        }
        return memberIdList;
    }
}
