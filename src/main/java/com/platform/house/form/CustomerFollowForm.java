package com.platform.house.form;

import com.platform.house.constant.CustomerFollowType;
import com.platform.house.domain.CustomerFollow;
import com.platform.house.domain.User;
import com.platform.house.security.ShiroUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by Office on 2019/1/28.
 */
public class CustomerFollowForm {

    private Long customerId;

    private String level;

    private String type;

    private String content;

    public CustomerFollow toCustomerFollow(ShiroUser user) {
        CustomerFollow customerFollow = new CustomerFollow();
        customerFollow.setCreateTime(new Date());
        User creator = new User();
        creator.setId(user.getId());
        customerFollow.setCreator(creator);
        return this.mergeCustomerFollow(customerFollow, user);
    }

    public CustomerFollow mergeCustomerFollow(CustomerFollow customerFollow, ShiroUser user) {
        customerFollow.setCustomerId(this.getCustomerId());

        String type = this.getType();
        if (StringUtils.isNotBlank(type)) {
            customerFollow.setType(CustomerFollowType.valueOf(type));
        }
        customerFollow.setContent(this.getContent());
        return customerFollow;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
