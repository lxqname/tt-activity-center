package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityMemberNumberCountDto;

/**
 * @author lxq
 */
public interface MemberNumberCountService {
    /**
     * 会员 - 我的页面数字统计
     * @return 统计数据
     */
    ActivityMemberNumberCountDto numberCount();
}
