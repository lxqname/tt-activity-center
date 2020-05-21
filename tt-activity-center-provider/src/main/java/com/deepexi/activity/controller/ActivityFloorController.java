package com.deepexi.activity.controller;

import com.deepexi.activity.service.ActivityFloorService;
import com.deepexi.activity.domain.dto.ActivityFloorDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/activityFloors")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ActivityFloorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActivityFloorService activityFloorService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam ActivityFloorDto eo,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(activityFloorService.findPage(eo, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam ActivityFloorDto eo) {
        return new Payload(activityFloorService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(activityFloorService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(ActivityFloorDto eo) {
        return new Payload(activityFloorService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, ActivityFloorDto eo) {
        return new Payload(activityFloorService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(activityFloorService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(activityFloorService.delete(pks));
    }
}
