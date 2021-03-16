package com.platform.house.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 户型
 * @author: xiaohai
 * @create: 2018-06-25 23:09
 */
@Entity
@Table(name = "tb_house_type_info")
public class HouseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "house_id")
    private Long houseId;

    @Column(name = "selling_status")
    private String sellingStatus;

    @Column(name = "room_count")
    private Integer roomCount;

    @Column(name = "hall_count")
    private Integer hallCount;

    @Column(name = "kitchen_count")
    private Integer kitchenCount;

    @Column(name = "bathroom_count")
    private Integer bathroomCount;

    @Column(name = "floorage")
    private Double floorage;

    @Column(name = "price")
    private Double price;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @JoinColumn(name = "image_url")
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
