package com.platform.house.controller;

import com.platform.house.constant.SysConstants;
import com.platform.house.domain.Staff;
import com.platform.house.domain.Store;
import com.platform.house.dto.StoreDto;
import com.platform.house.form.PageForm;
import com.platform.house.form.StoreForm;
import com.platform.house.repo.StaffRepo;
import com.platform.house.repo.StoreRepo;
import com.platform.house.security.ShiroUser;
import com.platform.house.services.StoreService;
import com.platform.house.utils.ErrorData;
import com.platform.house.utils.PageVo;
import com.platform.house.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private StaffRepo staffRepo;

    @GetMapping("/api/stores/all")
    public ResponseEntity getStoreList() {
        List<Store> storeList = storeRepo.findAll();
        return ResponseUtil.success(storeList);
    }

    @GetMapping("/api/stores")
    public ResponseEntity getAll(PageForm pageForm) {
        PageVo<StoreDto> page = storeService.search(pageForm);
        return ResponseUtil.success(page);
    }

    @PutMapping("/api/stores/{id:\\d+}")
    @RequiresRoles(value={SysConstants.ROLE_ADMIN})
    public ResponseEntity update(@PathVariable(name = "id") Long newsId,
                                 @Valid StoreForm storeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.validationError(bindingResult.getFieldErrors());
        }
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Store store = storeRepo.findOne(newsId);
        store = storeForm.mergeStore(store, user);
        storeRepo.save(store);
        return ResponseUtil.success();
    }

    @RequiresRoles(value={SysConstants.ROLE_ADMIN})
    @GetMapping("/api/stores/{id:\\d+}")
    public ResponseEntity get(@PathVariable(name = "id") Long storeId) {
        Store store = storeRepo.findOne(storeId);
        return ResponseUtil.success(new StoreDto(store));
    }

    @RequiresRoles(value={SysConstants.ROLE_ADMIN})
    @PostMapping("/api/stores")
    public ResponseEntity create(@Valid StoreForm storeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.validationError(bindingResult.getFieldErrors());
        }
        if (storeRepo.getByName(storeForm.getName()) != null) {
            return ResponseUtil.validationError(new ErrorData("name", "门店名称已存在"));
        }
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Store store = storeRepo.save(storeForm.toStore(user));
        return ResponseUtil.success(store);
    }

    @RequiresRoles(value={SysConstants.ROLE_ADMIN})
    @DeleteMapping("/api/stores/{id:\\d+}")
    public ResponseEntity delete(@PathVariable(name = "id") Long storeId) {
        List<Staff> staffList = staffRepo.findByStoreId(storeId);
        if (staffList != null && !staffList.isEmpty()) {
            return ResponseUtil.validationError(new ErrorData("errorMsg", "该门店已被使用不能删除"));
        } else {
            storeRepo.delete(storeId);
            return ResponseUtil.success();
        }

    }
}
