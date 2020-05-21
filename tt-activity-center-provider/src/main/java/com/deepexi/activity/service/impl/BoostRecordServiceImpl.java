package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.BoostRecordDto;
import com.deepexi.activity.domain.dto.BoostRecordQueryDto;
import com.deepexi.activity.domain.eo.BoostRecord;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.BoostRecordMapper;
import com.deepexi.activity.service.BoostRecordService;
import com.deepexi.activity.service.MemberService;
import com.deepexi.activity.utils.BaseFieldUtil;
import com.deepexi.common.enums.DrEnum;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.member.domain.eo.Member;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 助力记录服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class BoostRecordServiceImpl implements BoostRecordService {

    private static final Logger logger = LoggerFactory.getLogger(BoostRecordServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private BoostRecordMapper boostRecordMapper;

    @Resource
    private MemberService memberService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Override
    public void create(String memberActivityId, String helperMemberId) {
        if (StringUtils.isBlank(memberActivityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，会员活动ID为null"));
        }
        if (StringUtils.isBlank(helperMemberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，助力者会员ID为null"));
        }
        BoostRecord boostRecord = new BoostRecord();
        boostRecord.setMemberActivityId(memberActivityId);
        boostRecord.setBoostMemberId(helperMemberId);
        int insertResult = boostRecordMapper.insert(boostRecord);
        if (insertResult != 1) {
            logger.error("BoostRecordServiceImpl.create：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    @Override
    public Boolean delete(List<String> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，助力记录删除入参为null"));
        }
        int deleteResult = boostRecordMapper.deleteByIds(idList, BaseFieldUtil.getLoginAccountId(accountService, appRuntimeEnv.getToken()));
        if (deleteResult != idList.size()) {
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<BoostRecordDto> findList(BoostRecordQueryDto query) {
        query.setDr(DrEnum.ENABLE.getValue());
        query.setTenantCode(appRuntimeEnv.getTenantId());
        List<BoostRecordDto> list = boostRecordMapper.findList(query);
        if (CollectionUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }
        list.parallelStream().forEach(dto -> {
            Member member = memberService.getMemberById(dto.getBoostMemberId());
            dto.setBoostMemberNickName(member.getNickName());
            dto.setBoostMemberHeadImg(member.getHeadImg());
        });
        return list;
    }

    @Override
    public Boolean allowBoost(String boostMemberId) {
        if (StringUtils.isBlank(boostMemberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，助力者会员ID为null"));
        }
        BoostRecordQueryDto query = new BoostRecordQueryDto();
        query.setTenantCode(appRuntimeEnv.getTenantId());
        query.setBoostMemberId(boostMemberId);
        int count = boostRecordMapper.count(query);
        return count <= 0;
    }

    @Override
    public BoostRecordDto queryByBoostMemberId(String boostMemberId) {
        if (StringUtils.isBlank(boostMemberId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，助力者会员ID为null"));
        }
        BoostRecordQueryDto query = new BoostRecordQueryDto();
        query.setDr(DrEnum.ENABLE.getValue());
        query.setTenantCode(appRuntimeEnv.getTenantId());
        query.setBoostMemberId(boostMemberId);
        List<BoostRecordDto> list = boostRecordMapper.findList(query);
        if (CollectionUtil.isEmpty(list)) {
            // 没有助力过活动
            logger.error("BoostRecordServiceImpl.queryByBoostMemberId：会员ID为[{}]，没有助力过活动", boostMemberId);
            return null;
        }
        if (list.size() > 1) {
            // 出现多条助力记录，数据库异常
            logger.error("BoostRecordServiceImpl.queryByBoostMemberId：会员ID为[{}]，查询出现[{}]条助力活动记录", boostMemberId, list.size());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "同个会员查询出现多条助力记录"));
        }
        return list.get(0);
    }
}