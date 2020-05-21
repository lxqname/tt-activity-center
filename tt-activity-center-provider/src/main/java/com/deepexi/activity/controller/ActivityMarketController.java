package com.deepexi.activity.controller;

import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.activity.service.ActivityMarketService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

/**
 * @author ADMIN
 */
@Service
@Path("/api/v1/activity/markets")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ActivityMarketController {

    @Autowired
    private ActivityMarketService activityMarketService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam ActivityDetailDto dto,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(activityMarketService.findPage(dto, page, size));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(activityMarketService.detail(pk));
    }
}
