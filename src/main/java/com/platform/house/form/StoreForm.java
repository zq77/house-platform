package com.platform.house.form;


import com.platform.house.domain.Store;
import com.platform.house.security.ShiroUser;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

public class StoreForm {
    private static final Logger logger = Logger.getLogger(StoreForm.class);
    @NotBlank(message = "名称不能为空")
    private String name;
    private String contactName;
    private String contactPhone;
    private String address;
    private String openDate;
    private String content;
    private Date createTime;
    private String creator;
    private Date updateTime;
    private String updater;

    public Store toStore(ShiroUser user) {
        Store store = new Store();
        if (user != null) {
            store.setCreator(user.getUsername());
        }
        store.setCreateTime(new Date());
        return this.mergeStore(store, user);
    }

    public Store mergeStore(Store store, ShiroUser user) {
        store.setName(this.getName());
        store.setContactName(this.getContactName());
        store.setContactPhone(this.getContactPhone());
        store.setOpenDate(this.getOpenDate());
        store.setAddress(this.getAddress());
        store.setContent(this.getContent());
        store.setUpdateTime(new Date());
        if (user != null) {
            store.setUpdater(user.getUsername());
        }
        return store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
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
