package com.deepexi.activity.utils;

import com.deepexi.util.pageHelper.PageBean;
import com.google.common.collect.Lists;

/**
 * PageBean工具类
 *
 * @author 蝈蝈
 * @since 2019年09月17日 15:49
 */
public class PageBeanUtils {

    /**
     * 获取默认PageBean
     *
     * @return PageBean
     */
    public static PageBean getDefaultPageBean() {
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setContent(Lists.newArrayList());
        return pageBean;
    }
}
