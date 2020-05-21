package com.deepexi.activity.domain.dto;

import com.deepexi.activity.domain.eo.ActivityFloor;
import java.util.Collection;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

/**
 * @desc ac_activity_floor
 * @author 
 */
public class ActivityFloorDto implements Serializable {
    private String id;

    private String activityId;

    private String name;

    private String imgUrl;

    private Integer  type;

    private String typeParam;

    private Integer  sort;


    /**
     * 关联活动信息
     */
    private ActivityDetailDto activityDetailDto;

    /**
     * 关联商品列表
     */
    private List productList;


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


    public ActivityDetailDto getActivityDetailDto() {
        return activityDetailDto;
    }

    public void setActivityDetailDto(ActivityDetailDto activityDetailDto) {
        this.activityDetailDto = activityDetailDto;
    }

    public List getProductList() {
        return productList;
    }

    public void setProductList(List productList) {
        this.productList = productList;
    }
}

