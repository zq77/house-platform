package com.platform.house.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.platform.house.constant.HouseCategory;
import com.platform.house.constant.HouseInfoStatus;
import com.platform.house.constant.ImageType;
import com.platform.house.domain.House;
import com.platform.house.domain.HouseImage;
import com.platform.house.domain.HouseType;
import com.platform.house.form.HouseInfoForm;
import com.platform.house.form.HouseTypeForm;
import com.platform.house.security.ShiroUser;
import com.platform.house.services.AreaDivisionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-08-04 13:50
 */

@Component
public class HouseInfoMapper {
    private static final Logger logger = Logger.getLogger(HouseInfoMapper.class);
    
    @Autowired
    private AreaDivisionService areaDivisionService;

    public House mapToEntry(HouseInfoForm form, ShiroUser user) {
        House house = new House();

        mergeToEntry(house, form);

        house.setUserId(user.getId());
        house.setCreateTime(new Date());

        return house;
    }

    public void mergeToEntry(House house, HouseInfoForm form) {
        if (house == null) {
            return;
        }

        house.setName(form.getName());
        house.setAveFloorCount(form.getAveFloorCount());
        house.setBuildingCount(form.getBuildingCount());
        house.setContactName(form.getContactName());
        house.setContactPhone(form.getContactPhone());
        house.setDevelopers(form.getDevelopers());
        house.setDisabled(Boolean.FALSE);
        house.setFloorage(form.getFloorage());
        house.setGreeningRate(form.getGreeningRate());
        house.setHouseholds(form.getHouseholds());
        house.setIntroduction(form.getIntroduction());
        house.setPlotRatio(form.getPlotRatio());
        house.setPrice(form.getPrice());
        house.setPropertyType(form.getPropertyType());
        house.setPropertyCompany(form.getPropertyCompany());
        house.setPropertyRightsYears(form.getPropertyRightsYears());
        house.setSellingStatus(form.getSellingStatus());
        house.setSalesDepartmentAddress(form.getSalesDepartmentAddress());
        house.setTotalArea(form.getTotalArea());
        house.setTotalPrice(form.getTotalPrice());
        house.setAddress(form.getAddress());
        house.setArea(form.getArea());
        house.setCity(form.getCity());
        house.setCountry(form.getCountry());
        house.setLocation(this.getLocation(form));
        house.setProvince(form.getProvince());
        house.setStreet(form.getStreet());
        house.setPropertyPrice(form.getPropertyPrice());
        house.setBuildYear(form.getBuildYear());
        house.setParkCount(form.getParkCount());
        if (StringUtils.isNoneBlank(form.getCategory())) {
            house.setCategory(HouseCategory.valueOf(form.getCategory()));
        } else {
            house.setCategory(HouseCategory.OTHERS);
        }
        house.setUpdateTime(new Date());
        house.setLabels(form.getLabels());

        if (house.getId() == null && StringUtils.isNotBlank(form.getStatus())) {
            house.setStatus(HouseInfoStatus.valueOf(form.getStatus()));
        } else {
            house.setStatus(HouseInfoStatus.INIT);
        }

        if (StringUtils.isNotBlank(form.getGrabSite())) {
            house.setGrabSite(form.getGrabSite());
        }
        house.setSellingDate(form.getSellingDate());
        house.setCompletionDate(form.getCompletionDate());
    }

    public HouseType mapToEntry(House house, HouseTypeForm form) {

        HouseType houseType = new HouseType();
        houseType.setHouseId(house.getId());
        houseType.setCreateTime(new Date());

        mergeToEntry(houseType, form);

        return houseType;
    }

    public void mergeToEntry(HouseType houseType, HouseTypeForm form) {
        if (houseType == null) {
            return;
        }
        houseType.setSellingStatus(form.getSellingStatus());
        houseType.setBathroomCount(form.getBathroomCount());
        houseType.setFloorage(form.getFloorage());
        houseType.setHallCount(form.getHallCount());
        houseType.setKitchenCount(form.getKitchenCount());
        houseType.setPrice(form.getPrice());
        houseType.setRoomCount(form.getRoomCount());
        houseType.setTotalPrice(form.getTotalPrice());
        houseType.setImageUrl(form.getImageUrl());
        houseType.setUpdateTime(new Date());
    }

    public HouseImage transToHouseTypeImage(HouseTypeForm houseType) {
        HouseImage image = new HouseImage();

        image.setType(ImageType.HOUSE_TYPE);
        image.setUploadTime(new Date());
        image.setUrl(houseType.getImageUrl());

        StringBuilder sb = new StringBuilder();

        if (houseType.getRoomCount() != null) {
            sb.append(houseType.getRoomCount().intValue()).append("室 ");
        }

        if (houseType.getHallCount() != null) {
            sb.append(houseType.getRoomCount().intValue()).append("厅 ");
        }

        if (houseType.getKitchenCount() != null) {
            sb.append(houseType.getKitchenCount().intValue()).append("厨 ");
        }

        if (houseType.getBathroomCount() != null) {
            sb.append(houseType.getBathroomCount().intValue()).append("卫 ");
        }

        image.setTitle(sb.toString());
        image.setIntroduction(String.valueOf(houseType.getTotalPrice()));

        return image;
    }
    
    private String getLocation(HouseInfoForm form) {
    	String province = getByCode(areaDivisionService.getProvinces(), form.getProvince());
    	String city = getByCode(areaDivisionService.getCities(), form.getCity());
    	String area = getByCode(areaDivisionService.getAreas(), form.getArea());
    	String street = getByCode(areaDivisionService.getStreets(), form.getStreet());
    	return new StringBuilder().append(province).append(city).append(area).append(street).append(form.getAddress()).toString();
    }

    private String getByCode(JSONArray array, String code) {
        if (Strings.isNullOrEmpty(code)) {
            return "";
        }
        return array.stream().map(item -> (JSONObject) item)
                .filter((item) -> code.equals(item.get("code")))
                .map(item -> item.get("name").toString()).findFirst().orElse("");
    }
}
