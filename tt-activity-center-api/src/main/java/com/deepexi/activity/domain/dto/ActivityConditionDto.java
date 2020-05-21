package com.deepexi.activity.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 活动条件DTO
 *
 * @author 蝈蝈
 */
public class ActivityConditionDto implements Serializable {

    /**
     * 会员分组ID列表
     */
    private List<String> memberGroupIdList;

    /**
     * 公众号原始ID列表
     */
    private List<String> weChatNumberIdList;

    public List<String> getMemberGroupIdList() {
        return memberGroupIdList;
    }

    public void setMemberGroupIdList(List<String> memberGroupIdList) {
        this.memberGroupIdList = memberGroupIdList;
    }

    public List<String> getWeChatNumberIdList() {
        return weChatNumberIdList;
    }

    public void setWeChatNumberIdList(List<String> weChatNumberIdList) {
        this.weChatNumberIdList = weChatNumberIdList;
    }
}
