package com.deepexi.activity.controller;

import com.deepexi.activity.domain.dto.MemberActivityQueryDto;
import com.deepexi.activity.service.MemberActivityService;
import com.deepexi.common.annotation.Token;
import com.deepexi.common.enums.SymbolEnum;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * 会员活动Controller
 *
 * @author 蝈蝈
 * @since 2019年10月15日 17:50
 */
@Service
@Path("/api/v1/memberActivities")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class MemberActivityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MemberActivityService memberActivityService;

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    @Token(require = false)
    public Payload detail(@PathParam("id") String id) {
        return new Payload<>(memberActivityService.detail(id));
    }

    @GET
    @Path("/participate")
    @Token(require = false)
    public Payload participateDetail(@QueryParam("activityId") String activityId, @QueryParam("memberId") String memberId) {
        return new Payload<>(memberActivityService.participateDetail(activityId, memberId));
    }

    @GET
    @Path("/boost")
    @Token(require = false)
    public Payload boostDetail(@QueryParam("memberId") String memberId) {
        return new Payload<>(memberActivityService.boostDetail(memberId));
    }

    @PUT
    @Path("/end/{id:[a-zA-Z0-9]+}")
    @Token(require = false)
    public Payload end(@PathParam("id") String id) {
        return new Payload<>(memberActivityService.manualEnd(id));
    }

    @GET
    @Path("/h5")
    public Payload findPageForH5(@QueryParam("statusList") String statusListStr,
                                 @QueryParam("page") @DefaultValue("1") Integer page,
                                 @QueryParam("size") @DefaultValue("10") Integer size) {
        List<Integer> statusList = Lists.newArrayList();
        if (StringUtils.isNotBlank(statusListStr)) {
            for (String statusStr : statusListStr.split(SymbolEnum.COMMA.getState())) {
                statusList.add(Integer.parseInt(statusStr));
            }
        }
        return new Payload<>(memberActivityService.findPageForH5(statusList, page, size));
    }

    @GET
    @Path("/award/count")
    public Payload countAward(@QueryParam("activityId") String activityId,
                              @QueryParam("memberMobile") String memberMobile,
                              @QueryParam("page") @DefaultValue("1") Integer page,
                              @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload<>(memberActivityService.countAward(activityId, memberMobile, page, size));
    }

    @GET
    @Path("/member/count")
    public Payload countMember(@QueryParam("activityId") String activityId) {
        return new Payload<>(memberActivityService.countMember(activityId));
    }

    @GET
    @Path("/status/count")
    public Payload countByStatus(@QueryParam("activityId") String activityId) {
        return new Payload<>(memberActivityService.countByStatus(activityId));
    }

    @GET
    @Path("/")
    public Payload findPage(@BeanParam MemberActivityQueryDto query,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload<>(memberActivityService.findPage(query, page, size));
    }
}
