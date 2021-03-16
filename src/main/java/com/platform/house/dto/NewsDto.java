package com.platform.house.dto;

import com.platform.house.domain.News;
import com.platform.house.utils.Util;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class NewsDto {

	private Long id;
	private String type;
	private String original;
	private String title;
	private String coverImage;
	private String author;
	private String authorNick;
	private String publishDate;
	private String intro;
	private String content;
	private Long viewTimes;
	private Long likeTimes;
	private Date createTime;
	private String creator;
	private Date updateTime;
	private String updater;

	public NewsDto(News news) {
		BeanUtils.copyProperties(news, this);
		this.setContent("");
	}

	public NewsDto(News news, Boolean showContent) {
		BeanUtils.copyProperties(news, this);
		if(!showContent) {
			this.setContent("");
		}
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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
