package com.platform.house.domain;

import com.platform.house.constant.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: CustomerTakeDto 客源带看
 **/

@Entity
@Table(name = "tb_customer_take")
public class CustomerTake {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@Column(name = "tb_customer_id")
	private Long customerId;

	@Column(name = "house_need")
	@Enumerated(EnumType.STRING)
	private CustomerHouseNeed houseNeed;

    @Column(name = "name")
    private String name;

	@Column(name = "mobile")
	private String mobile;

	@ManyToOne
	@JoinColumn(name = "taker_id")
	private User taker;

	@ManyToOne
	@JoinColumn(name = "follow_id")
	private User follower;

	@Column(name = "take_time")
	private Date takeTime;
    
    @Column(name = "create_time")
    private Date createTime;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;

    
    @Column(name = "update_time")
    private Date updateTime;

	@ManyToOne
	@JoinColumn(name = "updater_id")
	private User updater;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public CustomerHouseNeed getHouseNeed() {
		return houseNeed;
	}

	public void setHouseNeed(CustomerHouseNeed houseNeed) {
		this.houseNeed = houseNeed;
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

	public User getTaker() {
		return taker;
	}

	public void setTaker(User taker) {
		this.taker = taker;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
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
}
