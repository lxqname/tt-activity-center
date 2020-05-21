package com.deepexi.activity.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.deepexi.activity.service.ActivityRemindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 活动提醒定时任务
 *
 * @author 蝈蝈
 * @since 2019年11月01日 10:53
 */
@Component
public class ActivityRemindJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(ActivityRemindJob.class);

    @Resource
    private ActivityRemindService activityRemindService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("RemindJob.execute：活动提醒任务-启动......");
        Integer effectRows = activityRemindService.remind();
        logger.info("RemindJob.execute：活动提醒任务-结束-影响行数[{}]......", effectRows);
    }
}
