package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.ActivityAuditHistoryDto;
import com.deepexi.activity.domain.eo.ActivityAuditHistory;
import com.deepexi.activity.enums.ActivityAuditHistoryStatusEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.ActivityAuditHistoryMapper;
import com.deepexi.activity.service.ActivityAuditHistoryService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.enums.YesNoEnum;
import com.deepexi.util.extension.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 活动审核记录服务实现
 *
 * @author 蝈蝈
 */
@Component
@Service(version = "${demo.service.version}")
public class ActivityAuditHistoryServiceImpl implements ActivityAuditHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityAuditHistoryServiceImpl.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private ActivityAuditHistoryMapper activityAuditHistoryMapper;

    @Override
    public void create(String activityId, Integer status, String remark) {
        ActivityAuditHistory activityAuditHistory = new ActivityAuditHistory();
        activityAuditHistory.setActivityId(activityId);
        activityAuditHistory.setStatus(status);
        activityAuditHistory.setRemark(remark);

        // 校验参数合法性
        this.validateInfo(activityAuditHistory);
        int insertResult = activityAuditHistoryMapper.insert(activityAuditHistory);
        if (insertResult != 1) {
            logger.error("ActivityAuditHistoryServiceImpl.create：插入数据操作失败，insert方法返回值为[]", insertResult);
            throw new ApplicationException(ResultEnum.DATABASE_ERROR);
        }
    }

    @Override
    public List<ActivityAuditHistoryDto> findAll(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常，活动ID为null"));
        }
        return activityAuditHistoryMapper.selectByActivityId(appRuntimeEnv.getTenantId(), activityId);
    }

    /**
     * 验证数据合法性
     *
     * @param eo 数据
     */
    private void validateInfo(ActivityAuditHistory eo) {
        if (eo == null) {
            logger.error("ActivityAuditHistoryServiceImpl.validateInfo：入参为null");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常"));
        }
        if (StringUtils.isBlank(eo.getActivityId())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "活动ID不可为null"));
        }
        if (!Objects.equals(ActivityAuditHistoryStatusEnum.MARKET_REJECT.getState(), eo.getStatus())
                && !Objects.equals(ActivityAuditHistoryStatusEnum.MARKET_SUBMIT.getState(), eo.getStatus())
                &&!Objects.equals(ActivityAuditHistoryStatusEnum.BUSINESS_REJECT.getState(), eo.getStatus())
                && !Objects.equals(ActivityAuditHistoryStatusEnum.BUSINESS_SUBMIT.getState(), eo.getStatus())) {
            logger.error("ActivityAuditHistoryServiceImpl.validateInfo：审核状态参数异常[{}]", eo.getStatus());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "审核状态参数异常"));
        }
        // 审核不通过审核说明为必填
        if (Objects.equals(YesNoEnum.NO.getState(), eo.getStatus()) && StringUtils.isBlank(eo.getRemark())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "审核不通过，请输入审核说明"));
        }
    }
}