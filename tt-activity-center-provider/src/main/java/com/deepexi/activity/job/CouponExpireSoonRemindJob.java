package com.deepexi.activity.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.deepexi.activity.service.MemberCouponRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 优惠券即将过期提醒定时任务
 *
 * @author 蝈蝈
 */
@Component
public class CouponExpireSoonRemindJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(CouponExpireSoonRemindJob.class);

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("CouponExpireSoonRemindJob.execute：优惠券即将过期提醒任务-启动......");
        Integer effectRows = memberCouponRelationService.couponExpireSoonRemind();
        logger.info("CouponExpireSoonRemindJob.execute：优惠券即将过期提醒任务-结束-影响行数[{}]......", effectRows);
    }
}
