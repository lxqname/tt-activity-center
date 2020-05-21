package com.deepexi.activity.service;

import com.deepexi.member.domain.eo.Member;

import java.util.List;
import java.util.Map;

/**
 * 会员服务
 *
 * @author 蝈蝈
 * @since 2019年10月12日 20:48
 */
public interface MemberService {

    /**
     * 获取登录会员ID
     *
     * @return String
     */
    String getLoginMemberId();

    /**
     * 获取登录会员信息
     *
     * @return Member
     */
    Member getLoginMember();

    /**
     * 根据ID获取会员信息-名称经过转码
     *
     * @param memberId 会员ID
     * @return Member
     */
    Member getMemberById(String memberId);

    /**
     * 根据手机号码查询所有会员信息
     *
     * @param mobile 手机号码
     * @return Map[会员ID, 会员信息]
     */
    Map<String, Member> findAllByMobile(String mobile);

    /**
     * 查询全部会员ID列表
     *
     * @param applicationType 创建平台（1-运营平台、2-商户平台）
     * @param enterpriseId    企业ID
     * @return List
     */
    List<String> findMemberIdList(Integer applicationType, String enterpriseId);

    /**
     * 根据会员标签ID查询会员ID列表
     *
     * @param memberTagId 会员标签ID
     * @return List
     */
    List<String> findMemberIdListByMemberTagId(String memberTagId);

    /**
     * 根据会员标签ID列表查询会员ID列表
     *
     * @param memberTagIdList 会员标签ID列表
     * @return List
     */
    List<String> findMemberIdListByMemberTagId(List<String> memberTagIdList);

    /**
     * 根据会员分组ID查询会员ID列表
     *
     * @param memberGroupId 会员分组ID
     * @return List
     */
    List<String> findMemberIdListByMemberGroupId(String memberGroupId);

    /**
     * 根据会员分组ID列表查询会员ID列表
     *
     * @param memberGroupIdList 会员分组ID列表
     * @return List
     */
    List<String> findMemberIdListByMemberGroupId(List<String> memberGroupIdList);
}
