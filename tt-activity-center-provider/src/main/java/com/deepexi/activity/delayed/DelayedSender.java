package com.deepexi.activity.delayed;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送者
 *
 * @author 黎勇超
 */
@Component
public class DelayedSender {

    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send(String msg, long time) {
        rabbitTemplate.convertAndSend(DelayedConfig.EXCHANGE_NAME, DelayedConfig.QUEUE_NAME, msg, message -> {
            message.getMessageProperties().setHeader("x-delay", time);
            return message;
        });
    }
}
