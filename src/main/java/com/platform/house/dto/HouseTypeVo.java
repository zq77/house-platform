package com.platform.house.dto;

import com.platform.house.domain.HouseImage;
import com.platform.house.domain.HouseType;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-08-28 22:53
 */
public class HouseTypeVo {

    private Long id;

    private Long houseId;

    private String sellingStatus;

    private Integer roomCount;

    private Integer hallCount;

    private Integer kitchenCount;

    private Integer bathroomCount;

    private Double floorage;

    private Double price;

    private Double totalPrice;

    private Date createTime;

    private Date updateTime;

    private Long imageId;

    private String imageUrl;

    public HouseTypeVo() {
    }

    public HouseTypeVo(HouseType type) {
        BeanUtils.copyProperties(type, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(String sellingStatus) {
        this.sellingStatus = sellingStatus;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getHallCount() {
        return hallCount;
    }

    public void setHallCount(Integer hallCount) {
        this.hallCount = hallCount;
    }

    public Integer getKitchenCount() {
        return kitchenCount;
    }

    public void setKitchenCount(Integer kitchenCount) {
        this.kitchenCount = kitchenCount;
    }

    public Integer getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(Integer bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public Double getFloorage() {
        return floorage;
    }

    public void setFloorage(Double floorage) {
        this.floorage = floorage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
