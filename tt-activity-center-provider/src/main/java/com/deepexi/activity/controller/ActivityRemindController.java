package com.deepexi.activity.controller;

import com.deepexi.activity.service.ActivityRemindService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * 活动提醒Controller
 *
 * @author 蝈蝈
 */
@Service
@Path("/api/v1/activityReminds")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ActivityRemindController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ActivityRemindService activityRemindService;

    @POST
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload create(@PathParam("id") String activityId) {
        return new Payload<>(activityRemindService.create(activityId));
    }

    @DELETE
    @Path("/cancel/{id:[a-zA-Z0-9]+}")
    public Payload cancel(@PathParam("id") String activityId) {
        return new Payload<>(activityRemindService.cancel(activityId));
    }
}
