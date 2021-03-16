package com.platform.house.controller;

import com.platform.house.constant.RoleType;
import com.platform.house.domain.*;
import com.platform.house.form.StoreForm;
import com.platform.house.form.UserForm;
import com.platform.house.repo.*;
import com.platform.house.security.ShiroUser;
import com.platform.house.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Office on 2019/2/24.
 */

@RestController
public class RegisterController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private StoreRepo storeRepo;

    /**
     * 经纪人注册
     * */
    @PostMapping("/api/user/register")
    public ResponseEntity register(UserForm userForm) {
        // 用户相关
        User user = userForm.toUser();
        User existUser = userRepo.getUserByname(user.getUsername());
        if (existUser != null) {
            return ResponseUtil.badRequestError("该手机号已被注册！");
        }
        userRepo.save(user);
        // 角色相关
        Role role = roleRepo.getByRoleType(RoleType.AGENT.getRoleValue());
        UserRole userRole = new UserRole(user, role);
        userRoleRepo.save(userRole);
        // 职员相关
        Staff staff = new Staff();
        staff.setEnabled(true);
        staff.setUser(user);
        staff.setRole(role);
        staffRepo.save(staff);
        return  ResponseUtil.success();
    }

    /**
     * 門店管理员注册
     * */
    @PostMapping("/api/store/user/register")
    public ResponseEntity registerStore(UserForm userForm) {
        // 用户相关
        User user = userForm.toUser();
        User existUser = userRepo.getUserByname(user.getUsername());
        if (existUser != null) {
            return ResponseUtil.badRequestError("该手机号已被注册！");
        }
        user = userRepo.save(user);
        // 角色相关
        Role role = roleRepo.getByRoleType(RoleType.STORE_ADMIN.getRoleValue());
        UserRole userRole = new UserRole(user, role);
        userRoleRepo.save(userRole);
        // 门店相关
        ShiroUser shiroUser = new ShiroUser(user);
        StoreForm storeForm = new StoreForm();
        storeForm.setName(userForm.getStoreName());
        storeForm.setContactPhone(userForm.getPhone());
        storeForm.setContactName(userForm.getRealName());
        Store store = storeForm.toStore(shiroUser);
        store = storeRepo.save(store);

        // 职员相关
        Staff staff = new Staff();
        staff.setEnabled(true);
        staff.setUser(user);
        staff.setRole(role);
        staff.setStore(store);
        staffRepo.save(staff);
        return  ResponseUtil.success();
    }
}
