package com.platform.house.dto;

import com.platform.house.domain.HouseNews;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by Office on 2019/3/18.
 */
public class HouseNewsDto {

    private Long id;

    private Long houseId;

    private String title;

    private String content;

    private String creator;

    private Date createTime;

    public HouseNewsDto(HouseNews houseNews) {
        BeanUtils.copyProperties(houseNews, this);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
