package com.platform.house.domain;

import com.platform.house.constant.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: Customer 客源
 **/

@Entity
@Table(name = "tb_customer_source")
public class Customer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "name_bak")
	private String nameBak;
    
    @Column(name = "mobile_bak")
    private String mobileBak;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "level")
	@Enumerated(EnumType.STRING)
	private CustomerLevel level;
    
    @Column(name = "house_need")
	@Enumerated(EnumType.STRING)
    private CustomerHouseNeed houseNeed;
    
    @Column(name = "city")
    private String city;

	@Column(name = "area")
	private String area;

	@Column(name = "area_name")
	private String areaName;

	@Column(name = "street")
	private String street;

	@Column(name = "street_name")
	private String streetName;

	@ManyToOne
	@JoinColumn(name = "tb_house_id", insertable = false, updatable = false)
	private House house;

	@Column(name = "tb_house_id")
	private Long houseId;

	@ManyToOne
	@JoinColumn(name = "tb_resold_house_id", insertable = false, updatable = false)
	private ResoldHouse resoldHouse;

	@Column(name = "tb_resold_house_id")
	private Long resoldHouseId;

	@Column(name = "house_name")
	private String houseName;
    
    @Column(name = "min_price")
    private Double minPrice;

	@Column(name = "max_price")
	private Double maxPrice;

	@Column(name = "min_area")
	private Double minArea;

	@Column(name = "max_area")
	private Double maxArea;

	@Column(name = "room_count")
	private Integer roomCount;

	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private HouseCategory category;

	@Column(name = "source")
	@Enumerated(EnumType.STRING)
	private CustomerSource source;

	@Column(name = "purpose")
	@Enumerated(EnumType.STRING)
	private BuyPurpose purpose;

	@Column(name = "content")
	private String content;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private CustomerStatus status;
    
    @Column(name = "create_time")
    private Date createTime;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;

	@Column(name = "creator_name")
	private String creatorName;
    
    @Column(name = "update_time")
    private Date updateTime;

	@ManyToOne
	@JoinColumn(name = "updater_id")
	private User updater;

	@Column(name = "updater_name")
	private String updaterName;

	@Column(name = "enabled")
	private Boolean enabled;

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

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public ResoldHouse getResoldHouse() {
		return resoldHouse;
	}

	public void setResoldHouse(ResoldHouse resoldHouse) {
		this.resoldHouse = resoldHouse;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}
}
