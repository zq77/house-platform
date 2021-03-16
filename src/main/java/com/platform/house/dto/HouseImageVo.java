package com.platform.house.dto;

import com.platform.house.constant.ImageType;
import com.platform.house.domain.HouseImage;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-08-28 23:11
 */
public class HouseImageVo extends BaseImageVo{

    private String title;

    private ImageType type;

    private String introduction;

    private String remark;

    private Date uploadTime;

    private Integer sort;

    public HouseImageVo() {
        super();
    }

    public HouseImageVo(HouseImage image) {
        super(image.getId(), image.getUrl());
        this.title = image.getTitle();
        this.type = image.getType();
        this.introduction = image.getIntroduction();
        this.remark = image.getRemark();
        this.uploadTime = image.getUploadTime();
        this.sort = image.getSort();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
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
