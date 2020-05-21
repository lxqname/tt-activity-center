package com.deepexi.activity.controller;


import com.deepexi.activity.domain.dto.ActivityQueryDto;
import com.deepexi.activity.domain.dto.GroupActivityCreateDto;
import com.deepexi.activity.service.GroupActivityService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

/**
 * 活动集列表Controller
 *
 * @author pangyingfa
 */
@Service
@Path("/api/v1/activities/group")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class GroupActivityController {

    @Autowired
    private GroupActivityService groupActivityService;

    @POST
    @Path("/")
    public Payload create(GroupActivityCreateDto createDto) {
        return new Payload<>(groupActivityService.create(createDto));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, GroupActivityCreateDto createDto) {
        return new Payload<>(groupActivityService.update(pk, createDto));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload<>(groupActivityService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload<>(groupActivityService.delete(pks));
    }

    @GET
    @Path("/")
    public Payload findPage(@BeanParam ActivityQueryDto queryDto,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload<>(groupActivityService.findPage(queryDto, page, size));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload<>(groupActivityService.detail(pk));
    }
}
