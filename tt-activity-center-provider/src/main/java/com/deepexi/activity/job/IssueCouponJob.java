package com.deepexi.activity.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.deepexi.activity.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发券定时任务
 *
 * @author 蝈蝈
 */
@Component
public class IssueCouponJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(IssueCouponJob.class);

    @Resource
    private ActivityService activityService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("IssueCouponJob.execute：发券任务-启动......");
        Integer effectRows = activityService.issueCouponOnTime();
        logger.info("IssueCouponJob.execute：发券任务-结束-影响行数[{}]......", effectRows);
    }
}
