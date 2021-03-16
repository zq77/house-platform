package com.platform.house.services;

import com.platform.house.constant.ImageType;
import com.platform.house.domain.ResoldHouse;
import com.platform.house.domain.ResoldHouseImage;
import com.platform.house.domain.ResoldHouseUser;
import com.platform.house.form.ResoldHouseInfoForm;
import com.platform.house.mapper.ResoldHouseInfoMapper;
import com.platform.house.repo.ResoldHouseImageRepo;
import com.platform.house.repo.ResoldHouseRepo;
import com.platform.house.repo.ResoldHouseUserRepo;
import com.platform.house.security.ShiroUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;
import java.util.*;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-09-09 15:02
 */
@Service
@Transactional
public class ResoldHouseInfoCommand {

    @Autowired
    private ResoldHouseRepo resoldHouseRepo;

    @Autowired
    private ResoldHouseInfoMapper resoldHouseInfoMapper;

    @Autowired
    private ResoldHouseImageRepo resoldHouseImageRepo;

    @Autowired
    private ResoldHouseUserRepo resoldHouseUserRepo;

    public ResoldHouse createNewHouse(ResoldHouseInfoForm houseInfoForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

        ResoldHouse resoldHouse = resoldHouseInfoMapper.mapToEntry(houseInfoForm, user);
        ResoldHouse returnResoldHouse = resoldHouseRepo.save(resoldHouse);

        ResoldHouseUser resoldHouseUser = new ResoldHouseUser();
        resoldHouseUser.setResoldHouseId(returnResoldHouse.getId());
        resoldHouseUser.setUserId(user.getId());
        resoldHouseUserRepo.save(resoldHouseUser);
        return returnResoldHouse;
    }

    public ResoldHouse updateHouse(ResoldHouse resoldHouse, ResoldHouseInfoForm houseInfoForm) throws NotSupportedException {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();

//        if (!Objects.equals(user.getId(), resoldHouse.getUserId())) {
//            throw new NotSupportedException("用户信息不一致");
//        }

        resoldHouseInfoMapper.mergeToEntry(resoldHouse, houseInfoForm);

        if (houseInfoForm.getTransfer()) {
            ResoldHouseUser resoldHouseUser = new ResoldHouseUser();
            resoldHouseUser.setResoldHouseId(resoldHouse.getId());
            resoldHouseUser.setUserId(user.getId());
            Example<ResoldHouseUser> example = Example.of(resoldHouseUser);
            List<ResoldHouseUser> resoldHouseUsers = resoldHouseUserRepo.findAll(example);
            if (resoldHouseUsers == null || resoldHouseUsers.isEmpty()) {
                resoldHouseUserRepo.save(resoldHouseUser);
            }
        }

        return resoldHouseRepo.saveAndFlush(resoldHouse);
    }

    public ResoldHouseImage addImage(Long houseId, String imageUrl) {
        ResoldHouseImage image = new ResoldHouseImage();
        image.setResoldHouseId(houseId);
        image.setImageUrl(imageUrl);
        image.setType(ImageType.NORMAL);
        image.setUploadTime(new Date());
        image.setSort(resoldHouseImageRepo.findByResoldHouseId(houseId).size());
        return resoldHouseImageRepo.saveAndFlush(image);
    }

    public void deleteImageById(Long imageId) {
        ResoldHouseImage image = resoldHouseImageRepo.findOne(imageId);
        if (image != null) {
            resoldHouseImageRepo.delete(image);
        }
    }
    
    public void sortNormalResoldImage(Long houseId, Long imageId, Integer index) {
        List<ResoldHouseImage> images = resoldHouseImageRepo.findByResoldHouseId(houseId);
        Optional<ResoldHouseImage> imageOptional = images.stream().filter(i -> i.getId().equals(imageId)).findFirst();
        if (CollectionUtils.isEmpty(images) || !imageOptional.isPresent()) {
            return;
        }

        images.removeIf(image -> image.getId().equals(imageId));
        images.add(index, imageOptional.get());

        ResoldHouseImage image = null;
        for (int i = 0; i < images.size(); i ++) {
        	image = images.get(i);
            image.setSort(i);
            resoldHouseImageRepo.save(image);
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
        List<ResoldHouse> houseList = resoldHouseRepo.findAll(ids);
        houseList.forEach(house -> {
        	house.setIsFeatured(isFeatured);
        });
        resoldHouseRepo.save(houseList);
    }
}
