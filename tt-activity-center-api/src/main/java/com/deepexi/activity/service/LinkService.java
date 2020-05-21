package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.LinkCreateDto;
import com.deepexi.activity.domain.dto.LinkQueryDto;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.activity.domain.eo.Link;
import com.deepexi.activity.domain.dto.LinkDto;
import java.util.*;

/**
 * 活动链接
 * @author pangyingfa
 */
public interface LinkService {

    /**
     * 分页查询
     * @param eo
     * @param page
     * @param size
     * @return
     */
    PageBean<Link> findPage(LinkQueryDto eo, Integer page, Integer size);

    /**
     * 条件查询
     * @param eo
     * @return
     */
    List<Link> findAll(LinkDto eo);

    /**
     * 详情
     * @param pk
     * @return
     */
    Link detail(String pk);

    /**
     * 编辑
     * @param pk
     * @param eo
     * @return
     */
    Boolean update(String pk, LinkCreateDto eo);

    /**
     * 新增
     * @param eo
     * @return
     */
    Boolean create(LinkCreateDto eo);

    /**
     * 删除
     * @param pk
     * @return
     */
    Boolean delete(String... pk);
}