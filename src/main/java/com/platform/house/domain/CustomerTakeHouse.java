package com.platform.house.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: CustomerTakeHouse 带看房源
 **/

@Entity
@Table(name = "tb_customer_take_house")
public class CustomerTakeHouse {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@Column(name = "tb_take_id")
	private Long takeId;

    @Column(name = "house_id")
    private Long houseId;

	@Column(name = "resold_house_id")
	private Long resoldHouseId;

	@Column(name = "house_name")
	private String houseName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTakeId() {
		return takeId;
	}

	public void setTakeId(Long takeId) {
		this.takeId = takeId;
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
}
