package com.platform.house.domain;


import java.util.Date;

import javax.persistence.*;

import com.platform.house.constant.ImageType;


/**
 * @description: 房产图片信息
 * @author: xiaohai
 */
@Entity
@Table(name = "tb_resold_house_image")
public class ResoldHouseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "resold_house_id")
    private Long resoldHouseId;

    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Column(name = "title")
    private String title;

    @Column(name = "introduction")
    private String introduction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time")
    private Date uploadTime;

    @Column(name = "sort")
    private Integer sort;

    public ResoldHouseImage() {
        this.sort = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResoldHouseId() {
        return resoldHouseId;
    }

    public void setResoldHouseId(Long resoldHouseId) {
        this.resoldHouseId = resoldHouseId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
    
    
}

