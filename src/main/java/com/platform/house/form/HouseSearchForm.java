package com.platform.house.form;

import com.platform.house.constant.DisplayType;

import java.util.List;

/**
 * @description: 新房搜索表单
 * @author: xiaohai
 * @create: 2018-06-26 22:51
 */
public class HouseSearchForm extends GlobalFilter {

    private String keywords;

    private String area;
    
    private String street;
    
    /**
     * 价格类型： 均价、总价
     */
    private String priceType;

    private Integer priceBegin;

    private Integer priceEnd;

    private Integer roomCount;
    
    private Double areaBegin;
    
    private Double areaEnd;
    
    private String category;
    
    private String decoration;

    private String status;

    private String grabSite;

    private List<Long> houseIdList;

    private Long wechatUserId;

    private String displayType;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Integer getPriceBegin() {
        return priceBegin;
    }

    public void setPriceBegin(Integer priceBegin) {
        this.priceBegin = priceBegin;
    }

    public Integer getPriceEnd() {
        return priceEnd;
    }

    public void setPriceEnd(Integer priceEnd) {
        this.priceEnd = priceEnd;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

	public Double getAreaBegin() {
		return areaBegin;
	}

	public void setAreaBegin(Double areaBegin) {
		this.areaBegin = areaBegin;
	}

	public Double getAreaEnd() {
		return areaEnd;
	}

	public void setAreaEnd(Double areaEnd) {
		this.areaEnd = areaEnd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDecoration() {
		return decoration;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrabSite() {
        return grabSite;
    }

    public void setGrabSite(String grabSite) {
        this.grabSite = grabSite;
    }

    public List<Long> getHouseIdList() {
        return houseIdList;
    }

    public void setHouseIdList(List<Long> houseIdList) {
        this.houseIdList = houseIdList;
    }

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
