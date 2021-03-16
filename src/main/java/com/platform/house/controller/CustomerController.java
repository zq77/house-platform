package com.platform.house.controller;

import com.alibaba.fastjson.JSON;
import com.platform.house.constant.CustomerFollowType;
import com.platform.house.constant.CustomerLevel;
import com.platform.house.constant.CustomerStatus;
import com.platform.house.constant.SysConstants;
import com.platform.house.domain.*;
import com.platform.house.dto.CustomerDto;
import com.platform.house.form.CustomerFollowForm;
import com.platform.house.form.CustomerForm;
import com.platform.house.form.CustomerSearchForm;
import com.platform.house.form.CustomerTakeForm;
import com.platform.house.repo.*;
import com.platform.house.security.ShiroUser;
import com.platform.house.services.CustomerService;
import com.platform.house.utils.PageVo;
import com.platform.house.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Office on 2019/1/28.
 */
@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerTakeRepo customerTakeRepo;

    @Autowired
    private CustomerTakeHouseRepo customerTakeHouseRepo;

    @Autowired
    private CustomerTakeReporterRepo customerTakeReporterRepo;

    @Autowired
    private CustomerFollowRepo customerFollowRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StaffRepo staffRepo;

    @GetMapping("/search")
    @RequiresUser
    public ResponseEntity search(CustomerSearchForm searchForm) {
        searchForm.setCreateIdList(this.getCreateIds());
        PageVo<CustomerDto> page = customerService.search(searchForm);
        return ResponseUtil.success(page);
    }

    public List<Long> getCreateIds() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Boolean isAdmin = currentUser.hasRole(SysConstants.ROLE_ADMIN);
        Boolean isStoreAdmin = currentUser.hasRole(SysConstants.ROLE_STORE_ADMIN);
        // Boolean isAgent = currentUser.hasRole(SysConstants.ROLE_AGENT);
        List<Long> createIdList = new ArrayList<>();
        if (isAdmin) {
            return null;
        } else if (isStoreAdmin) {
            List<Staff> staffList = staffRepo.findByUserId(user.getId());
            if (staffList != null && !staffList.isEmpty()) {
                Store store = staffList.get(0).getStore();
                List<Staff> staffsInStore = staffRepo.findByStoreId(store.getId());
                createIdList = staffsInStore.stream().map(Staff::getUser).map(User::getId).distinct().collect(Collectors.toList());
            } else {
                createIdList.add(user.getId());
            }
        } else {
            createIdList.add(user.getId());
        }
        return createIdList;
    }

    @PostMapping("/create")
    @RequiresUser
    public ResponseEntity create(CustomerForm customerForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Customer customer = customerForm.toCustomer(user);
        customer = customerRepo.save(customer);
        // 客源动态-客户报备
        addCustomerFollow(user, customer.getId(), "客户报备", CustomerFollowType.AUTO);
        return ResponseUtil.success();
    }

    @GetMapping("/detail/{id:\\d+}")
    @RequiresUser
    public ResponseEntity getDetail(@PathVariable(name = "id") Long customerId) {
        Customer customer = customerRepo.getOne(customerId);
        CustomerDto customerDto = customerService.mappingToCustomerDto(customer);
        return ResponseUtil.success(customerDto);
    }

    @PutMapping("/edit/{id:\\d+}")
    @RequiresUser
    public ResponseEntity update(@PathVariable(name = "id") Long customerId, CustomerForm customerForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Customer customer = customerRepo.getOne(customerId);
        customer = customerForm.mergeCustomer(customer, user);
        customerRepo.save(customer);
        return ResponseUtil.success();
    }

    @PutMapping("/edit/{id:\\d+}/creator/{userId:\\d+}")
    @RequiresUser
    public ResponseEntity updateCreator(@PathVariable(name = "id") Long customerId, @PathVariable(name = "userId") Long userId) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Customer customer = customerRepo.getOne(customerId);
        User creator = new User();
        creator.setId(userId);
        customer.setCreator(creator);
        customerRepo.save(customer);
        // 客户动态-修改客源
        addCustomerFollow(user, customer.getId(), "客源修改", CustomerFollowType.AUTO);
        return ResponseUtil.success();
    }

    @PutMapping("/edit/{id:\\d+}/updater/{userId:\\d+}")
    @RequiresUser
    public ResponseEntity updateUpdater(@PathVariable(name = "id") Long customerId, @PathVariable(name = "userId") Long userId) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Customer customer = customerRepo.getOne(customerId);
        User updater = new User();
        updater.setId(userId);
        customer.setUpdater(updater);
        customerRepo.save(customer);
        return ResponseUtil.success();
    }

    @DeleteMapping("/delete/{id:\\d+}")
    @RequiresUser
    public ResponseEntity delete(@PathVariable(name = "id") Long customerId) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Customer customer = customerRepo.getOne(customerId);
        customer.setEnabled(false);
        customerRepo.save(customer);
        return ResponseUtil.success();
    }

    @PostMapping("/follow/create")
    @RequiresUser
    public ResponseEntity createFollow(CustomerFollowForm customerFollowForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        CustomerFollow customerFollow = customerFollowForm.toCustomerFollow(user);
        customerFollowRepo.save(customerFollow);
        String level = customerFollowForm.getLevel();
        if (StringUtils.isNotBlank(level)) {
            Customer customer = customerRepo.getOne(customerFollowForm.getCustomerId());
            customer.setLevel(CustomerLevel.valueOf(level));
            customerRepo.save(customer);
        }
        return ResponseUtil.success();
    }

    @PostMapping("/follow/delete/{id:\\d+}")
    @RequiresUser
    public ResponseEntity deleteFollow(@PathVariable(name = "id") Long customerFollowId) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        customerFollowRepo.delete(customerFollowId);
        return ResponseUtil.success();
    }

    @PostMapping("/take/create")
    @RequiresUser
    @Transactional
    public ResponseEntity createTake(CustomerTakeForm customerTakeForm) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        CustomerTake customerTake = customerTakeForm.toCustomerTake(user);
        CustomerTake savedCustomerTake = customerTakeRepo.save(customerTake);

        String takeHouseArrayStr = customerTakeForm.getHouseArrayStr();
        List<CustomerTakeHouse> customerTakeHouseList = JSON.parseArray(takeHouseArrayStr, CustomerTakeHouse.class);
        for (CustomerTakeHouse takeHouse : customerTakeHouseList) {
            takeHouse.setTakeId(savedCustomerTake.getId());
        }
        customerTakeHouseRepo.save(customerTakeHouseList);

        String takeReporterArrayStr = customerTakeForm.getReporterArrayStr();
        List<CustomerTakeReporter> customerTakeReporterList = JSON.parseArray(takeReporterArrayStr, CustomerTakeReporter.class);
        for (CustomerTakeReporter takeReporter : customerTakeReporterList) {
            takeReporter.setTakeId(savedCustomerTake.getId());
        }
        customerTakeReporterRepo.save(customerTakeReporterList);

        // 客源动态-报备带看
        addCustomerFollow(user, savedCustomerTake.getCustomerId(), "报备带看", CustomerFollowType.DK);

        Customer customer = customerRepo.findOne(savedCustomerTake.getCustomerId());
        customer.setStatus(CustomerStatus.DK);
        customerRepo.save(customer);

        return ResponseUtil.success();
    }

    @PostMapping("/take/delete/{id:\\d+}")
    @RequiresUser
    public ResponseEntity deleteTake(@PathVariable(name = "id") Long customerTakeId) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        customerTakeRepo.delete(customerTakeId);
        return ResponseUtil.success();
    }

    private void addCustomerFollow(ShiroUser user, Long customerId, String content, CustomerFollowType followType) {
        CustomerFollowForm customerFollowForm = new CustomerFollowForm();
        customerFollowForm.setContent(content);
        if (followType != null) {
            customerFollowForm.setType(followType.toString());
        }
        CustomerFollow customerFollow = customerFollowForm.toCustomerFollow(user);
        customerFollow.setCustomerId(customerId);
        customerFollowRepo.save(customerFollow);
    }

    @PutMapping("/update/status/{id:\\d+}")
    @RequiresRoles(value={SysConstants.ROLE_ADMIN, SysConstants.ROLE_STORE_ADMIN}, logical= Logical.OR)
    public ResponseEntity updateCustomerStatus(@PathVariable(name = "id") Long customerId, CustomerForm customerForm) {

        Customer customer = customerRepo.findOne(customerId);
        customer.setStatus(CustomerStatus.valueOf(customerForm.getStatus()));
        customerRepo.save(customer);
        return ResponseUtil.success();
    }

}
