package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.BoostRecordDto;
import com.deepexi.activity.domain.dto.BoostRecordQueryDto;
import com.deepexi.activity.domain.eo.BoostRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 助力记录Mapper
 *
 * @author 蝈蝈
 */
@Mapper
public interface BoostRecordMapper extends BaseMapper<BoostRecord> {

    /**
     * 批量逻辑删除
     *
     * @param idList   主键ID列表
     * @param updateBy 更新人
     * @return int 影响行数
     */
    int deleteByIds(@Param("idList") List<String> idList, @Param("updateBy") String updateBy);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return List
     */
    List<BoostRecordDto> findList(@Param("query") BoostRecordQueryDto query);

    /**
     * 统计
     *
     * @param query 查询条件
     * @return int
     */
    int count(@Param("query") BoostRecordQueryDto query);
}
