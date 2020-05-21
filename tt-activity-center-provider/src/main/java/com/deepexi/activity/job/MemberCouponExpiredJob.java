package com.deepexi.activity.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.deepexi.activity.service.MemberCouponRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 会员优惠券过期定时任务
 *
 * @author 蝈蝈
 * @since 2019年09月23日 17:46
 */
@Component
public class MemberCouponExpiredJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(MemberCouponExpiredJob.class);

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("MemberCouponExpiredJob.execute：会员优惠券过期处理任务-启动......");
        Integer effectRows = memberCouponRelationService.expire();
        logger.info("MemberCouponExpiredJob.execute：会员优惠券过期处理任务-结束-影响行数[{}]......", effectRows);
    }

}
