package com.platform.house.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.platform.house.dto.AreaDivision;
import com.platform.house.services.AreaDivisionService;
import com.platform.house.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AreaDivisionController {

    @Autowired
    private AreaDivisionService areaDivisionService;
    
    @GetMapping(value = "/provinces")
    public ResponseEntity getProvinces() {
		JSONArray jsonArray = areaDivisionService.getProvinces();
		return ResponseUtil.success(jsonArray);
    }

    @GetMapping(value = "/cities")
    public ResponseEntity getCities() {
		JSONArray jsonArray = areaDivisionService.getCities();
		return ResponseUtil.success(jsonArray);
    }
    
    @GetMapping(value = "/citiesByLetter")
    public ResponseEntity getCitiesByLetter() throws PinyinException {
		List<AreaDivision> cityList = areaDivisionService.getCitiesOrderByLetter();
		return ResponseUtil.success(cityList);
    }

    @GetMapping(value = "/areas")
    public ResponseEntity getAreas() {
		JSONArray jsonArray = areaDivisionService.getAreas();
		return ResponseUtil.success(jsonArray);
    }

    @GetMapping(value = "/streets")
    public ResponseEntity getStreets() {
		JSONArray jsonArray = areaDivisionService.getStreets();
		return ResponseUtil.success(jsonArray);
    }

    @GetMapping(value = "/cities/{code}")
    public ResponseEntity getCitiesByProvince(@PathVariable String code) {
		JSONArray jsonArray = areaDivisionService.getCitiesByProvince(code);
		return ResponseUtil.success(jsonArray);
    }

    @GetMapping(value = "/areas/{code}")
    public ResponseEntity getAreasByCity(@PathVariable String code) {
		JSONArray jsonArray = areaDivisionService.getAreasByCity(code);
		return ResponseUtil.success(jsonArray);
    }

    @GetMapping(value = "/streets/{code}")
    public ResponseEntity getStreetsByArea(@PathVariable String code) {
		JSONArray jsonArray = areaDivisionService.getStreetsByArea(code);
		return ResponseUtil.success(jsonArray);
    }

}
