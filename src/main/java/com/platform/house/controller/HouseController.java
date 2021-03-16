package com.platform.house.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.NotSupportedException;

import com.platform.house.repo.UserRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.platform.house.domain.House;
import com.platform.house.domain.HouseImage;
import com.platform.house.domain.HouseNews;
import com.platform.house.domain.HouseType;
import com.platform.house.dto.HouseDetailVo;
import com.platform.house.dto.HouseImageVo;
import com.platform.house.dto.HouseNewsDto;
import com.platform.house.dto.HouseTypeVo;
import com.platform.house.form.HouseInfoForm;
import com.platform.house.form.HouseNewsForm;
import com.platform.house.form.HouseSearchForm;
import com.platform.house.form.HouseTypeForm;
import com.platform.house.form.NormalImageFrom;
import com.platform.house.repo.HouseInfoNewsRepo;
import com.platform.house.repo.HouseInfoRepo;
import com.platform.house.security.ShiroUser;
import com.platform.house.services.HouseInfoCommand;
import com.platform.house.services.HouseInfoQuery;
import com.platform.house.utils.ErrorData;
import com.platform.house.utils.PageVo;
import com.platform.house.utils.ResponseUtil;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-06-26 23:40
 */
@RestController
@RequestMapping(value = "/houses")
public class HouseController {
    private static final Logger logger = Logger.getLogger(HouseController.class);

    @Autowired
    private HouseInfoQuery houseInfoQuery;

    @Autowired
    private HouseInfoCommand houseInfoCommand;

    @Autowired
    private HouseInfoRepo houseInfoRepo;

    @Autowired
    private HouseInfoNewsRepo houseInfoNewsRepo;

    @GetMapping(value = {"", "/search"})
    public ResponseEntity getAll(HouseSearchForm searchForm,
                                 @RequestParam(name = "page", required = false) Integer pageNum,
                                 @RequestParam(name = "size", required = false) Integer pageSize) {
        if (pageNum == null || pageNum < 1) { pageNum = 1; }

        if (pageSize == null) { pageSize = 10; }
        List<Long> houseIdList = houseInfoQuery.getHouseIdList();
        searchForm.setHouseIdList(houseIdList);
        PageVo<HouseDetailVo> page = houseInfoQuery.search(searchForm, pageNum, pageSize);
        return ResponseUtil.success(page);
    }

    @GetMapping(value = "/search/view")
    public ResponseEntity getViewAll(HouseSearchForm searchForm,
                                     @RequestParam(name = "page", required = false) Integer pageNum,
                                     @RequestParam(name = "size", required = false) Integer pageSize) {
        if (pageNum == null || pageNum < 1) { pageNum = 1; }
        if (pageSize == null) { pageSize = 10; }
        List<Long> houseIdList = houseInfoQuery.getHouseIdListByWechatUser(searchForm.getWechatUserId(), searchForm.getDisplayType());
        searchForm.setHouseIdList(houseIdList);
        PageVo<HouseDetailVo> page = houseInfoQuery.search(searchForm, pageNum, pageSize);
        return ResponseUtil.success(page);
    }


    @GetMapping(value = "/featured")
    public ResponseEntity getFeaturedHouse(HouseSearchForm searchForm, @RequestParam(name = "size", required = false) Integer pageSize) {
        if (pageSize == null) {
            pageSize = 5;
        }
        PageVo<HouseDetailVo> page = houseInfoQuery.getFeaturedHouse(searchForm, pageSize);
        return ResponseUtil.success(page);
    }

    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity getDetail(@PathVariable(name = "id") Long houseId) {
        HouseDetailVo details = null;

        try {
            details = houseInfoQuery.getDetails(houseId);
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
    public ResponseEntity createHouseInfo(HouseInfoForm houseInfoForm) {
        List<House> result = houseInfoQuery.findByName(houseInfoForm.getName());
        if (result != null && !result.isEmpty()) {
            return ResponseUtil.validationError(new ErrorData("name", "名称已经存在"));
        }

        House newHouse = houseInfoCommand.createNewHouse(houseInfoForm);
        return ResponseUtil.creationSuccess(newHouse);
    }

    @PutMapping(value = "/{id:\\d+}")
    @RequiresUser
    public ResponseEntity update(@PathVariable(name = "id") Long houseId,
                                 HouseInfoForm houseInfoForm) {

//        List<House> result = houseInfoQuery.findByHouseForm(houseInfoForm);
//        if (result != null && !result.isEmpty()) {
//            if (result.size() > 1) {
//                return ResponseUtil.validationError(new ErrorData("name", "名称已经存在"));
//            }
//        }

        House house = houseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        House newHouse;
        try {
            newHouse = houseInfoCommand.updateHouse(house, houseInfoForm);
        } catch (NotSupportedException e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.creationSuccess(newHouse);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @RequiresRoles(value={SysConstants.ROLE_ADMIN}, logical= Logical.OR)
    public ResponseEntity delete(@PathVariable(name = "id") Long houseId) {
        House house = houseInfoRepo.findOne(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }
        house.setDisabled(true);
        houseInfoRepo.save(house);
        return ResponseUtil.success();
    }
    
    @PutMapping(value = "/set-featured")
    @RequiresRoles(value={SysConstants.ROLE_ADMIN}, logical= Logical.OR)
    public ResponseEntity batchSetFeatured(@RequestParam(name="houseIds", required = false) String houseIds) {
    	if(StringUtils.isNotBlank(houseIds)) {
    		String[] houseIdArray = houseIds.split(",");
    		houseInfoCommand.batchUpdateHouse(houseIdArray, true);
    	} 
    	return ResponseUtil.success(); 
    }
    
    @PutMapping(value = "/unset-featured")
    @RequiresRoles(value={SysConstants.ROLE_ADMIN}, logical= Logical.OR)
    public ResponseEntity batchUnsetFeatured(@RequestParam(name="houseIds", required = false) String houseIds) {
    	if(StringUtils.isNotBlank(houseIds)) {
    		String[] houseIdArray = houseIds.split(",");
    		houseInfoCommand.batchUpdateHouse(houseIdArray, false);
    	} 
    	return ResponseUtil.success(); 
    }

    @PostMapping(value = "/{id:\\d+}/housetype")
    @RequiresUser
    public ResponseEntity addHouseType(@PathVariable(name = "id") Long houseId,
                                       HouseTypeForm houseTypeForm) {

        House house = houseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        HouseType houseType;
        try {
            houseType = houseInfoCommand.addHouseType(house, houseTypeForm);
        } catch (NotSupportedException e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.creationSuccess(new HouseTypeVo(houseType));
    }

    @PutMapping(value = "/{id:\\d+}/housetype/{typeId:\\d+}")
    @RequiresUser
    public ResponseEntity updateHouseType(@PathVariable(name = "id") Long houseId,
                                          @PathVariable(name = "typeId") Long houseTypeId,
                                          HouseTypeForm houseTypeForm) {

        House house = houseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        HouseType houseType;
        try {
            houseType = houseInfoCommand.updateHouseType(house, houseTypeId, houseTypeForm);
        } catch (NotSupportedException e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.success(new HouseTypeVo(houseType));
    }

    @DeleteMapping(value = "/{id:\\d+}/housetype/{typeId:\\d+}")
    @RequiresUser
    public ResponseEntity deleteHouseType(@PathVariable(name = "id") Long houseId,
                                          @PathVariable(name = "typeId") Long houseTypeId) {
        House house = houseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        try {
            houseInfoCommand.deleteHouseType(house, houseTypeId);
        } catch (NotSupportedException e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.success();
    }

    @PostMapping(value = "/{id:\\d+}/images")
    @RequiresUser
    public ResponseEntity addHouseImage(@PathVariable(name = "id") Long houseId,
                                        NormalImageFrom imageFrom) {

        House house = houseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        HouseImage houseImage;
        try {
            houseImage = houseInfoCommand.addNormalImage(houseId, imageFrom);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtil.forbiddenError();
        }

        return ResponseUtil.creationSuccess(new HouseImageVo(houseImage));
    }

    @DeleteMapping(value = "/{id:\\d+}/images/{imageId:\\d+}")
    @RequiresUser
    public ResponseEntity addHouseImage(@PathVariable(name = "id") Long houseId,
                                        @PathVariable(name = "imageId") Long imageId) {
        House house = houseInfoQuery.findById(houseId);
        if (house == null) {
            return ResponseUtil.notFoundError();
        }

        try {
            houseInfoCommand.deleteImageById(imageId);
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
        houseInfoCommand.sortNormalImage(houseId, imageId, index);
        return ResponseUtil.success();
    }

    @GetMapping(value = "/{id:\\d+}/news")
    public ResponseEntity getHouseNews(@PathVariable(name = "id") Long houseId) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        HouseNews houseNews = new HouseNews();
        houseNews.setHouseId(houseId);
        Example<HouseNews> example = Example.of(houseNews);
        List<HouseNews> houseNewsList = houseInfoNewsRepo.findAll(example);
        List<HouseNewsDto> houseNewsDtoList = houseNewsList.stream().map(HouseNewsDto::new).sorted(Comparator.comparing(HouseNewsDto::getId).reversed()).collect(Collectors.toList());
        return ResponseUtil.success(houseNewsDtoList);
    }

    @PostMapping(value = "/{id:\\d+}/news")
    @RequiresUser
    public ResponseEntity addHouseNews(@PathVariable(name = "id") Long houseId, HouseNewsForm houseNewsForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        houseNewsForm.setHouseId(houseId);
        HouseNews houseNews = houseNewsForm.toHouseNes(user);
        houseInfoNewsRepo.save(houseNews);
        return ResponseUtil.success();
    }

    @DeleteMapping(value = "/news/{id:\\d+}")
    @RequiresUser
    public ResponseEntity delHouseNews(@PathVariable(name = "id") Long id) {
        houseInfoNewsRepo.delete(id);
        return ResponseUtil.success();
    }
}
