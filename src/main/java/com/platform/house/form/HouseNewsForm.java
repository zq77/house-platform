package com.platform.house.form;

import com.platform.house.domain.HouseNews;
import com.platform.house.security.ShiroUser;

import java.util.Date;

/**
 * Created by Office on 2019/3/18.
 */
public class HouseNewsForm {

    private Long houseId;

    private String title;

    private String content;

    public HouseNews toHouseNes(ShiroUser user) {
        HouseNews houseNews = new HouseNews();
        houseNews.setCreator(user.getUsername());
        houseNews.setCreateTime(new Date());
        return this.mergeHouseNews(houseNews, user);
    }

    public HouseNews mergeHouseNews(HouseNews houseNews, ShiroUser user) {
        houseNews.setHouseId(this.getHouseId());
        houseNews.setTitle(this.getTitle());
        houseNews.setContent(this.getContent());
        return houseNews;
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
}
