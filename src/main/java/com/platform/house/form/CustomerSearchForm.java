package com.platform.house.form;

import java.util.List;

public class CustomerSearchForm extends PageForm {
    private String keywords;
    private String customerSearchType;
    private String needFollow;
    private String thisMonthTake;
    private String abLevel;
    private List<Long> createIdList;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCustomerSearchType() {
        return customerSearchType;
    }

    public void setCustomerSearchType(String customerSearchType) {
        this.customerSearchType = customerSearchType;
    }

    public String getNeedFollow() {
        return needFollow;
    }

    public void setNeedFollow(String needFollow) {
        this.needFollow = needFollow;
    }

    public String getThisMonthTake() {
        return thisMonthTake;
    }

    public void setThisMonthTake(String thisMonthTake) {
        this.thisMonthTake = thisMonthTake;
    }

    public String getAbLevel() {
        return abLevel;
    }

    public void setAbLevel(String abLevel) {
        this.abLevel = abLevel;
    }

    public List<Long> getCreateIdList() {
        return createIdList;
    }

    public void setCreateIdList(List<Long> createIdList) {
        this.createIdList = createIdList;
    }
}
