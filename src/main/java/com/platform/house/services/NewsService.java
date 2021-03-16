package com.platform.house.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.platform.house.domain.News;
import com.platform.house.dto.NewsDto;
import com.platform.house.form.NewsSearchForm;
import com.platform.house.repo.NewsRepo;
import com.platform.house.utils.PageVo;

@Service
@Transactional
public class NewsService {

    @Autowired
    private NewsRepo newsRepo;

    @Transactional(readOnly = true)
    public PageVo<NewsDto> search(NewsSearchForm searchForm) {
        PageRequest pageRequest = new PageRequest(searchForm.getPageRequestPage(), searchForm.getPageSize(), new Sort(Sort.Direction.DESC, "id"));
        Page<News> newsPage = newsRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            String keywords = StringUtils.trimToEmpty(searchForm.getKeywords());
            if (!Strings.isNullOrEmpty(keywords)) {
                predicates.add(cb.or(
                        cb.like(root.get("title").as(String.class), "%"+keywords+"%"),
                        cb.like(root.get("author").as(String.class), "%"+keywords+"%"),
                        cb.like(root.get("authorNick").as(String.class), "%"+keywords+"%"),
                        cb.like(root.get("content").as(String.class), "%"+keywords+"%")
                ));
            }
            if (StringUtils.isNotBlank(searchForm.getType())) {
                predicates.add(cb.equal(root.get("type").as(String.class), searchForm.getType()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);
        if (!newsPage.hasContent()) {
            return new PageVo<>(pageRequest);
        }
        List<NewsDto> dtos = newsPage.getContent().stream().map(NewsDto::new).collect(Collectors.toList());
        return new PageVo<>(dtos, pageRequest, newsPage.getTotalElements());
    }
    
}
