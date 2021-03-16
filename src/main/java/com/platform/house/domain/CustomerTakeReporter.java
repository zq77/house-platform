package com.platform.house.domain;

import javax.persistence.*;

/**
 * @description: CustomerTakeReporter 带看责任人
 **/

@Entity
@Table(name = "tb_customer_take_reporter")
public class CustomerTakeReporter {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@Column(name = "tb_take_id")
	private Long takeId;

    @Column(name = "user_id")
    private Long userId;

	@Column(name = "user_name")
	private String userName;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
