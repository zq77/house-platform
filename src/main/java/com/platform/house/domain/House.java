package com.platform.house.domain;

import com.platform.house.constant.HouseCategory;
import com.platform.house.constant.HouseInfoStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 房产信息, 此模型用于新房
 * @author: xiaohai
 */
@Entity
@Table(name = "tb_house_info")
public class House extends BaseLocationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "disabled")
    private Boolean disabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private HouseInfoStatus status;

    @Column(name = "grab_site")
    private String grabSite;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private HouseCategory category;

    @Column(name = "total_area")
    private Double totalArea;

    @Column(name = "floorage")
    private Double floorage;

    @Column(name = "plot_ratio")
    private String plotRatio;

    @Column(name = "greening_rate")
    private String greeningRate;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "property_company")
    private String propertyCompany;

    @Column(name = "property_price")
    private String propertyPrice;

    @Column(name = "developers")
    private String developers;

    @Column(name = "property_rights_years")
    private String propertyRightsYears;

    @Column(name = "households")
    private String households;

    @Column(name = "building_count")
    private Integer buildingCount;

    @Column(name = "ave_floor_count")
    private Integer aveFloorCount;

    @Column(name = "build_year")
    private Integer buildYear;

    @Column(name = "selling_status")
    private String sellingStatus;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "selling_date")
    private String sellingDate;

    @Column(name = "completion_date")
    private String completionDate;

    @Column(name = "sales_department_address")
    private String salesDepartmentAddress;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "park_count")
    private String parkCount;

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

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public HouseCategory getCategory() {
        return category;
    }

    public void setCategory(HouseCategory category) {
        this.category = category;
    }

    public Integer getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Integer buildYear) {
        this.buildYear = buildYear;
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
