package com.deepexi.activity.domain.dto;

import com.deepexi.activity.domain.eo.ActivityOrganization;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

/**
 * @desc ac_activity_organization
 * @author 
 */
public class ActivityOrganizationDto implements Serializable {
    private String id;

    private String activityId;

    private String enterpriseId;

    private String organizationId;



    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setActivityId(String activityId){
        this.activityId = activityId;
    }

    public String getActivityId(){
        return this.activityId;
    }

    public void setEnterpriseId(String enterpriseId){
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseId(){
        return this.enterpriseId;
    }

    public void setOrganizationId(String organizationId){
        this.organizationId = organizationId;
    }

    public String getOrganizationId(){
        return this.organizationId;
    }

}

