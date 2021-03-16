package com.platform.house.controller;

import com.platform.house.constant.DisplayType;
import com.platform.house.domain.MycenterHouse;
import com.platform.house.domain.MycenterHouseResold;
import com.platform.house.repo.MycenterHouseRepo;
import com.platform.house.repo.MycenterHouseResoldRepo;
import com.platform.house.utils.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Office on 2019/3/23.
 */
@RestController
@RequestMapping(value = "/my-center")
public class MycenterHouseController {

    private static final Logger log = Logger.getLogger(MycenterHouseController.class);

    @Autowired
    private MycenterHouseRepo mycenterHouseRepo;

    @Autowired
    private MycenterHouseResoldRepo mycenterHouseResoldRepo;

    @PostMapping(value = "/house/like/{id:\\d+}/{userId:\\d+}")
    public ResponseEntity saveLikeHouse(@PathVariable(name = "id") Long houseId, @PathVariable(name = "userId") Long userId) {
        MycenterHouse mycenterHouse = this.getMycenterHouse(houseId, userId, DisplayType.SC);
        mycenterHouse.setCreateTime(new Date());
        mycenterHouseRepo.save(mycenterHouse);
        return ResponseUtil.success();
    }

    @GetMapping(value = "/house/{id:\\d+}/{userId:\\d+}")
    public ResponseEntity getLikeHouse(@PathVariable(name = "id") Long houseId, @PathVariable(name = "userId") Long userId) {
        MycenterHouse mycenterHouse = this.getMycenterHouse(houseId, userId, DisplayType.SC);
        Example<MycenterHouse> example = Example.of(mycenterHouse);
        MycenterHouse getMycenterHouse = mycenterHouseRepo.findOne(example);
        return ResponseUtil.success(getMycenterHouse);
    }

    @DeleteMapping(value = "/house/unlike/{id:\\d+}")
    public ResponseEntity unLikeHouse(@PathVariable(name = "id") Long id) {
        mycenterHouseRepo.delete(id);
        return ResponseUtil.success();
    }

    @PostMapping(value = "/house/browse/{id:\\d+}/{userId:\\d+}")
    public ResponseEntity saveBrowseHouse(@PathVariable(name = "id") Long houseId, @PathVariable(name = "userId") Long userId) {
        MycenterHouse mycenterHouse = this.getMycenterHouse(houseId, userId, DisplayType.LL);
        Example<MycenterHouse> example = Example.of(mycenterHouse);
        MycenterHouse getMycenterHouse = mycenterHouseRepo.findOne(example);
        if (getMycenterHouse == null) {
            mycenterHouse.setCreateTime(new Date());
            mycenterHouseRepo.save(mycenterHouse);
        }
        return ResponseUtil.success();
    }

    @PostMapping(value = "/resold/like/{id:\\d+}/{userId:\\d+}")
    public ResponseEntity saveLikeResoldHouse(@PathVariable(name = "id") Long houseId, @PathVariable(name = "userId") Long userId) {
        MycenterHouseResold mycenterHouse = this.getMycenterResoldHouse(houseId, userId, DisplayType.SC);
        mycenterHouse.setCreateTime(new Date());
        mycenterHouseResoldRepo.save(mycenterHouse);
        return ResponseUtil.success();
    }

    @GetMapping(value = "/resold/{id:\\d+}/{userId:\\d+}")
    public ResponseEntity getLikeResodHouse(@PathVariable(name = "id") Long houseId, @PathVariable(name = "userId") Long userId) {
        MycenterHouseResold mycenterHouse = this.getMycenterResoldHouse(houseId, userId, DisplayType.SC);
        Example<MycenterHouseResold> example = Example.of(mycenterHouse);
        MycenterHouseResold getMycenterHouse = mycenterHouseResoldRepo.findOne(example);
        return ResponseUtil.success(getMycenterHouse);
    }

    @DeleteMapping(value = "/resold/unlike/{id:\\d+}")
    public ResponseEntity unLikeResoldHouse(@PathVariable(name = "id") Long id) {
        mycenterHouseResoldRepo.delete(id);
        return ResponseUtil.success();
    }

    @PostMapping(value = "/resold/browse/{id:\\d+}/{userId:\\d+}")
    public ResponseEntity saveBrowseResoldHouse(@PathVariable(name = "id") Long houseId, @PathVariable(name = "userId") Long userId) {
        MycenterHouseResold mycenterHouse = this.getMycenterResoldHouse(houseId, userId, DisplayType.LL);
        Example<MycenterHouseResold> example = Example.of(mycenterHouse);
        MycenterHouseResold getMycenterHouse = mycenterHouseResoldRepo.findOne(example);
        if (getMycenterHouse == null) {
            mycenterHouse.setCreateTime(new Date());
            mycenterHouseResoldRepo.save(mycenterHouse);
        }
        return ResponseUtil.success();
    }

    private MycenterHouse getMycenterHouse(Long houseId, Long userId, DisplayType displayType) {
        MycenterHouse mycenterHouse = new MycenterHouse();
        mycenterHouse.setHouseId(houseId);
        mycenterHouse.setUserId(userId);
        mycenterHouse.setDisplayType(displayType);
        return mycenterHouse;
    }

    private MycenterHouseResold getMycenterResoldHouse(Long houseId, Long userId, DisplayType displayType) {
        MycenterHouseResold mycenterHouse = new MycenterHouseResold();
        mycenterHouse.setHouseId(houseId);
        mycenterHouse.setUserId(userId);
        mycenterHouse.setDisplayType(displayType);
        return mycenterHouse;
    }
}
