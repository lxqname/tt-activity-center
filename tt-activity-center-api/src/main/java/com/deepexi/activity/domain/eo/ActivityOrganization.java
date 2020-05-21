package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.util.*;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;


/**
 * @desc ac_activity_organization
 * @author 
 */
//@ApiModel(description = "活动-组织关系")
@TableName("ac_activity_organization")
public class ActivityOrganization extends SuperEntity implements Serializable{

    //@ApiModelProperty(value = "活动ID")
    private String activityId;
    //@ApiModelProperty(value = "企业ID")
    private String enterpriseId;
    //@ApiModelProperty(value = "部门ID")
    private String organizationId;
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

