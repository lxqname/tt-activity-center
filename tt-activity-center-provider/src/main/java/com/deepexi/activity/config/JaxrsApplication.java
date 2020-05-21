package com.deepexi.activity.config;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述。
 * <p>
 * <br>==========================
 * <br> 公司：滴普科技
 * <br> 开发：hudong@deepexi.com
 * <br> 版本：1.0
 * <br> 创建时间：2018/4/28 14:13
 * <br>==========================
 *
 * @author hudong
 */
@Component
@ApplicationPath("/")
public class JaxrsApplication extends Application {

    private static final String VERSION = "1.0.0";
    private static final String HTTP = "http";
    private static final String CONTROLLER = "com.deepexi.activity.controller";

    /**
     * swagger2
     * 访问 -> http://localhost:8088/swagger.json
     * md -> https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-RESTEasy-2.X-Project-Setup-1.5
     */
    public JaxrsApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(VERSION);
        beanConfig.setSchemes(new String[]{HTTP});
        beanConfig.setResourcePackage(CONTROLLER);
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);
        return resources;
    }
}