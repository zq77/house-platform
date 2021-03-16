package com.platform.house.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.platform.house.domain.User;
import com.platform.house.dto.UserDto;
import com.platform.house.form.UserForm;
import com.platform.house.repo.UserRepo;
import com.platform.house.security.ShiroUser;
import com.platform.house.utils.ResponseUtil;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;
    
    @GetMapping(value = "/news-webview/{userId:\\d+}/{id:\\d+}")
    public ModelAndView newsWebview(@PathVariable(name = "userId") Long userId, @PathVariable(name = "id") Long newsId) {
        ModelAndView mav = new ModelAndView("news_webview.jsp");
        User user = userRepo.getOne(userId);
        mav.addObject("userId", userId);
        mav.addObject("username", user.getUsername());
        mav.addObject("newsId", newsId);
        return mav;
    }

    @RequiresUser
    @GetMapping("/api/current-user")
    public ResponseEntity getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        User user = userRepo.getOne(shiroUser.getId());
        UserDto userDto = new UserDto(user);
        userDto.setRoles(shiroUser.getRoles());
        return ResponseUtil.success(userDto);
    }

    @RequiresUser
    @GetMapping("/api/myId")
    public ResponseEntity getUserId() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        User user = userRepo.getUserByname(shiroUser.getUsername());
        return ResponseUtil.success(user.getId());
    }
    
    @RequiresUser
    @PutMapping("/api/update-user")
    public ResponseEntity updateUserInfo(UserForm userForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        User user = userRepo.getOne(shiroUser.getId());
        User updateUser = userForm.mergeUpdatedUser(user);
        userRepo.save(updateUser);
        return ResponseUtil.success();
    }
    
    @RequiresUser
    @PutMapping("/api/update-pwd")
    public ResponseEntity updatePwd(UserForm userForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
        User user = userRepo.getOne(shiroUser.getId());
        String password = userForm.getPassword();
        if(!user.getPassword().equals(user.generagePassword(password))) {
        	return ResponseUtil.badRequestError("旧密码不正确");
        }
        user.setPassword(user.generagePassword(userForm.getNewPassword()));
        userRepo.save(user);
        return ResponseUtil.success();
    }

}
