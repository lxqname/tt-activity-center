package com.deepexi.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.activity.domain.dto.LinkDto;
import com.deepexi.activity.domain.dto.LinkQueryDto;
import com.deepexi.activity.domain.eo.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author pangyingfa
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

    List<Link> findList(@Param("eo") LinkDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<LinkDto> eo);

    int batchUpdate(@Param("eo") List<LinkDto> eo);

    /**
     * 根据名称和类型查询活动链接列表
     * @param linkQueryDto
     * @return
     */
    List<Link> findListByNameAndType(@Param("query") LinkQueryDto linkQueryDto);
}
