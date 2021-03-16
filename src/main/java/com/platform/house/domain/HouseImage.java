package com.platform.house.domain;


import com.platform.house.constant.ImageType;

import javax.persistence.*;
import java.util.Date;


/**
 * @description: 房产图片信息
 * @author: xiaohai
 */
@Entity
@Table(name = "tb_house_image")
public class HouseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "house_id")
    private Long houseId;

    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "remark")
    private String remark;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time")
    private Date uploadTime;

    @Column(name = "sort")
    private Integer sort;

    public HouseImage() {
        this.sort = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
