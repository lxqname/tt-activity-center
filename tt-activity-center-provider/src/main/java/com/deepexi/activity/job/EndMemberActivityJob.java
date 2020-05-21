package com.deepexi.activity.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.deepexi.activity.service.MemberActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 结束会员活动定时任务
 *
 * @author 蝈蝈
 */
@Component
public class EndMemberActivityJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(EndMemberActivityJob.class);

    @Resource
    private MemberActivityService memberActivityService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("EndMemberActivityJob.execute：结束会员活动任务-启动......");
        List<String> memberActivityIdList = memberActivityService.end();
        logger.info("EndMemberActivityJob.execute：结束会员活动任务-结束-影响行数[{}]......影响会员活动ID[{}]",
                memberActivityIdList.size(), memberActivityIdList.toString());
    }
}
