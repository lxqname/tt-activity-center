package com.deepexi.activity.delayed;

import com.alibaba.dubbo.config.annotation.Reference;
import com.deepexi.activity.domain.eo.MemberCouponRelation;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.service.MemberCouponRelationService;
import com.deepexi.activity.service.MessageService;
import com.deepexi.equity.domain.eo.Coupon;
import com.deepexi.equity.service.CouponService;
import com.deepexi.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接收者
 *
 * @author 黎勇超
 */
@Component
@RabbitListener(queues = "delayed.couponExpireSoonRemind")
public class DelayedReceiver {

    private static final Logger logger = LoggerFactory.getLogger(DelayedReceiver.class);

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Resource
    private MessageService messageService;

    @Resource
    private MemberCouponRelationService memberCouponRelationService;

    @Reference(version = "${demo.service.version}", check = false)
    private CouponService couponService;

    @RabbitHandler
    public void process(String msg) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            MemberCouponRelation memberCouponRelation = JsonUtil.json2Bean(msg, MemberCouponRelation.class);
            // 过期时间
            Date expireTime = memberCouponRelationService.calculateCouponExpireTime(
                    memberCouponRelation.getCouponUseTimeFlag(), memberCouponRelation.getCouponUseEndTime(),
                    memberCouponRelation.getCouponValidDay(), memberCouponRelation.getReceiveTime());
            logger.info("票券过期时间:{},优惠券过期延迟消息接收时间:{}",
                    simpleDateFormat.format(expireTime), simpleDateFormat.format(new Date()));
            appRuntimeEnv.setTenantId(memberCouponRelation.getTenantCode());
            Coupon coupon = couponService.selectById(memberCouponRelation.getCouponId(), true);
            messageService.couponExpireSoon(memberCouponRelation.getMemberId(), memberCouponRelation.getId(), memberCouponRelation.getReceiveTime(),
                    coupon.getName(), coupon.getFaceValue(), expireTime);
        } catch (Exception e) {
            logger.error("DelayedReceiver.process：优惠券过期延迟消息处理失败，异常信息如下：", e);
        }
    }
}
