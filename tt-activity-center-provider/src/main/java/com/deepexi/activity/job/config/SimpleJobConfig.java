/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.deepexi.activity.job.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

/**
 * 定时任务配置
 *
 * @author 蝈蝈
 */
@Configuration
@PropertySource("job.properties")
public class SimpleJobConfig {
    
    @Resource
    private ZookeeperRegistryCenter regCenter;
    
    @Resource
    private JobEventConfiguration jobEventConfiguration;

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }

    @Resource
    private SimpleJob startActivityJob;

    @Bean(initMethod = "init")
    public JobScheduler startActivitySchedulerJob(@Value("${elasticJob.startActivityJob.cron}") final String cron,
                                                  @Value("${elasticJob.startActivityJob.shardingTotalCount}") final int shardingTotalCount,
                                                  @Value("${elasticJob.startActivityJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(startActivityJob, regCenter, getLiteJobConfiguration(startActivityJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    @Resource
    private SimpleJob endActivityJob;

    @Bean(initMethod = "init")
    public JobScheduler endActivitySchedulerJob(@Value("${elasticJob.endActivityJob.cron}") final String cron,
                                                @Value("${elasticJob.endActivityJob.shardingTotalCount}") final int shardingTotalCount,
                                                @Value("${elasticJob.endActivityJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(endActivityJob, regCenter, getLiteJobConfiguration(endActivityJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    @Resource
    private SimpleJob endMemberActivityJob;

    @Bean(initMethod = "init")
    public JobScheduler endMemberActivitySchedulerJob(@Value("${elasticJob.endMemberActivityJob.cron}") final String cron,
                                                      @Value("${elasticJob.endMemberActivityJob.shardingTotalCount}") final int shardingTotalCount,
                                                      @Value("${elasticJob.endMemberActivityJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(endMemberActivityJob, regCenter, getLiteJobConfiguration(endMemberActivityJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    @Resource
    private SimpleJob couponExpireSoonRemindJob;

    @Bean(initMethod = "init")
    public JobScheduler couponExpireSoonRemindSchedulerJob(@Value("${elasticJob.couponExpireSoonRemindJob.cron}") final String cron,
                                                           @Value("${elasticJob.couponExpireSoonRemindJob.shardingTotalCount}") final int shardingTotalCount,
                                                           @Value("${elasticJob.couponExpireSoonRemindJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(couponExpireSoonRemindJob, regCenter, getLiteJobConfiguration(couponExpireSoonRemindJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    @Resource
    private SimpleJob issueCouponJob;

    @Bean(initMethod = "init")
    public JobScheduler issueCouponSchedulerJob(@Value("${elasticJob.issueCouponJob.cron}") final String cron,
                                                @Value("${elasticJob.issueCouponJob.shardingTotalCount}") final int shardingTotalCount,
                                                @Value("${elasticJob.issueCouponJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(issueCouponJob, regCenter, getLiteJobConfiguration(issueCouponJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    @Resource
    private SimpleJob memberCouponExpiredJob;

    @Bean(initMethod = "init")
    public JobScheduler memberCouponExpiredSchedulerJob(@Value("${elasticJob.memberCouponExpiredJob.cron}") final String cron,
                                                        @Value("${elasticJob.memberCouponExpiredJob.shardingTotalCount}") final int shardingTotalCount,
                                                        @Value("${elasticJob.memberCouponExpiredJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(memberCouponExpiredJob, regCenter, getLiteJobConfiguration(memberCouponExpiredJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }

    @Resource
    private SimpleJob activityRemindJob;

    @Bean(initMethod = "init")
    public JobScheduler activityRemindSchedulerJob(@Value("${elasticJob.activityRemindJob.cron}") final String cron,
                                                   @Value("${elasticJob.activityRemindJob.shardingTotalCount}") final int shardingTotalCount,
                                                   @Value("${elasticJob.activityRemindJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(activityRemindJob, regCenter, getLiteJobConfiguration(activityRemindJob.getClass(), cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration);
    }
}
