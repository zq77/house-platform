package com.platform.house.controller;

import com.platform.house.constant.SysConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.platform.house.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @GetMapping(value = "/login")
    public String login() {
        return "login.jsp";
    }

    @GetMapping(value = {"/index", "/"})
    public String index() {
    	Subject currentUser = SecurityUtils.getSubject();
    	boolean[] hasRoleArray = currentUser.hasRoles(Arrays.asList(SysConstants.ROLE_ADMIN, SysConstants.ROLE_STORE_ADMIN, SysConstants.ROLE_AGENT));
    	boolean hasRole = false;
    	for (boolean hasRoleItem : hasRoleArray) {
			if (hasRoleItem) {
				hasRole = true;
				break;
			}
    	}
    	if (hasRole) {
    		return "index.jsp";
    	} else {
    		return "login.jsp";
    	}
    }

    @GetMapping(value = "/api/isLogin")
    public ResponseEntity isLogedIn() {
        Subject currentUser = SecurityUtils.getSubject();
        boolean[] hasRoleArray = currentUser.hasRoles(Arrays.asList(SysConstants.ROLE_ADMIN, SysConstants.ROLE_STORE_ADMIN, SysConstants.ROLE_AGENT));
        boolean hasRole = false;
        for (boolean hasRoleItem : hasRoleArray) {
            if (hasRoleItem) {
                hasRole = true;
                break;
            }
        }
        return ResponseUtil.success(hasRole);
    }

    @GetMapping(value = "/tiles")
    public ModelAndView tiles() {
        ModelAndView mv = new ModelAndView("base_template");
        return mv;
    }

    @GetMapping(value = "/testpage")
    public String test() {
        return "convertPassengerToVo.jsp";
    }
}
