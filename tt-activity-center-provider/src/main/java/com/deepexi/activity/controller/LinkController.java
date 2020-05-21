package com.deepexi.activity.controller;

import com.deepexi.activity.domain.dto.LinkCreateDto;
import com.deepexi.activity.domain.dto.LinkQueryDto;
import com.deepexi.activity.service.LinkService;
import com.deepexi.activity.domain.dto.LinkDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

/**
 * @author pangyingfa
 */
@Service
@Path("/api/v1/links")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class LinkController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LinkService linkService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam LinkQueryDto queryDto,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(linkService.findPage(queryDto, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam LinkDto eo) {
        return new Payload(linkService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(linkService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(LinkCreateDto eo) {
        return new Payload(linkService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, LinkCreateDto eo) {
        return new Payload(linkService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(linkService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(linkService.delete(pks));
    }
}
