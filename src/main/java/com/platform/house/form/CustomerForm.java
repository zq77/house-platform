package com.platform.house.form;

import com.platform.house.constant.*;
import com.platform.house.domain.Customer;
import com.platform.house.domain.House;
import com.platform.house.domain.ResoldHouse;
import com.platform.house.domain.User;
import com.platform.house.security.ShiroUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


/**
 * Created by Office on 2019/1/28.
 */
public class CustomerForm {

    private String name;

    private String mobile;

    private String nameBak;

    private String mobileBak;

    private String gender;

    private String level;

    private String houseNeed;

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

    private String category;

    private String source;

    private String purpose;

    private String content;

    private String status;

    public Customer toCustomer(ShiroUser user) {
        Customer customer = new Customer();
        customer.setCreateTime(new Date());
        User creator = new User();
        creator.setId(user.getId());
        customer.setCreator(creator);
        customer.setCreatorName(user.getRealname());
        customer.setEnabled(true);
        // 创建报备
        customer.setStatus(CustomerStatus.BB);
        return this.mergeCustomer(customer, user);
    }

    public Customer mergeCustomer(Customer customer, ShiroUser user) {
        customer.setName(this.getName());
        customer.setMobile(this.getMobile());
        customer.setNameBak(this.getNameBak());
        customer.setMobileBak(this.getMobileBak());
        String gender = this.getGender();
        if (StringUtils.isNotBlank(gender)) {
            customer.setGender(Gender.valueOf(gender));
        }
        String level = this.getLevel();
        if (StringUtils.isNotBlank(level)) {
            customer.setLevel(CustomerLevel.valueOf(level));
        }

        String houseNeed = this.getHouseNeed();
        if (StringUtils.isNotBlank(houseNeed)) {
            customer.setHouseNeed(CustomerHouseNeed.valueOf(houseNeed));
        }
        customer.setCity(this.getCity());
        customer.setArea(this.getArea());
        customer.setAreaName(this.getAreaName());
        customer.setStreet(this.getStreet());
        customer.setStreetName(this.getStreetName());

        customer.setHouseId(this.getHouseId());
        customer.setResoldHouseId(this.getResoldHouseId());
        customer.setHouseName(this.getHouseName());
        customer.setMinPrice(this.getMinPrice());
        customer.setMaxPrice(this.getMaxPrice());
        customer.setMinArea(this.getMinArea());
        customer.setMaxArea(this.getMaxArea());
        customer.setRoomCount(this.getRoomCount());

        String category = this.getCategory();
        if (StringUtils.isNotBlank(category)) {
            customer.setCategory(HouseCategory.valueOf(category));
        }

        String source = this.getSource();
        if (StringUtils.isNotBlank(source)) {
            customer.setSource(CustomerSource.valueOf(source));
        }

        String purpose = this.getPurpose();
        if (StringUtils.isNotBlank(purpose)) {
            customer.setPurpose(BuyPurpose.valueOf(purpose));
        }

        customer.setContent(this.getContent());
        customer.setUpdateTime(new Date());
        User updator = new User();
        updator.setId(user.getId());
        customer.setUpdater(updator);
        customer.setUpdaterName(user.getRealname());

        return customer;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHouseNeed() {
        return houseNeed;
    }

    public void setHouseNeed(String houseNeed) {
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
