package com.deepexi.activity.controller;

import com.deepexi.activity.domain.dto.ActivityQueryDto;
import com.deepexi.activity.domain.dto.ActivitySaveDto;
import com.deepexi.activity.domain.dto.AuditSaveDto;
import com.deepexi.activity.service.ActivityService;
import com.deepexi.common.annotation.Token;
import com.deepexi.common.enums.SymbolEnum;
import com.deepexi.common.util.DateUtils;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * 活动Controller
 *
 * @author 蝈蝈
 */
@Service
@Path("/api/v1/activities")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ActivityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ActivityService activityService;

    @POST
    @Path("/")
    public Payload create(ActivitySaveDto saveDto) {
        return new Payload<>(activityService.create(saveDto));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String id, ActivitySaveDto saveDto) {
        return new Payload<>(activityService.update(id, saveDto));
    }

    @DELETE
    @Path("/{idList:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("idList") String idListStr) {
        return new Payload<>(activityService.delete(Lists.newArrayList(idListStr.split(SymbolEnum.COMMA.getState()))));
    }

    @GET
    @Path("/")
    public Payload findPage(@BeanParam ActivityQueryDto query,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        this.handleActivityQueryDto(query);
        return new Payload<>(activityService.findPage(query, page, size));
    }

    @GET
    @Path("/popup/")
    public Payload findPopupPage(@BeanParam ActivityQueryDto query,
                                 @QueryParam("page") @DefaultValue("1") Integer page,
                                 @QueryParam("size") @DefaultValue("10") Integer size) {
        this.handleActivityQueryDto(query);
        return new Payload<>(activityService.findPageWithOnlyActivity(query, page, size));
    }

    @GET
    @Path("/audit/")
    public Payload findAuditPage(@BeanParam ActivityQueryDto query,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        this.handleActivityQueryDto(query);
        return new Payload<>(activityService.findAuditPage(query, page, size));
    }

    /**
     * 处理活动查询DTO
     *
     * @param query 数据
     */
    private void handleActivityQueryDto(ActivityQueryDto query) {
        if (query == null) {
            return;
        }
        //手动处理前端日期传参
        query.setCreateTimeLeftRange(DateUtils.getDateFromString(query.getCreateTimeLeftRangeStr()));
        query.setCreateTimeRightRange(DateUtils.getDateFromString(query.getCreateTimeRightRangeStr()));
        query.setStartTimeLeftRange(DateUtils.getDateFromString(query.getStartTimeLeftRangeStr()));
        query.setStartTimeRightRange(DateUtils.getDateFromString(query.getStartTimeRightRangeStr()));
        query.setRealStartTimeLeftRange(DateUtils.getDateFromString(query.getRealStartTimeLeftRangeStr()));
        query.setRealStartTimeRightRange(DateUtils.getDateFromString(query.getRealStartTimeRightRangeStr()));
        query.setEndTimeLeftRange(DateUtils.getDateFromString(query.getEndTimeLeftRangeStr()));
        query.setEndTimeRightRange(DateUtils.getDateFromString(query.getEndTimeRightRangeStr()));
        if (StringUtils.isNotBlank(query.getStatusListStr())) {
            List<Integer> statusList = Lists.newArrayList();
            for (String statusStr : query.getStatusListStr().split(SymbolEnum.COMMA.getState())) {
                statusList.add(Integer.parseInt(statusStr));
            }
            query.setStatusList(statusList);
        }
        if (StringUtils.isNotBlank(query.getTypeListStr())) {
            List<Integer> typeList = Lists.newArrayList();
            for (String typeStr : query.getTypeListStr().split(SymbolEnum.COMMA.getState())) {
                typeList.add(Integer.parseInt(typeStr));
            }
            query.setTypeList(typeList);
        }
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String id) {
        return new Payload<>(activityService.detail(id));
    }

    @PUT
    @Path("/audit/{id:[a-zA-Z0-9]+}")
    public Payload audit(@PathParam("id") String id, AuditSaveDto saveDto) {
        return new Payload<>(activityService.audit(id, saveDto));
    }

    @PUT
    @Path("/auditOnBusiness/{id:[a-zA-Z0-9]+}")
    public Payload auditOnBusiness(@PathParam("id") String id, AuditSaveDto saveDto) {
        return new Payload<>(activityService.auditOnBusiness(id, saveDto));
    }

    @PUT
    @Path("/start/{id:[a-zA-Z0-9]+}")
    @Token(require = false)
    public Payload start(@PathParam("id") String id) {
        return new Payload<>(activityService.manualStart(id));
    }

    @PUT
    @Path("/end/{id:[a-zA-Z0-9]+}")
    @Token(require = false)
    public Payload end(@PathParam("id") String id) {
        return new Payload<>(activityService.manualEnd(id));
    }

    @PUT
    @Path("/stop/{id:[a-zA-Z0-9]+}")
    public Payload stop(@PathParam("id") String id) {
        return new Payload<>(activityService.stop(id));
    }

    @GET
    @Path("/h5")
    @Token(require = false)
    public Payload findH5Page(@QueryParam("type") Integer type,
                              @QueryParam("frontCategoryId") String frontCategoryId,
                              @QueryParam("name") String name,
                              @QueryParam("page") @DefaultValue("1") Integer page,
                              @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload<>(activityService.findH5Page(type, frontCategoryId, name, page, size));
    }

    @GET
    @Path("/h5/{id:[a-zA-Z0-9]+}")
    @Token(require = false)
    public Payload detailForH5(@PathParam("id") String id,
                               @QueryParam("personType") Integer personType) {
        return new Payload<>(activityService.detailForH5(id, personType));
    }

    @PUT
    @Path("/participate/{id:[a-zA-Z0-9]+}")
    public Payload participate(@PathParam("id") String id, @QueryParam("channelId") String channelId) {
        return new Payload<>(activityService.participate(id, channelId));
    }

    @PUT
    @Path("/boost/{id:[a-zA-Z0-9]+}")
    @Token(require = false)
    public Payload boost(@PathParam("id") String participantMemberActivityId, @QueryParam("memberId") String helperMemberId) {
        return new Payload<>(activityService.boost(participantMemberActivityId, helperMemberId));
    }
}
