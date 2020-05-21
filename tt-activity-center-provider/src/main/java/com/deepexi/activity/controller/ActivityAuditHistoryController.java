package com.deepexi.activity.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.deepexi.activity.domain.dto.ActivityAuditHistoryDto;
import com.deepexi.activity.service.ActivityAuditHistoryService;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * 活动审核记录Controller
 *
 * @author 蝈蝈
 */
@Service
@Path("/api/v1/activityAuditHistories")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ActivityAuditHistoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ActivityAuditHistoryService activityAuditHistoryService;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @GET
    @Path("/list")
    public Payload findAll(@QueryParam("activityId") String activityId) {
        List<ActivityAuditHistoryDto> list = activityAuditHistoryService.findAll(activityId);
        if (CollectionUtil.isNotEmpty(list)) {
            //将创建人转换为名称
            for (ActivityAuditHistoryDto dto : list) {
                try {
                    if (StringUtils.isNotBlank(dto.getCreatedBy())) {
                        dto.setCreatedBy(accountService.getUsernameById(dto.getCreatedBy()));
                    }
                } catch (Exception e) {
                    logger.info("ActivityAuditHistoryController.findAll：找不到用户信息,accountId为[{}]", dto.getCreatedBy());
                    logger.error("错误信息为:", e);
                }
            }
        }
        return new Payload<>(list);
    }
}
