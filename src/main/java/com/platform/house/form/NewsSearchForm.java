package com.platform.house.form;

public class NewsSearchForm extends PageForm {
    private String keywords;
    private String type;
    private Long storeId;
    private String userName;
    private String displayAll;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayAll() {
        return displayAll;
    }

    public void setDisplayAll(String displayAll) {
        this.displayAll = displayAll;
    }
}
