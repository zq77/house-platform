package com.platform.house.form;

public class StaffSearchForm extends PageForm {
    private String keywords;
    private Long storeId;
    private Long computedStoreId;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

	public Long getComputedStoreId() {
		return computedStoreId;
	}

	public void setComputedStoreId(Long computedStoreId) {
		this.computedStoreId = computedStoreId;
	}
    
    
}
