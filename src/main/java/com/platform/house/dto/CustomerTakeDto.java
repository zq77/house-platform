package com.platform.house.dto;

import com.platform.house.constant.CustomerHouseNeed;
import com.platform.house.constant.SysConstants;
import com.platform.house.domain.CustomerTake;
import com.platform.house.domain.User;
import com.platform.house.services.UserService;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by Office on 2019/1/28.
 */
public class CustomerTakeDto {
    private static final Logger logger = Logger.getLogger(CustomerTakeDto.class);

    private Long id;

    private Long customerId;

    private CustomerHouseNeed houseNeed;

    private String name;

    private String mobile;

    private User taker;

    private User follower;

    private Date takeTime;

    private Date createTime;

    private String createTimeFormat;

    private User creator;

    private Date updateTime;

    private User updater;

    public CustomerTakeDto(CustomerTake customerTake) {
        BeanUtils.copyProperties(customerTake, this);
        User creator = UserService.getAccessUserDetail(customerTake.getCreator());
        this.setCreator(creator);
        User updater = UserService.getAccessUserDetail(customerTake.getUpdater());
        this.setUpdater(updater);
        try {
            String createTimeFormat = DateUtils.formatDate(customerTake.getCreateTime(), SysConstants.DATE_PARSE_PATTERNS[2]);
            this.setCreateTimeFormat(createTimeFormat);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustomerHouseNeed getHouseNeed() {
        return houseNeed;
    }

    public void setHouseNeed(CustomerHouseNeed houseNeed) {
        this.houseNeed = houseNeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getTaker() {
        return taker;
    }

    public void setTaker(User taker) {
        this.taker = taker;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
    }
}
