package com.deepexi.activity.service;

import com.deepexi.activity.domain.dto.BoostRecordDto;
import com.deepexi.activity.domain.dto.BoostRecordQueryDto;

import java.util.List;

/**
 * 助力记录服务
 *
 * @author 蝈蝈
 */
public interface BoostRecordService {

    /**
     * 新增
     *
     * @param memberActivityId 会员活动ID
     * @param helperMemberId   助力者会员ID
     */
    void create(String memberActivityId, String helperMemberId);

    /**
     * 批量删除
     *
     * @param idList 主键ID列表
     * @return Boolean
     */
    Boolean delete(List<String> idList);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return List
     */
    List<BoostRecordDto> findList(BoostRecordQueryDto query);

    /**
     * 是否允许助力
     *
     * @param boostMemberId 助力者会员ID
     * @return Boolean true 允许，false 不允许
     */
    Boolean allowBoost(String boostMemberId);

    /**
     * 查询助力者的助力记录
     *
     * @param boostMemberId 助力会员ID
     * @return BoostRecordDto
     */
    BoostRecordDto queryByBoostMemberId(String boostMemberId);
}