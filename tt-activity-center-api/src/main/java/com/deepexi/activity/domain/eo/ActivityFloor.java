package com.deepexi.activity.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.util.*;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;


/**
 * @desc ac_activity_floor
 * @author 
 */
//@ApiModel(description = "活动楼层")
@TableName("ac_activity_floor")
public class ActivityFloor extends SuperEntity implements Serializable{

    //@ApiModelProperty(value = "活动ID")
    private String activityId;
    //@ApiModelProperty(value = "名称")
    private String name;
    //@ApiModelProperty(value = "图片URL")
    private String imgUrl;
    //@ApiModelProperty(value = "关联类型（1-商品、2-活动、3-自定义url 、4-无）")
    private Integer  type;
    //@ApiModelProperty(value = "关联类型参数（逗号分隔）")
    private String typeParam;
    //@ApiModelProperty(value = "排序 小在前")
    private Integer  sort;
    public void setActivityId(String activityId){
        this.activityId = activityId;
    }

    public String getActivityId(){
        return this.activityId;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public String getImgUrl(){
        return this.imgUrl;
    }
    public void setType(Integer  type){
        this.type = type;
    }

    public Integer  getType(){
        return this.type;
    }
    public void setTypeParam(String typeParam){
        this.typeParam = typeParam;
    }

    public String getTypeParam(){
        return this.typeParam;
    }
    public void setSort(Integer  sort){
        this.sort = sort;
    }

    public Integer  getSort(){
        return this.sort;
    }


}

