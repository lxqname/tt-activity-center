package com.deepexi.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.activity.domain.dto.LinkCreateDto;
import com.deepexi.activity.domain.dto.LinkDto;
import com.deepexi.activity.domain.dto.LinkQueryDto;
import com.deepexi.activity.domain.eo.Link;
import com.deepexi.activity.enums.LinkTypeEnum;
import com.deepexi.activity.extension.AppRuntimeEnv;
import com.deepexi.activity.mapper.LinkMapper;
import com.deepexi.activity.service.LinkService;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.BeanPowerHelper;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.wechat.domain.eo.MpAccount;
import com.deepexi.wechat.service.mp.MpAccountService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 活动链接service实现
 *
 * @author pangyingfa
 * @since 2019年9月16日17:35:15
 */
@Component
@Service(version = "${demo.service.version}")
public class LinkServiceImpl implements LinkService {

    private static final Logger logger = LoggerFactory.getLogger(LinkServiceImpl.class);

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Reference(version = "${demo.service.version}", check = false)
    private AccountService accountService;

    @Reference(version = "${demo.service.version}", check = false)
    private MpAccountService mpAccountService;


    @Override
    public PageBean findPage(LinkQueryDto queryDto, Integer page, Integer size) {

        queryDto.setTenantCode(appRuntimeEnv.getTenantId());
        PageHelper.startPage(page, size);
        //根据业务查询活动链接数据
        List<Link> linkList = linkMapper.findListByNameAndType(queryDto);
        //返回页面的pageBean
        PageBean<Link> linkPageBean = new PageBean<>(linkList);
        PageBean<LinkDto> responsePageBean = new PageBean<>();
        BeanUtils.copyProperties(linkPageBean, responsePageBean);

        List<LinkDto> responseList = new ArrayList<>(linkList.size());
        if (!CollectionUtil.isEmpty(linkList)) {
            for (Link link : linkList) {
                LinkDto dto = new LinkDto();
                this.copyProperties(link, dto);
                MpAccount mpAccount = mpAccountService.getByAccount(link.getWechatNumberId());
                //转WechatNumberId为名称
                dto.setWechatNumberName(mpAccount == null ? "" : mpAccount.getNickName());
                // 将创建人账号ID转为名称
                String name = accountService.getUsernameById(link.getCreatedBy());
                dto.setCreatedBy(StringUtil.isBlank(name) ? "" : name);
                responseList.add(dto);
            }
            responsePageBean.setContent(responseList);

        } else {
            responsePageBean.setContent(new ArrayList<>());
        }
        return responsePageBean;
    }

    @Override
    public List<Link> findAll(LinkDto eo) {
        List<Link> list = linkMapper.findList(eo);
        return list;
    }

    @Override
    public Link detail(String pk) {
        return linkMapper.selectById(pk);
    }

    @Override
    public Boolean create(LinkCreateDto eo) {
        this.validateInfo(eo);
        Link link = new Link();
        BeanUtils.copyProperties(eo, link);
        int result = linkMapper.insert(link);
        return result > 0;
    }

    @Override
    public Boolean update(String pk, LinkCreateDto eo) {
        this.validateInfo(eo);
        eo.setId(pk);
        int result = linkMapper.updateById(BeanPowerHelper.mapPartOverrider(eo, Link.class));
        return result > 0;
    }

    @Override
    public Boolean delete(String... pk) {
        int result = linkMapper.deleteByIds(pk);
        return result > 0;
    }

    /**
     * 验证数据合法性
     *
     * @param dto 数据
     */
    private void validateInfo(LinkCreateDto dto) {
        if (null == dto) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常"));
        }
        if (StringUtil.isBlank(dto.getName())) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入链接名称"));
        }
        int nameMaxLen = 20;
        if (dto.getName().length() > nameMaxLen) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "链接名称最多可输入20个字符"));
        }
        if (Objects.equals(LinkTypeEnum.PAGE_LINK.getState(), dto.getType())) {
            if (StringUtils.isBlank(dto.getUrl())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请输入页面链接"));
            }
        } else if (Objects.equals(LinkTypeEnum.PUBLIC.getState(), dto.getType())) {
            if (StringUtils.isBlank(dto.getWechatNumberId())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "请选择公众号"));
            }
        } else {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "链接类型参数异常"));
        }
    }

    /**
     * 将Eo对应字段复制到Dto中
     *
     * @param link EO
     * @param dto  DTO
     */
    private void copyProperties(Link link, LinkDto dto) {
        if (null == link || null == dto) {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "参数异常"));
        }
        dto.setId(link.getId());
        dto.setName(link.getName());
        dto.setType(link.getType());
        if (Objects.equals(LinkTypeEnum.PAGE_LINK.getState(), link.getType())) {
            dto.setUrl(link.getUrl());
            dto.setWechatNumberId("");
        } else if (Objects.equals(LinkTypeEnum.PUBLIC.getState(), link.getType())) {
            dto.setUrl("");
            dto.setWechatNumberId(link.getWechatNumberId());
        } else {
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "链接类型异常"));
        }
        dto.setRemark(link.getRemark());
        dto.setCreatedAt(link.getCreatedAt());
        dto.setCreatedBy(link.getCreatedBy());
    }


}