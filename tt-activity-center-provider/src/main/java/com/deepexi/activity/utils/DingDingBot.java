package com.deepexi.activity.utils;

import com.deepexi.common.domain.eo.SuperEntity;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 钉钉机器人
 *
 * @author admin
 */
public class DingDingBot {

    private static Logger logger = LoggerFactory.getLogger(DingDingBot.class);

    private final boolean enabled;

    public static class BotText extends SuperEntity {
        private final String content;

        public BotText(String content) {
            this.content = content;
        }
    }

    public static class BotAt extends SuperEntity {
        private boolean isAtAll = false;
    }

    public static final BotAt BOT_AT = new BotAt();

    public static class BotMessage extends SuperEntity {
        private final String msgtype = "text";
        private BotText text;
        private final BotAt at = BOT_AT;

        public BotMessage(String content) {
            this.text = new BotText(content);
        }

        @Override
        public String toString() {
            return new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setPrettyPrinting()
                    .create()
                    .toJson(this);
        }
    }

    private String url;

    public DingDingBot(String url, boolean enabled) {
        this.url = url;
        this.enabled = enabled;
    }

    public boolean sendTextMsg(String msg) {
        if (!enabled) {
            return false;
        }
        String sendMsg = new BotMessage(msg).toString();
        try {
            String s = HttpPostClient.postJson(url, sendMsg);
            return true;
        } catch (IOException e) {
            logger.error("发送钉钉机器人失败");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * https://oapi.dingtalk.com/robot/send?access_token=4e310ba620e129ab1420657915f4df3de4ff80adc56ad05d95608f2686520d03",
     */
    public static final DingDingBot DING_DING_BOT = new DingDingBot("https://oapi.dingtalk.com/robot/send?access_token=9e1d8175f2e81fdc1e1e0abc6501e7d68d29c15f2e88c3e4b823daa8031a69be",
            true);

    public static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("activity-schedule-pool-%d").daemon(true).build());

    public static void sendMsg(String msg) {
        try {
            DingDingBot.sendMsg(msg,Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMsg(String msg, String... obj) {
        try {
            String str = String.format(msg, obj);
            DingDingBot.sendMsg(str, Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMsg(String msg, String threadName) {
        executorService.execute(() -> {
            try {
                logger.error(msg);
                DING_DING_BOT.sendTextMsg("activity-center: " + threadName + ": " + msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}