package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 对象DTO
 *
 * @author 蝈蝈
 * @since 2019年10月12日 17:45
 */
public class TargetDto implements Serializable {

    /**
     * 活动对象类型（0-全部、1-会员分组、2-关注公众号、3-会员标签）
     */
    private Integer targetType;

    /**
     * 活动对象列表
     */
    private List<String> targetIdList;

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public List<String> getTargetIdList() {
        return targetIdList;
    }

    public void setTargetIdList(List<String> targetIdList) {
        this.targetIdList = targetIdList;
    }
}
