package com.platform.house.controller;

import java.util.List;

import javax.transaction.NotSupportedException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.house.constant.SysConstants;
import com.platform.house.domain.ResoldHouse;
import com.platform.house.domain.ResoldHouseImage;
import com.platform.house.dto.BaseImageVo;
import com.platform.house.dto.ResoldHouseVo;
import com.platform.house.form.HouseSearchForm;
import com.platform.house.form.NormalImageFrom;
import com.platform.house.form.ResoldHouseInfoForm;
import com.platform.house.repo.ResoldHouseRepo;
import com.platform.house.services.ResoldHouseInfoCommand;
import com.platform.house.services.ResoldHouseInfoQuery;
import com.platform.house.utils.ErrorData;
import com.platform.house.utils.PageVo;
import com.platform.house.utils.ResponseUtil;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-07-15 12:51
 */
@RestController
@RequestMapping(value = "/resold")
public class ResoldHouseController {

    private static final Logger logger = Logger.getLogger(HouseController.class);

    @Autowired
    private ResoldHouseInfoQuery resoldHouseInfoQuery;

    @Autowired
    private ResoldHouseInfoCommand resoldHouseInfoCommand;

    @Autowired
    private ResoldHouseRepo resoldHouseRepo;

    @GetMapping(value = {"", "/search"})
    public ResponseEntity getAll(HouseSearchForm searchForm,
                                 @RequestParam(name = "page", required = false) Integer pageNum,
                                 @RequestParam(name = "size", required = false) Integer pageSize) {
        if (pageNum == null) { pageNum = 1; }
        if (pageSize == null) { pageSize = 10; }
        List<Long> houseIdList = resoldHouseInfoQuery.getResoldHouseIdList();
        searchForm.setHouseIdList(houseIdList);
        PageVo<ResoldHouseVo> page = resoldHouseInfoQuery.search(searchForm, pageNum, pageSize);
        return ResponseUtil.success(page);
    }

    @GetMapping(value = "/search/view")
    public ResponseEntity getViewAll(HouseSearchForm searchForm,
                                 @RequestParam(name = "page", required = false) Integer pageNum,
                                 @RequestParam(name = "size", required = false) Integer pageSize) {
        if (pageNum == null) { pageNum = 1; }
        if (pageSize == null) { pageSize = 10; }
        List<Long> houseIdList = resoldHouseInfoQuery.getResoldHouseIdListByWechatUser(searchForm.getWechatUserId(), searchForm.getDisplayType());
        searchForm.setHouseIdList(houseIdList);
        PageVo<ResoldHouseVo> page = resoldHouseInfoQuery.search(searchForm, pageNum, pageSize);
        return ResponseUtil.success(page);
    }

    @GetMapping(value = "/featured")
    public ResponseEntity getFeaturedHouse(HouseSearchForm searchForm, @RequestParam(name = "size", required = false) Integer pageSize) {
        if (pageSize == null) {
            pageSize = 5;
        }
        PageVo<ResoldHouseVo> page = resoldHouseInfoQuery.getFeaturedHouse(searchForm, pageSize);
        return ResponseUtil.success(page);
    }

    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity getDetail(@PathVariable(name = "id") Long houseId) {
        ResoldHouseVo details = null;

        try {
            details = resoldHouseInfoQuery.getDetails(houseId);
        } catch (Exception e) {
            logger.error(e);
        }

        if (details == null) {
            return ResponseUtil.notFoundError();
        }

        return ResponseUtil.success(details);
    }

    @PostMapping(value = "")
    @RequiresUser
    public ResponseEntity createResoldHouseInfo(ResoldHouseInfoForm houseInfoForm) {
        List<ResoldHouse> result = resoldHouseInfoQuery.findByName(houseInfoForm.getName());
        if (result != null && !result.isEmpty()) {
            return ResponseUtil.validationError(new ErrorData("name", "名称已经存在"));
        }

        ResoldHouse newHouse = resoldHouseInfoCommand.createNewHouse(houseInfoForm);
        return ResponseUtil.creationSuccess(newHouse);
    }

    @PutMapping(value = "/{id:\\d+}")
    @RequiresUser
    public ResponseEntity update(@PathVariable(name = "id") Long houseId,
                                 ResoldHouseInfoForm houseInfoForm) {
//        List<ResoldHouse> result = resoldHouseInfoQuery.findByName(houseInfoForm.getName());
//        if (result != null && !result.isEmpty()) {
//            if (result.size() > 1) {
//                return ResponseUtil.validationError(new ErrorData("name", "名称已经存在"));
//            }
//
//            ResoldHouse house = result.get(0);
//            if (!house.getId().equals(houseId)) {
//                return ResponseUtil.validationError(new ErrorData("name", "名称已经存在"));
//            }
//        }

        ResoldHouse house = resoldHouseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        ResoldHouse newHouse;
        try {
            newHouse = resoldHouseInfoCommand.updateHouse(house, houseInfoForm);
        } catch (NotSupportedException e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.creationSuccess(newHouse);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @RequiresRoles(value={SysConstants.ROLE_ADMIN}, logical= Logical.OR)
    public ResponseEntity delete(@PathVariable(name = "id") Long houseId) {
        ResoldHouse house = resoldHouseRepo.findOne(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }
        house.setDisabled(true);
        resoldHouseRepo.save(house);
        return ResponseUtil.success();
    }
    
    @PutMapping(value = "/set-featured")
    public ResponseEntity batchSetFeatured(@RequestParam(name="houseIds", required = false) String houseIds) {
    	if(StringUtils.isNotBlank(houseIds)) {
    		String[] houseIdArray = houseIds.split(",");
    		resoldHouseInfoCommand.batchUpdateHouse(houseIdArray, true);
    	} 
    	return ResponseUtil.success(); 
    }
    
    @PutMapping(value = "/unset-featured")
    public ResponseEntity batchUnsetFeatured(@RequestParam(name="houseIds", required = false) String houseIds) {
    	if(StringUtils.isNotBlank(houseIds)) {
    		String[] houseIdArray = houseIds.split(",");
    		resoldHouseInfoCommand.batchUpdateHouse(houseIdArray, false);
    	} 
    	return ResponseUtil.success(); 
    }


    @PostMapping(value = "/{id:\\d+}/images")
    @RequiresUser
    public ResponseEntity addHouseImage(@PathVariable(name = "id") Long houseId,
                                        NormalImageFrom imageFrom) {

        ResoldHouse house = resoldHouseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        ResoldHouseImage image;
        try {
            image = resoldHouseInfoCommand.addImage(houseId, imageFrom.getImageUrl());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.creationSuccess(new BaseImageVo(image.getId(), image.getImageUrl()));
    }

    @DeleteMapping(value = "/{id:\\d+}/images/{imageId:\\d+}")
    @RequiresUser
    public ResponseEntity delHouseImage(@PathVariable(name = "id") Long houseId,
                                        @PathVariable(name = "imageId") Long imageId) {
        ResoldHouse house = resoldHouseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        try {
            resoldHouseInfoCommand.deleteImageById(imageId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.success();
    }

    @PostMapping(value = "/{id:\\d+}/images/{imageId:\\d+}/sort")
    @RequiresUser
    public ResponseEntity sortHouseImage(@PathVariable(name = "id") Long houseId,
                                         @PathVariable(name = "imageId") Long imageId,
                                         @Param("index") Integer index) {

        if (index == null || index < 0) {
            ResponseUtil.validationError();
        }
        resoldHouseInfoCommand.sortNormalResoldImage(houseId, imageId, index);
        return ResponseUtil.success();
    }


}
