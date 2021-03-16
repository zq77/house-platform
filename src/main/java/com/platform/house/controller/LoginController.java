package com.platform.house.controller;

import com.platform.house.constant.SysConstants;
import com.platform.house.domain.Role;
import com.platform.house.domain.User;
import com.platform.house.dto.LoginDto;
import com.platform.house.repo.UserRepo;
import com.platform.house.repo.UserRoleRepo;
import com.platform.house.utils.ErrorData;
import com.platform.house.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @PostMapping("/api/login")
    public ResponseEntity login(String username, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        //登录
        try {
            currentUser.login(new UsernamePasswordToken(username, password));
        } catch (IncorrectCredentialsException i) {
            return ResponseUtil.validationError(new ErrorData("用户名或密码错误"));
        } catch (UnknownAccountException e) {
            return ResponseUtil.validationError(new ErrorData("用户名或密码错误"));
        }
        Session session = currentUser.getSession();
        Serializable JSESSIONID = session.getId();
        session.setTimeout(1000 * 60 * 60 * 2);
        User user = userRepo.getUserByname(username);
        user.setRequestDate(new Date());
        userRepo.save(user);

        return ResponseUtil.success(getLoginInfo(user, JSESSIONID));
    }

    private LoginDto getLoginInfo(User user, Serializable JSESSIONID) {
        List<Role> roles = userRoleRepo.getRolesByUserId(user.getId());
        List<String> roleValues = roles.stream().map(Role::getValue).collect(Collectors.toList());
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(user.getUsername());
        loginDto.setjSessionId(JSESSIONID.toString());
        for(String roleValue : roleValues) {
            switch (roleValue) {
                case SysConstants.ROLE_ADMIN:
                    loginDto.setAdmin(true);
                    break;
                case SysConstants.ROLE_STORE_ADMIN:
                    loginDto.setStoreAdmin(true);
                    break;
                case SysConstants.ROLE_AGENT:
                    loginDto.setAgent(true);
                    break;
                case SysConstants.ROLE_NORMAL_USER:
                    loginDto.setNormalUser(true);
                    break;
                case SysConstants.ROLE_WECHAT_USER:
                    loginDto.setWechatUser(true);
                    break;
            }
        }
        return loginDto;
    }

    @RequiresUser
    @PostMapping("/api/logout")
    public ResponseEntity logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResponseUtil.success();
    }
}
