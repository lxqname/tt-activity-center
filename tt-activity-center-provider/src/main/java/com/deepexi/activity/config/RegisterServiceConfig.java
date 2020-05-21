package com.deepexi.activity.config;

import com.deepexi.response.service.listener.RegisterResponseServiceListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @author: LuoGuang
 * @Package com.deepexi.activity.config
 * @Description:
 * @date: 2019/10/24 15:24
 */
@Component
public class RegisterServiceConfig {

    @Value(value = "${spring.application.name}")
    private String projectName;


    @Value("${project.domain.name}")
    private String domainName;

    @Bean
    public RegisterResponseServiceListener registerListener() {
        return new RegisterResponseServiceListener(projectName,domainName);
    }
}

