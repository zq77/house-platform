package com.platform.house.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: News 新闻资讯
 **/

@Entity
@Table(name = "tb_news")
public class News {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

	@Column(name = "original")
	private String original;

	@Column(name = "type")
	private String type;
    
    @Column(name = "cover_image")
    private String coverImage;
    
    @Column(name = "author")
    private String author;

	@Column(name = "author_nick")
	private String authorNick;
    
    @Column(name = "publish_date")
    private String publishDate;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "view_times")
    private Long viewTimes;
    
    @Column(name = "like_times")
    private Long likeTimes;
    
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorNick() {
		return authorNick;
	}

	public void setAuthorNick(String authorNick) {
		this.authorNick = authorNick;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(Long viewTimes) {
		this.viewTimes = viewTimes;
	}

	public Long getLikeTimes() {
		return likeTimes;
	}

	public void setLikeTimes(Long likeTimes) {
		this.likeTimes = likeTimes;
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
