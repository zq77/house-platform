package com.platform.house.controller;

import com.platform.house.constant.SysConstants;
import com.platform.house.domain.Role;
import com.platform.house.domain.Staff;
import com.platform.house.domain.Store;
import com.platform.house.domain.User;
import com.platform.house.dto.StaffDto;
import com.platform.house.form.StaffForm;
import com.platform.house.form.StaffSearchForm;
import com.platform.house.repo.RoleRepo;
import com.platform.house.repo.StaffRepo;
import com.platform.house.repo.StoreRepo;
import com.platform.house.repo.UserRepo;
import com.platform.house.security.ShiroUser;
import com.platform.house.services.StaffService;
import com.platform.house.utils.ErrorData;
import com.platform.house.utils.PageVo;
import com.platform.house.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
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
public class StaffController {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private StaffService staffService;

    @GetMapping("/api/staffs")
    @RequiresRoles(value={SysConstants.ROLE_ADMIN, SysConstants.ROLE_STORE_ADMIN}, logical = Logical.OR)
    public ResponseEntity search(StaffSearchForm searchForm) {
    	searchForm.setComputedStoreId(this.getStoreId());
        PageVo<StaffDto> page = staffService.search(searchForm);
        return ResponseUtil.success(page);
    }
    
    public Long getStoreId() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Boolean isAdmin = currentUser.hasRole(SysConstants.ROLE_ADMIN);
        Boolean isStoreAdmin = currentUser.hasRole(SysConstants.ROLE_STORE_ADMIN);
        // Boolean isAgent = currentUser.hasRole(SysConstants.ROLE_AGENT);
        Long storeId = 0L;
        if (isAdmin) {
            return null;
        } else if (isStoreAdmin) {
            List<Staff> staffList = staffRepo.findByUserId(user.getId());
            if (staffList != null && !staffList.isEmpty()) {
                Store store = staffList.get(0).getStore();
                storeId = store.getId();
            }
        }
        return storeId;
    }

    public Store getStore(StaffForm staffForm) {
    	Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        Boolean isAdmin = currentUser.hasRole(SysConstants.ROLE_ADMIN);
        Boolean isStoreAdmin = currentUser.hasRole(SysConstants.ROLE_STORE_ADMIN);
        Store store = null;
        if (isAdmin) {
        	store = storeRepo.getOne(staffForm.getStoreId());
        } else if (isStoreAdmin) {
            List<Staff> staffList = staffRepo.findByUserId(shiroUser.getId());
            if (staffList != null && !staffList.isEmpty()) {
                store = staffList.get(0).getStore();
            }
        }
        return store;
    }
    
    @RequiresRoles(value={SysConstants.ROLE_ADMIN, SysConstants.ROLE_STORE_ADMIN}, logical= Logical.OR)
    @PostMapping("/api/staffs")
    public ResponseEntity create(@Valid StaffForm staffForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.validationError(bindingResult.getFieldErrors());
        }
        Store store = this.getStore(staffForm);
        if (store == null) {
            return ResponseUtil.validationError(new ErrorData("storeId", "门店不存在"));
        }
        Role role = roleRepo.getByRoleType(staffForm.getRole().getRoleValue());
        if (role == null) {
            return ResponseUtil.validationError(new ErrorData("role", "角色不能为空"));
        }
        User user = userRepo.getUserByname(staffForm.getPhone());
        if (user != null) {
            Staff staff = staffRepo.findByUserIdAndStoreId(user.getId(), store.getId());
            if (staff != null) {
                return ResponseUtil.validationError(new ErrorData("phone", "手机号已在该门店下注册过"));
            }
            user = staffForm.mergeUser(user);
        } else {
            user = staffForm.toUser();
        }
        StaffDto staffDto = staffService.update(user, role, staffForm.toStaff(), store);
        return ResponseUtil.success(staffDto);
    }

    @RequiresRoles(value={SysConstants.ROLE_ADMIN, SysConstants.ROLE_STORE_ADMIN}, logical= Logical.OR)
    @PutMapping("/api/staffs/{id:\\d+}")
    public ResponseEntity update(@PathVariable(name = "id") Long staffId,
                                 @Valid StaffForm staffForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.validationError(bindingResult.getFieldErrors());
        }
        Store store = this.getStore(staffForm);
        if (store == null) {
            return ResponseUtil.validationError(new ErrorData("storeId", "门店不存在"));
        }
        Role role = roleRepo.getByRoleType(staffForm.getRole().getRoleValue());
        if (role == null) {
            return ResponseUtil.validationError(new ErrorData("role", "角色不能为空"));
        }
        Staff staff = staffRepo.getOne(staffId);
        if (staff == null) {
            return ResponseUtil.notFoundError();
        }
        User user = userRepo.getUserByname(staffForm.getPhone());
        if (user != null) {
            Staff existStaff = staffRepo.findByUserIdAndStoreId(user.getId(), store.getId());
            if (existStaff != null && !existStaff.getId().equals(staffId)) {
                return ResponseUtil.validationError(new ErrorData("phone", "手机号已在该门店下注册过"));
            }
            user = staffForm.mergeUser(user);
        } else {
            user = staffForm.toUser();
        }
        StaffDto staffDto = staffService.update(user, role, staffForm.merge(staff), store);
        return ResponseUtil.success(staffDto);
    }

    @RequiresRoles(value={SysConstants.ROLE_ADMIN, SysConstants.ROLE_STORE_ADMIN}, logical= Logical.OR)
    @DeleteMapping("/api/staffs/{id:\\d+}")
    public ResponseEntity delete(@PathVariable(name = "id") Long staffId) {
        staffRepo.delete(staffId);
        return ResponseUtil.success();
    }

    @GetMapping("/api/staffs/{id:\\d+}")
    @RequiresUser
    public ResponseEntity getDetail(@PathVariable(name = "id") Long staffId) {
        Staff staff = staffRepo.getOne(staffId);
        if (staff == null) {
            return ResponseUtil.notFoundError();
        }
        return ResponseUtil.success(new StaffDto(staff));
    }

}
