package com.platform.house.form;

import com.platform.house.domain.News;
import com.platform.house.security.ShiroUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class NewsForm {

	private String title;
	private String type;
	private String original;
	private String coverImage;
	private String author;
	private String authorNick;
	private String publishDate;
	private String content;
	private Long viewTimes;
	private Long likeTimes;
	private Date createTime;
	private String creator;
	private Date updateTime;
	private String updater;
	private String userId;

	public News toNews(ShiroUser user) {
		News news = new News();
		news.setCreator(user.getUsername());
		news.setCreateTime(new Date());
		return this.mergeNews(news, user);
	}

	public News mergeNews(News news, ShiroUser user) {
		news.setTitle(this.getTitle());
		if (StringUtils.isNotBlank(this.getType())) {
			news.setType(this.getType());
		} else {
			news.setType("NORMAL");
		}
		news.setOriginal(this.getOriginal());
		news.setCoverImage(this.getCoverImage());
		news.setAuthor(this.getAuthor());
		news.setAuthorNick(this.getAuthorNick());
		news.setPublishDate(this.getPublishDate());
		news.setContent(this.getContent());
		news.setViewTimes(this.getViewTimes());
		news.setLikeTimes(this.getLikeTimes());
		news.setUpdater(user.getUsername());
		news.setUpdateTime(new Date());
		return news;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
