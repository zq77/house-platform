package com.platform.house.domain;

import com.platform.house.constant.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: CustomerFollow 客源跟进
 **/

@Entity
@Table(name = "tb_customer_follow")
public class CustomerFollow {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tb_customer_id")
    private Long customerId;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private CustomerFollowType type;

	@Column(name = "content")
	private String content;

    @Column(name = "create_time")
    private Date createTime;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;

	@Column(name = "creator_name")
	private String creatorName;

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

	public CustomerFollowType getType() {
		return type;
	}

	public void setType(CustomerFollowType type) {
		this.type = type;
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
}
