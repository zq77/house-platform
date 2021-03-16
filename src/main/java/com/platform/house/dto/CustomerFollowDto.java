package com.platform.house.dto;

import com.platform.house.constant.CustomerFollowType;
import com.platform.house.constant.SysConstants;
import com.platform.house.domain.CustomerFollow;
import com.platform.house.domain.User;
import com.platform.house.services.UserService;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by Office on 2019/1/28.
 */
public class CustomerFollowDto {
    private static final Logger logger = Logger.getLogger(CustomerFollowDto.class);

    private Long id;

    private Long customerId;

    private CustomerFollowType type;

    private String content;

    private Date createTime;

    private String createTimeFormat;

    private User creator;

    private String creatorName;

    public CustomerFollowDto(CustomerFollow customerFollow) {
        BeanUtils.copyProperties(customerFollow, this);
        User creator = UserService.getAccessUserDetail(customerFollow.getCreator());
        try {
            String createTimeFormat = DateUtils.formatDate(customerFollow.getCreateTime(), SysConstants.DATE_PARSE_PATTERNS[2]);
            this.setCreateTimeFormat(createTimeFormat);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        this.setCreator(creator);
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

    public CustomerFollowType getType() {
        return type;
    }

    public void setType(CustomerFollowType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
