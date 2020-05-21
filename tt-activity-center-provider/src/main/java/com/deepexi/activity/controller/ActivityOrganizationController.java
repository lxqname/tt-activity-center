package com.deepexi.activity.controller;

import com.deepexi.activity.service.ActivityOrganizationService;
import com.deepexi.activity.domain.dto.ActivityOrganizationDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/activityOrganizations")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ActivityOrganizationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActivityOrganizationService activityOrganizationService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam ActivityOrganizationDto eo,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(activityOrganizationService.findPage(eo, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam ActivityOrganizationDto eo) {
        return new Payload(activityOrganizationService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(activityOrganizationService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(ActivityOrganizationDto eo) {
        return new Payload(activityOrganizationService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, ActivityOrganizationDto eo) {
        return new Payload(activityOrganizationService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(activityOrganizationService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(activityOrganizationService.delete(pks));
    }
}
