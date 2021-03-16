package com.platform.house.form;

import com.platform.house.constant.CustomerHouseNeed;
import com.platform.house.constant.SysConstants;
import com.platform.house.domain.CustomerTake;
import com.platform.house.domain.CustomerTakeHouse;
import com.platform.house.domain.CustomerTakeReporter;
import com.platform.house.domain.User;
import com.platform.house.security.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Office on 2019/1/28.
 */
public class CustomerTakeForm {
    private static final Logger logger = Logger.getLogger(CustomerTakeForm.class);

    private Long customerId;

    private String houseNeed;

    private String name;

    private String mobile;

    private Long takerId;

    private Long followerId;

    private String takeTime;

    private String houseArrayStr;

    private String reporterArrayStr;

    public CustomerTake toCustomerTake(ShiroUser user) {
        CustomerTake customerTake = new CustomerTake();
        customerTake.setCreateTime(new Date());
        User creator = new User();
        creator.setId(user.getId());
        customerTake.setCreator(creator);
        return this.mergeCustomerTake(customerTake, user);
    }

    public CustomerTake mergeCustomerTake(CustomerTake customerTake, ShiroUser user) {
        customerTake.setCustomerId(this.getCustomerId());
        String houseNeed = this.getHouseNeed();
        if (StringUtils.isNotBlank(houseNeed)) {
            customerTake.setHouseNeed(CustomerHouseNeed.valueOf(houseNeed));
        }
        customerTake.setName(this.getName());
        customerTake.setMobile(this.getMobile());
        if (this.getTakerId() != null) {
            User taker = new User();
            taker.setId(this.getTakerId());
            customerTake.setTaker(taker);
        }

        if (this.getFollowerId() != null) {
            User follower = new User();
            follower.setId(this.getFollowerId());
            customerTake.setFollower(follower);
        }

        if (StringUtils.isNotBlank(this.getTakeTime())) {
            try {
                Date takeTime = DateUtils.parseDate(this.getTakeTime(), SysConstants.DATE_PARSE_PATTERNS[1]);
                customerTake.setTakeTime(takeTime);
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        User updater = new User();
        updater.setId(user.getId());
        customerTake.setUpdater(updater);
        customerTake.setUpdateTime(new Date());
        return customerTake;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getHouseNeed() {
        return houseNeed;
    }

    public void setHouseNeed(String houseNeed) {
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

    public Long getTakerId() {
        return takerId;
    }

    public void setTakerId(Long takerId) {
        this.takerId = takerId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getHouseArrayStr() {
        return houseArrayStr;
    }

    public void setHouseArrayStr(String houseArrayStr) {
        this.houseArrayStr = houseArrayStr;
    }

    public String getReporterArrayStr() {
        return reporterArrayStr;
    }

    public void setReporterArrayStr(String reporterArrayStr) {
        this.reporterArrayStr = reporterArrayStr;
    }
}
