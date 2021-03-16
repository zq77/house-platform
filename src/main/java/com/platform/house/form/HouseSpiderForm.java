package com.platform.house.form;

import com.alibaba.fastjson.JSONObject;
import com.platform.house.constant.HouseWebsite;

/**
 * Created by Office on 2019/1/3.
 */
public class HouseSpiderForm {

    private String cityCode;
    private String webSiteName;
    private Integer startPageNum;
    private Integer endPageNum;
    private Boolean ifNewHouse;

    private JSONObject selectedCity;
    private HouseWebsite houseWebsite;
    private String firstListUrl;
    private String listUrlReg;
    private String detailUrlReg;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public Integer getStartPageNum() {
        return startPageNum;
    }

    public void setStartPageNum(Integer startPageNum) {
        this.startPageNum = startPageNum;
    }

    public Integer getEndPageNum() {
        return endPageNum;
    }

    public void setEndPageNum(Integer endPageNum) {
        this.endPageNum = endPageNum;
    }

    public Boolean getIfNewHouse() {
        return ifNewHouse;
    }

    public void setIfNewHouse(Boolean ifNewHouse) {
        this.ifNewHouse = ifNewHouse;
    }

    public JSONObject getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(JSONObject selectedCity) {
        this.selectedCity = selectedCity;
    }

    public HouseWebsite getHouseWebsite() {
        return houseWebsite;
    }

    public void setHouseWebsite(HouseWebsite houseWebsite) {
        this.houseWebsite = houseWebsite;
    }

    public String getFirstListUrl() {
        return firstListUrl;
    }

    public void setFirstListUrl(String firstListUrl) {
        this.firstListUrl = firstListUrl;
    }

    public String getListUrlReg() {
        return listUrlReg;
    }

    public void setListUrlReg(String listUrlReg) {
        this.listUrlReg = listUrlReg;
    }

    public String getDetailUrlReg() {
        return detailUrlReg;
    }

    public void setDetailUrlReg(String detailUrlReg) {
        this.detailUrlReg = detailUrlReg;
    }
}
