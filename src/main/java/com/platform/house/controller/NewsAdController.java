package com.platform.house.controller;

import com.alibaba.fastjson.JSON;
import com.platform.house.domain.NewsAd;
import com.platform.house.form.NewsAdForm;
import com.platform.house.repo.NewsAdRepo;
import com.platform.house.security.ShiroUser;
import com.platform.house.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Office on 2019/1/22.
 */
@RestController
public class NewsAdController {
    @Autowired
    private NewsAdRepo newsAdRepo;

    @PostMapping("/api/newsAd")
    @RequiresUser
    public ResponseEntity create(String newsAdFormArrayJson) {
        List<NewsAdForm> newsAdFormsList = JSON.parseArray(newsAdFormArrayJson, NewsAdForm.class);
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        List<NewsAd> newsAdList = new ArrayList<>();
        for(NewsAdForm newsAdForm : newsAdFormsList) {
            newsAdList.add(newsAdForm.toNewsAd(user));
        }
        NewsAd filterNewsAd = new NewsAd();
        filterNewsAd.setUserId(user.getId());
        Example<NewsAd> example = Example.of(filterNewsAd);
        List<NewsAd> myNewsAdList = newsAdRepo.findAll(example);
        newsAdRepo.delete(myNewsAdList);
        newsAdRepo.save(newsAdList);
        return ResponseUtil.success();
    }

    @GetMapping("/api/newsAd/templates")
    @RequiresUser
    public ResponseEntity search() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        NewsAd newsAd = new NewsAd();
        newsAd.setUserId(user.getId());
        Example<NewsAd> example = Example.of(newsAd);
        List<NewsAd> newsAdList = newsAdRepo.findAll(example);
        return ResponseUtil.success(newsAdList);
    }

    @GetMapping("/api/newsAd/{id:\\d+}/templates")
    public ResponseEntity getNewsAdTemplate(@PathVariable(name = "id") Long userId) {
        NewsAd newsAd = new NewsAd();
        newsAd.setUserId(userId);
        Example<NewsAd> example = Example.of(newsAd);
        List<NewsAd> newsAdList = newsAdRepo.findAll(example);
        return ResponseUtil.success(newsAdList);
    }

}
