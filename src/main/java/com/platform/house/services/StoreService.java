package com.platform.house.services;

import com.google.common.base.Strings;
import com.platform.house.domain.Store;
import com.platform.house.dto.StoreDto;
import com.platform.house.form.PageForm;
import com.platform.house.repo.StoreRepo;
import com.platform.house.utils.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Office on 2019/2/17.
 */
@Service
public class StoreService {

    @Autowired
    private StoreRepo storeRepo;

    @Transactional(readOnly = true)
    public PageVo<StoreDto> search(PageForm pageForm) {
        PageRequest pageRequest = new PageRequest(pageForm.getPageRequestPage(), pageForm.getPageSize(), new Sort(Sort.Direction.DESC, "id"));
        Page<Store> storePage = storeRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            String keywords = StringUtils.trimToEmpty(pageForm.getKeywords());
            if (!Strings.isNullOrEmpty(keywords)) {
                predicates.add(cb.or(
                        cb.like(root.get("name").as(String.class), "%"+keywords+"%")
                ));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);
        if (!storePage.hasContent()) {
            return new PageVo<>(pageRequest);
        }
        List<StoreDto> dtos = storePage.getContent().stream().map(StoreDto::new).collect(Collectors.toList());
        return new PageVo<>(dtos, pageRequest, storePage.getTotalElements());
    }
}
