package com.platform.house.dto;

import com.platform.house.constant.*;
import com.platform.house.domain.Customer;
import com.platform.house.domain.CustomerFollow;
import com.platform.house.domain.CustomerTake;
import com.platform.house.domain.User;
import com.platform.house.services.UserService;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Office on 2019/1/28.
 */
public class CustomerDto {
    private Long id;

    private String name;

    private String mobile;

    private String nameBak;

    private String mobileBak;

    private Gender gender;

    private CustomerLevel level;

    private CustomerHouseNeed houseNeed;

    private String city;

    private String area;

    private String street;

    private String areaName;

    private String streetName;

    private Long houseId;

    private Long resoldHouseId;

    private String houseName;

    private Double minPrice;

    private Double maxPrice;

    private Double minArea;

    private Double maxArea;

    private Integer roomCount;

    private HouseCategory category;

    private CustomerSource source;

    private BuyPurpose purpose;

    private String content;

    private CustomerStatus status;

    private Date createTime;

    private User creator;

    private String creatorName;

    private Date updateTime;

    private User updater;

    private String updaterName;

    private List<CustomerFollowDto> customerFollowList;

    private List<CustomerTakeDto> customerTakeList;

    public CustomerDto(Customer customer) {
        BeanUtils.copyProperties(customer, this);
        User creator = UserService.getAccessUserDetail(customer.getCreator());
        User updater = UserService.getAccessUserDetail(customer.getUpdater());
        this.setCreator(creator);
        this.setUpdater(updater);
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNameBak() {
        return nameBak;
    }

    public void setNameBak(String nameBak) {
        this.nameBak = nameBak;
    }

    public String getMobileBak() {
        return mobileBak;
    }

    public void setMobileBak(String mobileBak) {
        this.mobileBak = mobileBak;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public CustomerLevel getLevel() {
        return level;
    }

    public void setLevel(CustomerLevel level) {
        this.level = level;
    }

    public CustomerHouseNeed getHouseNeed() {
        return houseNeed;
    }

    public void setHouseNeed(CustomerHouseNeed houseNeed) {
        this.houseNeed = houseNeed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getResoldHouseId() {
        return resoldHouseId;
    }

    public void setResoldHouseId(Long resoldHouseId) {
        this.resoldHouseId = resoldHouseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinArea() {
        return minArea;
    }

    public void setMinArea(Double minArea) {
        this.minArea = minArea;
    }

    public Double getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(Double maxArea) {
        this.maxArea = maxArea;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public HouseCategory getCategory() {
        return category;
    }

    public void setCategory(HouseCategory category) {
        this.category = category;
    }

    public CustomerSource getSource() {
        return source;
    }

    public void setSource(CustomerSource source) {
        this.source = source;
    }

    public BuyPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(BuyPurpose purpose) {
        this.purpose = purpose;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public List<CustomerFollowDto> getCustomerFollowList() {
        return customerFollowList;
    }

    public void setCustomerFollowList(List<CustomerFollowDto> customerFollowList) {
        this.customerFollowList = customerFollowList;
    }

    public List<CustomerTakeDto> getCustomerTakeList() {
        return customerTakeList;
    }

    public void setCustomerTakeList(List<CustomerTakeDto> customerTakeList) {
        this.customerTakeList = customerTakeList;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}
