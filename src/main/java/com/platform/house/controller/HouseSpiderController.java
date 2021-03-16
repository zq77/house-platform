package com.platform.house.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.platform.house.constant.HouseWebsite;
import com.platform.house.constant.ImageType;
import com.platform.house.constant.SysConstants;
import com.platform.house.domain.*;
import com.platform.house.form.*;
import com.platform.house.mapper.HouseInfoMapper;
import com.platform.house.mapper.ResoldHouseInfoMapper;
import com.platform.house.repo.*;
import com.platform.house.security.ShiroUser;
import com.platform.house.services.AreaDivisionService;
import com.platform.house.services.HouseInfoQuery;
import com.platform.house.services.ResoldHouseInfoQuery;
import com.platform.house.spider.HouseSpiderPipeline;
import com.platform.house.spider.HouseSpiderProcessor;
import com.platform.house.spider.ResoldHouseSpiderPipeline;
import com.platform.house.utils.QiNiuUpload;
import com.platform.house.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.*;

@RestController
@RequestMapping(value = "/spider")
public class HouseSpiderController {
    private static final Logger logger = Logger.getLogger(HouseSpiderController.class);

    @Autowired
    AreaDivisionService areaDivisionService;
    
    @Autowired
    private HouseInfoMapper houseInfoMapper;

    @Autowired
    private ResoldHouseInfoMapper resoldHouseInfoMapper;
    
    @Autowired
    private HouseInfoRepo houseInfoRepo;

    @Autowired
    private HouseTypeRepo houseTypeRepo;

    @Autowired
    private HouseImageRepo houseImageRepo;

    @Autowired
    private ResoldHouseRepo resoldHouseRepo;

    @Autowired
    private HouseInfoQuery houseInfoQuery;

    @Autowired
    private ResoldHouseInfoQuery resoldHouseInfoQuery;

    @Autowired
    private ResoldHouseImageRepo resoldHouseImageRepo;

    @RequiresRoles(value={SysConstants.ROLE_ADMIN})
    @GetMapping(value = "/grab/newHouse")
    public ResponseEntity grab(HouseSpiderForm houseSpiderForm) {
        try {
            HouseSpiderForm returnHouseSpiderForm = getHouseSpiderForm(houseSpiderForm);
            HouseSpiderProcessor houseSpiderProcessor = new HouseSpiderProcessor(returnHouseSpiderForm);

            Spider.create(houseSpiderProcessor).addUrl(returnHouseSpiderForm.getFirstListUrl())
                    .addPipeline(new HouseSpiderPipeline()).thread(5).run();

            saveGrabedNewHouseList();


        } catch (PinyinException e) {
            logger.error(e.getMessage());
            return ResponseUtil.internalServerError();
        }

        return ResponseUtil.success();
    }

    @RequiresRoles(value={SysConstants.ROLE_ADMIN})
    @GetMapping(value = "/grab/resoldHouse")
    public ResponseEntity grabResoldHouse(HouseSpiderForm houseSpiderForm) {
        try {
            HouseSpiderForm returnHouseSpiderForm = getHouseSpiderForm(houseSpiderForm);
            HouseSpiderProcessor houseSpiderProcessor = new HouseSpiderProcessor(returnHouseSpiderForm);

            Spider.create(houseSpiderProcessor).addUrl(returnHouseSpiderForm.getFirstListUrl())
                    .addPipeline(new ResoldHouseSpiderPipeline()).thread(5).run();

            saveGrabedResoldHouseList();

        } catch (PinyinException e) {
            logger.error(e.getMessage());
            return ResponseUtil.internalServerError();
        }

        return ResponseUtil.success();
    }

    private HouseSpiderForm getHouseSpiderForm(HouseSpiderForm houseSpiderForm) throws PinyinException {
        JSONObject selectedCity = areaDivisionService.getCityByCode(houseSpiderForm.getCityCode());
        houseSpiderForm.setSelectedCity(selectedCity);
        String cityShortPY = selectedCity.getString("shortPY");
        HouseWebsite website = HouseWebsite.getWebsiteByName(houseSpiderForm.getWebSiteName());
        houseSpiderForm.setHouseWebsite(website);
        String firstListUrl;
        String listUrlReg;
        String detailUrlReg;
        if (houseSpiderForm.getIfNewHouse()) {
            firstListUrl = website.getNewHouseListUrl().replace("$1", cityShortPY).replace("$2", "1");
            listUrlReg = website.getNewHouseListUrl().replace("$1", cityShortPY).replace("$2", "\\d+").replace(".", "\\.");
            detailUrlReg = website.getNewHouseDetailUrl().replace("$1", cityShortPY).replace(".", "\\.").replace("$2", ".+");
        } else {
            firstListUrl = website.getResoldHouseListUrl().replace("$1", cityShortPY).replace("$2", "1");
            listUrlReg = website.getResoldHouseListUrl().replace("$1", "\\w+").replace("$2", "\\d+").replace(".", "\\.");
            detailUrlReg =  website.getResoldHouseDetailUrl().replace("$1", "\\w+").replace(".", "\\.").replace("$2", ".+");
        }
        houseSpiderForm.setFirstListUrl(firstListUrl);
        houseSpiderForm.setListUrlReg(listUrlReg);
        houseSpiderForm.setDetailUrlReg(detailUrlReg);
        return houseSpiderForm;
    }

    private void saveGrabedNewHouseList() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        List<Map<String, HouseInfoForm>> houseList = HouseSpiderPipeline.houseList;
        List<Map<String, List<HouseTypeForm>>> houseTypeList = HouseSpiderPipeline.houseTypeList;
        List<Map<String, List<NormalImageFrom>>> houseImageList = HouseSpiderPipeline.houseImageList;
        Iterator keyIterator;
        String key;
        List<House> existHouseList;
        House mappedHouse;
        House house;
        HouseInfoForm houseInfoForm;
        for(Map<String, HouseInfoForm> houseMap : houseList) {
            keyIterator = houseMap.keySet().iterator();
            if (keyIterator.hasNext()) {
                key = keyIterator.next().toString();
                // 获取基本信息
                houseInfoForm = houseMap.get(key);
                // 查询并判断当前数据是否存在
                existHouseList = houseInfoQuery.findByHouseForm(houseInfoForm);
                if (existHouseList == null || existHouseList.isEmpty()) {
                    mappedHouse = houseInfoMapper.mapToEntry(houseInfoForm, user);
                    house = houseInfoRepo.saveAndFlush(mappedHouse);
                    for(Map<String, List<HouseTypeForm>> houseTypeMap : houseTypeList) {
                        // 获取对应的户型信息
                        List<HouseTypeForm> houseTypeFormList = houseTypeMap.get(key);
                        if (houseTypeFormList != null && !houseTypeFormList.isEmpty()) {
                            // 保存户型信息
                            saveGrabedHouseType(houseTypeFormList, house);
                            break;
                        }

                    }
                    for(Map<String, List<NormalImageFrom>> houseImageMap : houseImageList) {
                        // 获取对应的图片信息
                        List<NormalImageFrom> houseImageUrlList = houseImageMap.get(key);
                        if (houseImageUrlList != null && !houseImageUrlList.isEmpty()) {
                            // 保存图集信息
                            saveGrabedHouseImage(houseImageUrlList, house);
                            break;
                        }

                    }
                }
            }
        }

        HouseSpiderPipeline.houseList = new ArrayList<>();
        HouseSpiderPipeline.houseTypeList = new ArrayList<>();
        HouseSpiderPipeline.houseImageList = new ArrayList<>();
    }

    private void saveGrabedHouseType(List<HouseTypeForm> houseTypeFormList, House house) {
        List<HouseType> houseTypeList = new ArrayList<>();
        String fileKey;
        for(HouseTypeForm houseTypeForm : houseTypeFormList) {
            fileKey = QiNiuUpload.getfileKeyFromUrl(houseTypeForm.getImageUrl());
            if (StringUtils.isNotBlank(fileKey)) {
                houseTypeForm.setImageUrl(fileKey);
            } else {
                houseTypeForm.setImageUrl("");
            }
            HouseType houseType = houseInfoMapper.mapToEntry(house, houseTypeForm);
            houseTypeList.add(houseType);
        }
        houseTypeRepo.save(houseTypeList);
    }

    private void saveGrabedHouseImage(List<NormalImageFrom> houseImageUrlList, House house) {
        List<HouseImage> houseImageList = new ArrayList<>();
        String fileKey;
        for(NormalImageFrom normalImageFrom : houseImageUrlList) {
            fileKey = QiNiuUpload.getfileKeyFromUrl(normalImageFrom.getImageUrl());
            if (StringUtils.isNotBlank(fileKey)) {
                HouseImage image = new HouseImage();
                image.setHouseId(house.getId());
                image.setUrl(fileKey);
                image.setType(ImageType.valueOf(normalImageFrom.getImageType()));
                image.setUploadTime(new Date());
                houseImageList.add(image);
            }
        }
        houseImageRepo.save(houseImageList);
    }

    private void saveGrabedResoldHouseImage(List<String> houseImageUrlList, ResoldHouse house) {
        List<ResoldHouseImage> houseImageList = new ArrayList<>();
        String fileKey;
        for(String imageUrl : houseImageUrlList) {
            fileKey = QiNiuUpload.getfileKeyFromUrl(imageUrl);
            if (StringUtils.isNotBlank(fileKey)) {
                ResoldHouseImage image = new ResoldHouseImage();
                image.setResoldHouseId(house.getId());
                image.setImageUrl(fileKey);
                image.setType(ImageType.NORMAL);
                image.setUploadTime(new Date());
                houseImageList.add(image);
            }
        }
        resoldHouseImageRepo.save(houseImageList);
    }

    private void saveGrabedResoldHouseList() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        List<ResoldHouseInfoForm> houseInfoFormList = ResoldHouseSpiderPipeline.houseList;
        ResoldHouse mappedHouse = null;
        ResoldHouse house = null;
        List<ResoldHouse> existHouseList = null;
        for(ResoldHouseInfoForm houseForm : houseInfoFormList) {
            // 查询并判断当前数据是否存在
            existHouseList = resoldHouseInfoQuery.findByHouseForm(houseForm);
            if (existHouseList == null || existHouseList.isEmpty()) {
                mappedHouse = resoldHouseInfoMapper.mapToEntry(houseForm, user);
                house = resoldHouseRepo.saveAndFlush(mappedHouse);
                // 保存二手房图片信息
                saveGrabedResoldHouseImage(houseForm.getHouseImageList(), house);
            }
        }
        ResoldHouseSpiderPipeline.houseList = new ArrayList<>();
    }
}
