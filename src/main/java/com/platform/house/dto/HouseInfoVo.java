package com.platform.house.dto;

import com.platform.house.constant.HouseCategory;
import com.platform.house.constant.HouseInfoStatus;

import java.util.Date;

import javax.persistence.Column;

/**
 * @description: 新房详情页面模型
 * @author: xiaohai
 * @create: 2018-06-26 23:47
 */
public class HouseInfoVo extends LocationInfoVo {

    private Long id;

    private String name;
    
    private Boolean isFeatured;

    private HouseInfoStatus status;

    private String introduction;

    private Date createTime;

    private Date updateTime;

    private Double totalArea;

    private Double floorage;

    private String plotRatio;

    private String greeningRate;

    private String propertyType;

    private String propertyCompany;

    private String propertyPrice;

    private String developers;

    private String propertyRightsYears;

    private String households;

    private Integer buildingCount;

    private Integer aveFloorCount;

    private Integer buildYear;

    private String sellingStatus;

    private Double price;

    private Double totalPrice;

    private String sellingDate;

    private String completionDate;

    private String salesDepartmentAddress;

    private String contactName;

    private String contactPhone;

    private HouseCategory category;

    private String parkCount;
    
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public Double getFloorage() {
        return floorage;
    }

    public void setFloorage(Double floorage) {
        this.floorage = floorage;
    }

    public String getPlotRatio() {
        return plotRatio;
    }

    public void setPlotRatio(String plotRatio) {
        this.plotRatio = plotRatio;
    }

    public String getGreeningRate() {
        return greeningRate;
    }

    public void setGreeningRate(String greeningRate) {
        this.greeningRate = greeningRate;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyCompany() {
        return propertyCompany;
    }

    public void setPropertyCompany(String propertyCompany) {
        this.propertyCompany = propertyCompany;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getPropertyRightsYears() {
        return propertyRightsYears;
    }

    public void setPropertyRightsYears(String propertyRightsYears) {
        this.propertyRightsYears = propertyRightsYears;
    }

    public String getHouseholds() {
        return households;
    }

    public void setHouseholds(String households) {
        this.households = households;
    }

    public Integer getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(Integer buildingCount) {
        this.buildingCount = buildingCount;
    }

    public Integer getAveFloorCount() {
        return aveFloorCount;
    }

    public void setAveFloorCount(Integer aveFloorCount) {
        this.aveFloorCount = aveFloorCount;
    }

    public String getSellingStatus() {
        return sellingStatus;
    }

    public void setSellingStatus(String sellingStatus) {
        this.sellingStatus = sellingStatus;
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

    public String getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getSalesDepartmentAddress() {
        return salesDepartmentAddress;
    }

    public void setSalesDepartmentAddress(String salesDepartmentAddress) {
        this.salesDepartmentAddress = salesDepartmentAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Integer buildYear) {
        this.buildYear = buildYear;
    }

    public HouseCategory getCategory() {
        return category;
    }

    public void setCategory(HouseCategory category) {
        this.category = category;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getParkCount() {
        return parkCount;
    }

    public void setParkCount(String parkCount) {
        this.parkCount = parkCount;
    }

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}
    
}
