package com.platform.house.services;

import com.google.common.base.Strings;
import com.platform.house.domain.Role;
import com.platform.house.domain.Staff;
import com.platform.house.domain.Store;
import com.platform.house.domain.User;
import com.platform.house.domain.UserRole;
import com.platform.house.dto.StaffDto;
import com.platform.house.form.StaffSearchForm;
import com.platform.house.repo.StaffRepo;
import com.platform.house.repo.UserRepo;
import com.platform.house.repo.UserRoleRepo;
import com.platform.house.utils.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StaffService {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Transactional(readOnly = true)
    public PageVo<StaffDto> search(StaffSearchForm searchForm) {
        PageRequest pageRequest = new PageRequest(searchForm.getPageRequestPage(), searchForm.getPageSize(), new Sort(Sort.Direction.DESC, "id"));
        Page<Staff> staffPage = staffRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Object, Object> store = root.join("store", JoinType.LEFT);
            Join<Object, Object> user = root.join("user", JoinType.INNER);
            root.fetch("store", JoinType.LEFT);
            root.fetch("user", JoinType.INNER);
            root.fetch("role", JoinType.INNER);
            String keywords = StringUtils.trimToEmpty(searchForm.getKeywords());
            if (!Strings.isNullOrEmpty(keywords)) {
                predicates.add(cb.or(
                        cb.like(user.get("username").as(String.class), "%"+keywords+"%"),
                        cb.like(user.get("realname").as(String.class), "%"+keywords+"%")
                ));
            }
            if (searchForm.getStoreId() != null) {
                predicates.add(cb.equal(store.get("id"), searchForm.getStoreId()));
            }
            if (searchForm.getComputedStoreId() != null) {
                predicates.add(cb.equal(store.get("id"), searchForm.getComputedStoreId()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);
        if (!staffPage.hasContent()) {
            return new PageVo<>(pageRequest);
        }
        List<StaffDto> dtos = staffPage.getContent().stream().map(StaffDto::new).collect(Collectors.toList());
        return new PageVo<>(dtos, pageRequest, staffPage.getTotalElements());
    }

    public StaffDto update(User user, Role role, Staff staff, Store store) {
        userRepo.save(user);
        staff.setStore(store);
        staff.setRole(role);
        staff.setUser(user);

        List<Role> roles = userRoleRepo.getRolesByUserId(staff.getUser().getId());
        if (roles.stream().noneMatch(r -> role.getId() == r.getId())) {
            UserRole userRole = new UserRole(staff.getUser(), role);
            userRoleRepo.save(userRole);
        }
        staffRepo.save(staff);
        return new StaffDto(staff);
    }
}
