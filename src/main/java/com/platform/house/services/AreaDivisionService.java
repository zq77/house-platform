package com.platform.house.services;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.platform.house.dto.AreaDivision;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Office on 2018/6/17.
 */
@Service
public class AreaDivisionService {
    private static final Logger log = Logger.getLogger(AreaDivisionService.class);

    private static JSONArray provinces;
    private static JSONArray cities;
    private static JSONArray areas;
    private static JSONArray streets;

    static {
        String path = Objects.requireNonNull(AreaDivisionService.class.getClassLoader().getResource("")).getPath();
        try {
            File provinceFile = new File(path + "json_file/provinces.json");
            String provinceStr = FileUtils.readFileToString(provinceFile, "UTF-8");
            provinces = JSONArray.parseArray(provinceStr);

            File cityFile = new File(path + "json_file/cities.json");
            String cityStr = FileUtils.readFileToString(cityFile, "UTF-8");
            cities = JSONArray.parseArray(cityStr);

            File areaFile = new File(path + "json_file/areas.json");
            String areaStr = FileUtils.readFileToString(areaFile, "UTF-8");
            areas = JSONArray.parseArray(areaStr);

            File streetFile = new File(path + "json_file/streets.json");
            String streetStr = FileUtils.readFileToString(streetFile, "UTF-8");
            streets = JSONArray.parseArray(streetStr);
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Cacheable(cacheNames = "provinces")
    public JSONArray getProvinces() {
        return provinces;
    }

    @Cacheable(cacheNames = "cities")
    public JSONArray getCities() {
        return cities;
    }

    @Cacheable(cacheNames = "areas")
    public JSONArray getAreas() {
        return areas;
    }
    
    @Cacheable(cacheNames = "streets")
    public JSONArray getStreets() {
        return streets;
    }

    @Cacheable(cacheNames = "citiesByProvince")
    public JSONArray getCitiesByProvince(String code) {
        return getJsonArrayByParentCode(code, cities);
    }
    @Cacheable(cacheNames = "areasByCity")
    public JSONArray getAreasByCity(String code) {
        return getJsonArrayByParentCode(code, areas);
    }

    @Cacheable(cacheNames = "streetsByArea")
    public JSONArray getStreetsByArea(String code) {
        return getJsonArrayByParentCode(code, streets);
    }

    private JSONArray getJsonArrayByParentCode(String code, JSONArray originJsonArray) {
        JSONArray returnJsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for(Object object : originJsonArray) {
            jsonObject = (JSONObject) object;
            if(jsonObject.get("parent_code").equals(code)) {
                returnJsonArray.add(jsonObject);
            }
        }
        return returnJsonArray;
    }
    
    @Cacheable(cacheNames = "citiesOrderByLetter")
    public List<AreaDivision> getCitiesOrderByLetter() throws PinyinException {
    	List<AreaDivision> cityList = cities.toJavaList(AreaDivision.class);
    	for(AreaDivision areaDivistion : cityList) {
    		areaDivistion.setFirst_letters(PinyinHelper.convertToPinyinString(areaDivistion.getName(), "", PinyinFormat.WITHOUT_TONE));
    		areaDivistion.setInitial(PinyinHelper.getShortPinyin(areaDivistion.getName().substring(0, 1)).toUpperCase());
    	}
		cityList.sort(Comparator.comparing(AreaDivision::getFirst_letters));
        return cityList;
    }

    public JSONObject getCityByCode(String code) throws PinyinException {
        JSONArray cityList = getCities();
        JSONObject jsonObject = null;
        for(Object object : cityList) {
            jsonObject = (JSONObject) object;
            if(jsonObject.get("code").equals(code)) {
                String name = jsonObject.get("name").toString().replace("市", "");
                jsonObject.put("shortPY", PinyinHelper.getShortPinyin(name));
                break;
            }
        }
        return jsonObject;
    }

    public JSONObject getAreaByCityCodeAndName(String cityCode, String name) {
        JSONArray areaList = getAreasByCity(cityCode);
        JSONObject jsonObject = null;
        for(Object object : areaList) {
            jsonObject = (JSONObject) object;
            if(jsonObject.get("name").toString().contains(name)) {
                break;
            }
        }
        return jsonObject;
    }

    public static void main(String[] args) {
        try {
            JSONObject s = new AreaDivisionService().getCityByCode("1101");
            System.out.println(s);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
    }

}
