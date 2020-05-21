package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityOrganizationDto;
import com.deepexi.activity.domain.eo.ActivityOrganization;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityOrganizationMapper;
import com.deepexi.activity.service.ActivityOrganizationService;
import com.deepexi.business.domain.vo.LoginBusinessInfoVo;
import com.deepexi.business.service.BusinessAccountService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.util.BeanPowerHelper;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Service(version = "${demo.service.version}")
public class ActivityOrganizationServiceImpl implements ActivityOrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityOrganizationServiceImpl.class);

    @Autowired
    private ActivityOrganizationMapper activityOrganizationMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Reference(version = "${demo.service.version}", check = false)
    private BusinessAccountService businessAccountService;


    @Override
    public PageBean findPage(ActivityOrganizationDto eo, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ActivityOrganization> list = activityOrganizationMapper.findList(eo);
        return new PageBean<>(list);
    }

    @Override
    public List<ActivityOrganization> findAll(ActivityOrganizationDto eo) {
        List<ActivityOrganization> list = activityOrganizationMapper.findList(eo);
        return list;
    }
    @Override
    public ActivityOrganization detail(String pk) {
        return activityOrganizationMapper.selectById(pk);
    }

    @Override
    public Boolean create(ActivityOrganizationDto eo) {
        int result = activityOrganizationMapper.insert(BeanPowerHelper.mapPartOverrider(eo,ActivityOrganization.class));
        return result > 0;
    }


    @Override
    public Boolean create(String activityId) {
        LoginBusinessInfoVo loginBusinessInfo = businessAccountService.getLoginBusinessInfo();
        ActivityOrganization eo = new ActivityOrganization();
        eo.setActivityId(activityId);
        eo.setEnterpriseId(loginBusinessInfo.getEnterprOrganizationId());
        eo.setOrganizationId(loginBusinessInfo.getDepartmentOrganizationId());
        eo.setTenantCode(appRuntimeEnv.getTenantId());
        int result = activityOrganizationMapper.insert(BeanPowerHelper.mapPartOverrider(eo,ActivityOrganization.class));
        return result > 0;
    }

    @Override
    public Boolean update(String pk,ActivityOrganizationDto eo) {
        eo.setId(pk);
        int result = activityOrganizationMapper.updateById(BeanPowerHelper.mapPartOverrider(eo,ActivityOrganization.class));
        return result > 0;
    }

    @Override
    public Boolean delete(String...pk) {
        int result = activityOrganizationMapper.deleteByIds(pk);
        return result > 0;
    }

    @Override
    public Boolean deleteByActivityId(String activityId) {
        Map param = new HashMap(1);
        param.put("activity_id",activityId);
        int result = activityOrganizationMapper.deleteByMap(param);
        return result > 0;
    }

    @Override
    public List<ActivityOrganization> selectByActivityId(String id) {
        return activityOrganizationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), id);
    }

    @Override
    public ActivityOrganization queryByActivityId(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        List<ActivityOrganization> list = activityOrganizationMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activityId);
        // 目前活动与企业为一对一关系
        if (CollectionUtil.isEmpty(list) || list.size() > 1) {
            logger.error("ActivityOrganizationServiceImpl.queryByActivityId：根据活动ID[{}]查询企业ID列表长度为[{}]", activityId, list.size());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "获取活动企业信息失败"));
        }
        return list.get(0);
    }
}