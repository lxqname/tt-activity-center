package com.deepexi.activity.controller;

import com.deepexi.activity.domain.dto.MemberCouponRelationQueryDto;
import com.deepexi.activity.service.MemberCouponRelationService;
import com.deepexi.common.annotation.Token;
import com.deepexi.common.constant.Constants;
import com.deepexi.common.enums.SymbolEnum;
import com.deepexi.common.util.DateUtils;
import com.deepexi.response.service.annotation.RegisterResponseServiceAnnotation;
import com.deepexi.response.service.domain.HttpActionParameter;
import com.deepexi.response.service.enums.BooleanEnum;
import com.deepexi.response.service.enums.NodeTypeEnum;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;

/**
 * 会员优惠券关系Controller
 *
 * @author 蝈蝈
 * @since 2019年10月15日 17:38
 */
@Service
@Path("/api/v1/memberCouponRelations")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class MemberCouponRelationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @GET
    @Path("/h5/boostActivity/list")
    public Payload findAll(@QueryParam("activityId") String activityId) {
        return new Payload<>(memberCouponRelationService.findActivityAwardList(activityId));
    }

    @PUT
    @Path("/receive")
    public Payload receive(@QueryParam("idList") String idListStr) {
        return new Payload<>(memberCouponRelationService.receive(Lists.newArrayList(idListStr.split(SymbolEnum.COMMA.getState()))));
    }

    @GET
    @Path("/")
    public Payload findPage(@BeanParam MemberCouponRelationQueryDto query,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload<>(memberCouponRelationService.findPage(query, page, size));
    }

    @GET
    @Path("/activity/verificationStatus/count")
    public Payload countNumAndMoneyByStatus(@QueryParam("activityId") String activityId) {
        return new Payload<>(memberCouponRelationService.countNumAndAmountByStatus(activityId));
    }

    /**
     * 给对象会员主体发送指定优惠卷/优惠包 用于流程引擎
     * @param h
     * @return
     */
    @POST
    @Path("/send/coupon")
    @Token(require = false)
    @RegisterResponseServiceAnnotation(
            name = "发送指定优惠卷/优惠包给对象会员",
            type = NodeTypeEnum.SEQ,
            asynchronous = BooleanEnum.FALSE,
            metaData = "{\"type\":1}",
            url = "tt-activity-center/api/v1/memberCouponRelations/send/coupon",
            parameters = "[{\"key\":\"couponId\",\"name\":\"优惠券\",\"type\":3,\"dataSource\":\"COUPON\"}]")
    public Payload sendCoupon(HttpActionParameter h) {
        return new Payload<>(memberCouponRelationService.sendCoupon(h.getValueByConstantMap(Constants.PROCESS_ENGINE_OBJECT_ID_KEY), h.getValueByParameterMap("couponId")));
    }

    @GET
    @Path("/h5/mine/coupon")
    public Payload findMyCoupon(@BeanParam MemberCouponRelationQueryDto query,
                                @QueryParam("page") @DefaultValue("1") Integer page,
                                @QueryParam("size") @DefaultValue("10") Integer size) {
        if (StringUtils.isNotBlank(query.getVerificationStatusListStr())) {
            List<Integer> verificationStatusList = Lists.newArrayList();
            for (String verificationStatusStr : query.getVerificationStatusListStr().split(SymbolEnum.COMMA.getState())) {
                verificationStatusList.add(Integer.parseInt(verificationStatusStr));
            }
            query.setVerificationStatusList(verificationStatusList);
        }
        return new Payload<>(memberCouponRelationService.findMyCoupon(query, page, size));
    }

    @GET
    @Path("/h5/mine/coupon/{id:[a-zA-Z0-9]+}")
    public Payload myCouponDetail(@PathParam("id") String id) {
        return new Payload<>(memberCouponRelationService.myCouponDetail(id));
    }

    @GET
    @Path("/h5/mine/award")
    public Payload findAwardPage(@QueryParam("page") @DefaultValue("1") Integer page,
                                 @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload<>(memberCouponRelationService.findAwardPage(page, size));
    }

    @GET
    @Path("/use/check/store/{id:[a-zA-Z0-9]+}")
    public Payload checkUseStore(@PathParam("id") String id,
                                 @QueryParam("storeId") String storeId) {
        return new Payload<>(memberCouponRelationService.checkUseStore(id, storeId));
    }

    @PUT
    @Path("/use/{id:[a-zA-Z0-9]+}")
    public Payload use(@PathParam("id") String id,
                       @QueryParam("storeId") String storeId) {
        return new Payload<>(memberCouponRelationService.use(id, storeId));
    }

    @GET
    @Path("/merchant/verification/record")
    public Payload findVerificationRecordPage(@QueryParam("startTime") String startTimeStr,
                                              @QueryParam("endTime") String endTimeStr,
                                              @QueryParam("page") @DefaultValue("1") Integer page,
                                              @QueryParam("size") @DefaultValue("10") Integer size) {
        Date startTime = DateUtils.getDateFromString(startTimeStr);
        Date endTime = DateUtils.getDateFromString(endTimeStr);
        return new Payload<>(memberCouponRelationService.findVerificationRecordPage(startTime, endTime, page, size));
    }

    @GET
    @Path("/merchant/{id:[a-zA-Z0-9]+}")
    public Payload detailForMerchant(@PathParam("id") String id) {
        return new Payload<>(memberCouponRelationService.detailForMerchant(id));
    }

    @GET
    @Path("/h5/mine/coupon/idList")
    public Payload findIdList(@QueryParam("activityId") String activityId) {
        return new Payload<>(memberCouponRelationService.findIdList(activityId));
    }
}
