package com.deepexi.activity.controller;

import com.deepexi.activity.service.MemberNumberCountService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author lxq
 */
@Service
@Path("/api/v1/memberCouponRelations")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class MemberNumberCountController {

    @Autowired
    MemberNumberCountService memberNumberCountService;

    /**
     * 会员 - 我的页面数字统计
     * @return
     */
    @GET
    @Path("/number/count")
    public Payload numberCount() {
        return new Payload(memberNumberCountService.numberCount());
    }
}
