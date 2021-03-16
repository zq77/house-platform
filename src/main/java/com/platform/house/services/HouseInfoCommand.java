package com.platform.house.services;

import com.platform.house.constant.ImageType;
import com.platform.house.domain.*;
import com.platform.house.form.HouseInfoForm;
import com.platform.house.form.HouseTypeForm;
import com.platform.house.form.NormalImageFrom;
import com.platform.house.mapper.HouseInfoMapper;
import com.platform.house.repo.*;
import com.platform.house.security.ShiroUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-08-04 13:45
 */
@Service
@Transactional
public class HouseInfoCommand {

    private static final Logger logger = Logger.getLogger(HouseInfoCommand.class);

    @Autowired
    private HouseInfoMapper houseInfoMapper;

    @Autowired
    private HouseInfoRepo houseInfoRepo;

    @Autowired
    private HouseTypeRepo houseTypeRepo;

    @Autowired
    private HouseImageRepo houseImageRepo;

    @Autowired
    private HouseUserRepo houseUserRepo;

    @Autowired
    private UserRepo userRepo;

    public House createNewHouse(HouseInfoForm form) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

        House entry = houseInfoMapper.mapToEntry(form, user);
        entry.setCreateTime(new Date());

        House createdHouse = houseInfoRepo.saveAndFlush(entry);
        User agent = userRepo.findOne(user.getId());
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(createdHouse.getId());
        houseUser.setUser(agent);
        houseUserRepo.save(houseUser);
        return createdHouse;
    }

    public House updateHouse(House house, HouseInfoForm form) throws NotSupportedException {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

//        if (!Objects.equals(house.getUserId(), user.getId())) {
//            throw new NotSupportedException("用户信息不一致");
//        }

        houseInfoMapper.mergeToEntry(house, form);
        if (form.getTransfer()) {
            User agent = userRepo.findOne(user.getId());
            HouseUser houseUser = new HouseUser();
            houseUser.setHouseId(house.getId());
            houseUser.setUser(agent);
            Example<HouseUser> example = Example.of(houseUser);
            List<HouseUser> houseUsers = houseUserRepo.findAll(example);
            if (houseUsers == null || houseUsers.isEmpty()) {
                houseUserRepo.save(houseUser);
            }
        }
        return houseInfoRepo.saveAndFlush(house);
    }

    public void deleteHouseType(House house, Long houseTypeId) throws NotSupportedException {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

//        if (!Objects.equals(house.getUserId(), user.getId())) {
//            throw new NotSupportedException("用户信息不一致");
//        }

        HouseType houseType = houseTypeRepo.findOne(houseTypeId);
        if (houseType == null) {
            return;
        }

        logger.info("用户: " + user.getId() + " 删除户型:" + houseTypeId);
        houseTypeRepo.delete(houseType);

    }

    public HouseType addHouseType(House house, HouseTypeForm houseTypeForm) throws NotSupportedException {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

//        if (!Objects.equals(house.getUserId(), user.getId())) {
//            throw new NotSupportedException("用户信息不一致");
//        }

        HouseType houseType = houseInfoMapper.mapToEntry(house, houseTypeForm);

        return houseTypeRepo.saveAndFlush(houseType);
    }

    public HouseType updateHouseType(House house, Long houseTypeId, HouseTypeForm houseTypeForm) throws NotSupportedException {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

//        if (!Objects.equals(house.getUserId(), user.getId())) {
//            throw new NotSupportedException("用户信息不一致");
//        }

        HouseType houseType = houseTypeRepo.findOne(houseTypeId);

        houseInfoMapper.mergeToEntry(houseType, houseTypeForm);

        return houseTypeRepo.saveAndFlush(houseType);
    }

    public HouseImage addNormalImage(Long houseId, NormalImageFrom normalImageFrom) {
        HouseImage image = new HouseImage();
        image.setHouseId(houseId);
        image.setUrl(normalImageFrom.getImageUrl());
        String imageType = normalImageFrom.getImageType();
        if (StringUtils.isNotBlank(imageType)) {
            image.setType(ImageType.valueOf(imageType));
        } else {
            image.setType(ImageType.XGT);
        }

        image.setUploadTime(new Date());
        image.setSort(houseImageRepo.findNormalImagesByHouseId(houseId).size());
        return houseImageRepo.saveAndFlush(image);
    }

    public void deleteImageById(Long imageId) {
        HouseImage houseImage = houseImageRepo.findOne(imageId);
        if (houseImage != null) {
            houseImageRepo.delete(houseImage);
        }
    }

    public void sortNormalImage(Long houseId, Long imageId, Integer index) {
        List<HouseImage> images = houseImageRepo.findNormalImagesByHouseId(houseId);
        Optional<HouseImage> imageOptional = images.stream().filter(i -> i.getId().equals(imageId)).findFirst();
        if (CollectionUtils.isEmpty(images) || !imageOptional.isPresent()) {
            return;
        }

        images.removeIf(image -> image.getId().equals(imageId));
        images.add(index, imageOptional.get());

        for (int i = 0; i < images.size(); i ++) {
            HouseImage image = images.get(i);
            image.setSort(i);
            houseImageRepo.save(image);
        }
    }
    
    public void batchUpdateHouse(String[] houseIds, Boolean isFeatured) {
    	if(ArrayUtils.isEmpty(houseIds)) {
    		return;
    	}
    	List<Long> ids = new ArrayList<Long>();
    	for(int i = 0; i < houseIds.length; i++) {
    		ids.add(Long.valueOf(houseIds[i]));
    	}
        List<House> houseList = houseInfoRepo.findAll(ids);
        houseList.forEach(house -> {
        	house.setIsFeatured(isFeatured);
        });
    	houseInfoRepo.save(houseList);
    }
}
