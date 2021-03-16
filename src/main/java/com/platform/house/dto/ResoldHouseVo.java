package com.platform.house.dto;

import com.platform.house.constant.HouseCategory;
import com.platform.house.constant.HouseInfoStatus;
import com.platform.house.domain.BaseLocationInfo;
import com.platform.house.domain.ResoldHouseImage;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @description: 二手房页面展示模型
 */
public class ResoldHouseVo extends LocationInfoVo {

    private Long id;

    private String name;

    private HouseInfoStatus status;

    private String title;

    private String subTitle;
    
    private Boolean isFeatured;

    private Date createTime;

    private Date updateTime;

    private Integer roomCount;

    private Integer hallCount;

    private Integer kitchenCount;

    private Integer bathroomCount;

    private Double floorage;

    private Double price;

    private Double totalPrice;

    private HouseCategory category;

    private String features;

    private String introduction;

    private Boolean disabled;

    private List<BaseImageVo> images;
    
    private String ownerName;
    
    private String ownerPhone;
    
    private String block;
    
    private String unit;
    
    private String roomNum;
    
    private Double useageArea;
    
    private Double lowestPrice;
    
    private String floor;
    
    private String totalFloor;
    
    private String orientation;
    
    private String decoration;
    
    private String useYear;
    
    private String buildingYear;
    
    private String propertyNature;
    
    private String propertyYear;
    
    private String mortgageStatus;
    
    private String sellingStatus;
    
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

    public HouseInfoStatus getStatus() {
        return status;
    }

    public void setStatus(HouseInfoStatus status) {
        this.status = status;
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

    public List<BaseImageVo> getImages() {
        return images;
    }

    public void setImages(List<BaseImageVo> images) {
        this.images = images;
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
