package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.ActivityDetailDto;
import com.deepexi.util.pageHelper.PageBean;

/**
 * 移动推广端推广市场
 * @author pangyingfa
 */
public interface ActivityMarketService {

    PageBean findPage(ActivityDetailDto dto, Integer page, Integer size);

    Object detail(String pk);
}
