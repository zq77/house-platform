package com.platform.house.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.platform.house.constant.HouseCategory;
import com.platform.house.constant.HouseInfoStatus;
import com.platform.house.domain.ResoldHouse;
import com.platform.house.form.ResoldHouseInfoForm;
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
public class ResoldHouseInfoMapper {
    private static final Logger logger = Logger.getLogger(ResoldHouseInfoMapper.class);
    
    @Autowired
    private AreaDivisionService areaDivisionService;

    @Autowired
    private HouseInfoMapper houseInfoMapper;

    public ResoldHouse mapToEntry(ResoldHouseInfoForm form, ShiroUser user) {
        ResoldHouse house = new ResoldHouse();

        mergeToEntry(house, form);

        house.setUserId(user.getId());
        house.setCreateTime(new Date());

        return house;
    }

    public void mergeToEntry(ResoldHouse house, ResoldHouseInfoForm form) {
        if (house == null) {
            return;
        }
        house.setTitle(form.getTitle());
        house.setSubTitle(form.getSubTitle());
        house.setName(form.getName());
        house.setDisabled(Boolean.FALSE);
        house.setFloorage(form.getFloorage());
        house.setIntroduction(form.getIntroduction());
        house.setPrice(form.getPrice());
        house.setTotalPrice(form.getTotalPrice());
        house.setAddress(form.getAddress());
        house.setArea(form.getArea());
        house.setCity(form.getCity());
        house.setCountry(form.getCountry());
        house.setLocation(this.getLocation(form));
        house.setProvince(form.getProvince());
        house.setStreet(form.getStreet());
        if (StringUtils.isNoneBlank(form.getCategory())) {
        	house.setCategory(HouseCategory.valueOf(form.getCategory()));
        } else {
        	house.setCategory(HouseCategory.OTHERS);
        }

        house.setRoomCount(form.getRoomCount());
        house.setHallCount(form.getHallCount());
        house.setKitchenCount(form.getKitchenCount());
        house.setBathroomCount(form.getBathroomCount());
        house.setFloorage(form.getFloorage());
        house.setPrice(form.getPrice());
        house.setTotalPrice(form.getTotalPrice());
        house.setFeatures(form.getFeatures());
        house.setUpdateTime(new Date());
        
        house.setOwnerName(form.getOwnerName());
        house.setOwnerPhone(form.getOwnerPhone());
        house.setBlock(form.getBlock());
        house.setUnit(form.getUnit());
        house.setRoomNum(form.getRoomNum());
        house.setUseageArea(form.getUseageArea());
        house.setLowestPrice(form.getLowestPrice());
        house.setFloor(form.getFloor());
        house.setTotalFloor(form.getTotalFloor());
        house.setOrientation(form.getOrientation());
        house.setDecoration(form.getDecoration());
        house.setUseYear(form.getUseYear());

        house.setBuildingYear(form.getBuildingYear());
        house.setPropertyNature(form.getPropertyNature());
        house.setPropertyYear(form.getPropertyYear());
        house.setMortgageStatus(form.getMortgageStatus());
        house.setSellingStatus(form.getSellingStatus());
        house.setLabels(form.getLabels());

        if (house.getId() == null && StringUtils.isNotBlank(form.getStatus())) {
            house.setStatus(HouseInfoStatus.valueOf(form.getStatus()));
        } else {
            house.setStatus(HouseInfoStatus.INIT);
        }

        if (StringUtils.isNotBlank(form.getGrabSite())) {
            house.setGrabSite(form.getGrabSite());
        }
    }

    private String getLocation(ResoldHouseInfoForm form) {
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
