package com.platform.house.form;

import com.platform.house.domain.NewsAd;
import com.platform.house.security.ShiroUser;

import java.util.Date;

/**
 * Created by Office on 2019/1/22.
 */
public class NewsAdForm {
    private Long id;

    private Long userId;

    private String type;

    private String templateType;

    private String cssFixed;

    private String imageUrl;

    private String link;

    private String text;

    private String title;

    private String intro;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updater;

    public NewsAd toNewsAd(ShiroUser user) {
        NewsAd newsAd = new NewsAd();
        newsAd.setCreator(user.getUsername());
        newsAd.setCreateTime(new Date());
        return this.mergeNewsAd(newsAd, user);
    }

    public NewsAd mergeNewsAd(NewsAd newsAd, ShiroUser user) {
        newsAd.setId(this.getId());
        newsAd.setTitle(this.getTitle());
        newsAd.setType(this.getType());
        newsAd.setTemplateType(this.getTemplateType());
        newsAd.setCssFixed(this.getCssFixed());
        newsAd.setIntro(this.getIntro());
        newsAd.setLink(this.getLink());
        newsAd.setText(this.getText());
        newsAd.setImageUrl(this.getImageUrl());
        newsAd.setUserId(user.getId());
        newsAd.setUpdater(user.getUsername());
        newsAd.setUpdateTime(new Date());
        return newsAd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getCssFixed() {
        return cssFixed;
    }

    public void setCssFixed(String cssFixed) {
        this.cssFixed = cssFixed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
