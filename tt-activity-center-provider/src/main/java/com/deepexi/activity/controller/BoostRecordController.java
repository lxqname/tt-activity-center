package com.deepexi.activity.controller;

import com.deepexi.activity.domain.dto.BoostRecordQueryDto;
import com.deepexi.activity.service.BoostRecordService;
import com.deepexi.common.annotation.Token;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 助力记录Controller
 *
 * @author 蝈蝈
 */
@Service
@Path("/api/v1/boostRecords")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class BoostRecordController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BoostRecordService boostRecordService;

    @GET
    @Path("/list")
    @Token(require = false)
    public Payload findAll(@BeanParam BoostRecordQueryDto query) {
        return new Payload<>(boostRecordService.findList(query));
    }
}
