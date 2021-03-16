package com.platform.house.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.platform.house.domain.User;
import com.platform.house.repo.UserRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.platform.house.constant.SysConstants;
import com.platform.house.domain.News;
import com.platform.house.domain.NewsAd;
import com.platform.house.dto.NewsDto;
import com.platform.house.form.NewsForm;
import com.platform.house.form.NewsSearchForm;
import com.platform.house.repo.NewsAdRepo;
import com.platform.house.repo.NewsRepo;
import com.platform.house.security.ShiroUser;
import com.platform.house.services.NewsService;
import com.platform.house.spider.NewsSpiderPipeline;
import com.platform.house.spider.NewsSpiderProcessor;
import com.platform.house.utils.PageVo;
import com.platform.house.utils.QiNiuUpload;
import com.platform.house.utils.ResponseUtil;

import us.codecraft.webmagic.Spider;

@RestController
public class NewsController {

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsAdRepo newsAdRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/api/news")
    public ResponseEntity search(NewsSearchForm searchForm) {
        PageVo<NewsDto> page = newsService.search(searchForm);
        return ResponseUtil.success(page);
    }

    @PostMapping("/api/news")
    public ResponseEntity create(@Valid NewsForm newsForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.validationError(bindingResult.getFieldErrors());
        }
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        if (user == null) {
            User getUser = userRepo.getOne(Long.valueOf(newsForm.getUserId()));
            user = new ShiroUser(getUser);
        }
        News news = newsForm.toNews(user);
        newsRepo.save(news);
        return ResponseUtil.success();
    }

    @PutMapping("/api/news/{id:\\d+}")
    public ResponseEntity update(@PathVariable(name = "id") Long newsId,
                                 @Valid NewsForm newsForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.validationError(bindingResult.getFieldErrors());
        }
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        if (user == null) {
            User getUser = userRepo.getOne(Long.valueOf(newsForm.getUserId()));
            user = new ShiroUser(getUser);
        }
        Boolean isAdmin = currentUser.hasRole(SysConstants.ROLE_ADMIN);
        News news = newsRepo.findOne(newsId);
        // 不能修改非自己创建的资讯
        if (!isAdmin && !user.getUsername().equals(news.getCreator())) {
        	return ResponseUtil.badRequestError("抱歉，您没有权限修改此文章");
        }
        news = newsForm.mergeNews(news, user);
        newsRepo.save(news);
        return ResponseUtil.success();
    }

    @PutMapping("/api/news/view/{id:\\d+}")
    public ResponseEntity updateViewTimes(@PathVariable(name = "id") Long newsId) {
        News news = newsRepo.findOne(newsId);
        Long viewTimes = news.getViewTimes();
        if (viewTimes == null) {
            viewTimes = 0L;
        }
        news.setViewTimes(viewTimes + 1);
        newsRepo.save(news);
        return ResponseUtil.success();
    }

    @PutMapping("/api/news/like/{id:\\d+}")
    public ResponseEntity updateLikeTimes(@PathVariable(name = "id") Long newsId) {
        News news = newsRepo.findOne(newsId);
        Long likeTimes = news.getLikeTimes();
        if (likeTimes == null) {
            likeTimes = 0L;
        }
        news.setLikeTimes(likeTimes + 1);
        newsRepo.save(news);
        return ResponseUtil.success();
    }

    @DeleteMapping("/api/news/{id:\\d+}")
    @RequiresUser
    public ResponseEntity delete(@PathVariable(name = "id") Long newsId) {
    	Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Boolean isAdmin = currentUser.hasRole(SysConstants.ROLE_ADMIN);
        News news = newsRepo.findOne(newsId);
    	// 不能删除非自己创建的资讯(除总管理员)
        if (!isAdmin && !user.getUsername().equals(news.getCreator())) {
        	return ResponseUtil.badRequestError("抱歉，您没有权限删除此文章");
        }
        newsRepo.delete(newsId);
        return ResponseUtil.success();
    }

    @GetMapping("/api/news/{id:\\d+}")
    public ResponseEntity getDetail(@PathVariable(name = "id") Long newsId) {
        News news = newsRepo.getOne(newsId);
        if (news == null) {
            return ResponseUtil.notFoundError();
        }
        return ResponseUtil.success(new NewsDto(news, true));
    }

    @GetMapping("/share/{userId:\\d+}/news/{id:\\d+}")
    public ModelAndView getShareNews(@PathVariable(name = "id") Long newsId, @PathVariable(name = "userId") Long userId) {
        ModelAndView mav = new ModelAndView("news_share.jsp");
        News news = newsRepo.getOne(newsId);
        mav.addObject("news", news);
        if (news == null) {
            return new ModelAndView("404.jsp");
        }
        NewsAd newsAd = new NewsAd();
        newsAd.setUserId(userId);
        Example<NewsAd> example = Example.of(newsAd);
        List<NewsAd> newsAdList = newsAdRepo.findAll(example);
        List<NewsAd> topNewsAdList = newsAdList.stream().filter(item -> "TOP".equals(item.getType())).collect(Collectors.toList());
        List<NewsAd> bottomNewsAdList = newsAdList.stream().filter(item -> "BOTTOM".equals(item.getType())).collect(Collectors.toList());
        mav.addObject("topNewsAdList", topNewsAdList);
        mav.addObject("bottomNewsAdList", bottomNewsAdList);
        if (bottomNewsAdList != null && !bottomNewsAdList.isEmpty()) {
            NewsAd bottomNewsAd = bottomNewsAdList.get(0);
            if (bottomNewsAd != null) {
                mav.addObject("cssFixed", bottomNewsAd.getCssFixed());
            }
        }

        mav.addObject("qiNiuImgUrl", QiNiuUpload.QINIU_WEBSITE);
        return mav;
    }

    @GetMapping("/view/news/{id:\\d+}")
    public ModelAndView getDetailView(@PathVariable(name = "id") Long newsId) {
        ModelAndView mav = new ModelAndView("news.jsp");
        News news = newsRepo.getOne(newsId);
        mav.addObject("news", news);
        if (news == null) {
            return new ModelAndView("404.jsp");
        }
        return mav;
    }

    @PostMapping("/api/news/grab")
    @RequiresUser
    public ResponseEntity grabNews(String grabUrl) {
        StringBuilder grabUrlBuilder = new StringBuilder(grabUrl);
        NewsSpiderProcessor newsSpiderProcessor = new NewsSpiderProcessor();
        NewsSpiderPipeline newsSpiderPipeline = new NewsSpiderPipeline();
        Spider.create(newsSpiderProcessor).addUrl(grabUrlBuilder.toString())
                .addPipeline(newsSpiderPipeline).thread(1).run();
        NewsForm newsForm = newsSpiderPipeline.getNewsForm();
        newsForm.setType("GRAB");
        newsForm.setViewTimes(0L);
        newsForm.setLikeTimes(0L);
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        News news = newsForm.toNews(user);
        if (StringUtils.isNotBlank(newsForm.getCoverImage())) {
            String fileKey = QiNiuUpload.getfileKeyFromUrl(newsForm.getCoverImage().trim());
            news.setCoverImage(fileKey);
        }
        News savedNews = newsRepo.saveAndFlush(news);
        return ResponseUtil.success(new NewsDto(savedNews, true));
    }

}
