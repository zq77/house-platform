package com.platform.house.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Office on 2019/1/22.
 */
@Entity
@Table(name = "tb_news_ad")
public class NewsAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tb_user_id")
    private Long userId;

    @Column(name = "type")
    private String type;

    @Column(name = "template_type")
    private String templateType;

    @Column(name = "css_fixed")
    private String cssFixed;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "link")
    private String link;

    @Column(name = "text")
    private String text;

    @Column(name = "title")
    private String title;

    @Column(name = "intro")
    private String intro;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "creator")
    private String creator;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "updater")
    private String updater;

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
