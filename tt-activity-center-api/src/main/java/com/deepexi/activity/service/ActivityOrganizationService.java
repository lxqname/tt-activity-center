package com.deepexi.activity.service;

import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.activity.domain.eo.ActivityOrganization;
import com.deepexi.activity.domain.dto.ActivityOrganizationDto;
import java.util.*;

public interface ActivityOrganizationService {

    PageBean<ActivityOrganization> findPage(ActivityOrganizationDto eo, Integer page, Integer size);

    List<ActivityOrganization> findAll(ActivityOrganizationDto eo);

    ActivityOrganization detail(String pk);

    Boolean create(String activityId);

    Boolean update(String pk, ActivityOrganizationDto eo);

    Boolean create(ActivityOrganizationDto eo);

    Boolean delete(String... pk);

    Boolean deleteByActivityId(String activityId);

    List<ActivityOrganization> selectByActivityId(String id);

    /**
     * 根据活动ID查询
     *
     * @param activityId 活动ID
     * @return ActivityOrganization
     */
    ActivityOrganization queryByActivityId(String activityId);
}