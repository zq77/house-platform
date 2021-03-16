package com.platform.house.domain;

import com.platform.house.constant.HouseCategory;
import com.platform.house.constant.HouseInfoStatus;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 二手房
 * @author: xiaohai
 */
@Entity
@Table(name = "tb_resold_house_info")
public class ResoldHouse extends BaseLocationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "name")
    private String name;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private HouseInfoStatus status;

    @Column(name = "grab_site")
    private String grabSite;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_phone")
    private String ownerPhone;

    @Column(name = "block")
    private String block;

    @Column(name = "unit")
    private String unit;

    @Column(name = "room_num")
    private String roomNum;

    @Column(name = "user_id")
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

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

    @Column(name = "usage_area")
    private Double useageArea;

    @Column(name = "price")
    private Double price;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @Column(name = "lowest_price")
    private Double lowestPrice;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private HouseCategory category;

    @Column(name = "floor")
    private String floor;

    @Column(name = "total_floor")
    private String totalFloor;

    @Column(name = "features")
    @Type(type = "text")
    private String features;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "decoration")
    private String decoration;

    @Column(name = "use_years")
    private String useYear;

    @Column(name = "building_year")
    private String buildingYear;

    @Column(name = "property_nature")
    private String propertyNature;

    @Column(name = "property_year")
    private String propertyYear;

    @Column(name = "mortgage_status")
    private String mortgageStatus;

    @Column(name = "selling_status")
    private String sellingStatus;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "labels")
    private String labels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public HouseInfoStatus getStatus() {
        return status;
    }

    public void setStatus(HouseInfoStatus status) {
        this.status = status;
    }

    public String getGrabSite() {
        return grabSite;
    }

    public void setGrabSite(String grabSite) {
        this.grabSite = grabSite;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public HouseCategory getCategory() {
        return category;
    }

    public void setCategory(HouseCategory category) {
        this.category = category;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public Double getUseageArea() {
        return useageArea;
    }

    public void setUseageArea(Double useageArea) {
        this.useageArea = useageArea;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(String totalFloor) {
        this.totalFloor = totalFloor;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getUseYear() {
        return useYear;
    }

    public void setUseYear(String useYear) {
        this.useYear = useYear;
    }

    public String getBuildingYear() {
        return buildingYear;
    }

    public void setBuildingYear(String buildingYear) {
        this.buildingYear = buildingYear;
    }

    public String getPropertyNature() {
        return propertyNature;
    }

    public void setPropertyNature(String propertyNature) {
        this.propertyNature = propertyNature;
    }

    public String getPropertyYear() {
        return propertyYear;
    }

    public void setPropertyYear(String propertyYear) {
        this.propertyYear = propertyYear;
    }

    public String getMortgageStatus() {
        return mortgageStatus;
    }

    public void setMortgageStatus(String mortgageStatus) {
        this.mortgageStatus = mortgageStatus;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(String sellingStatus) {
        this.sellingStatus = sellingStatus;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

}
