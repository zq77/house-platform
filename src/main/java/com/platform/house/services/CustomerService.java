package com.platform.house.services;

import com.google.common.base.Strings;
import com.platform.house.constant.CustomerFollowType;
import com.platform.house.constant.CustomerLevel;
import com.platform.house.constant.CustomerSearchType;
import com.platform.house.domain.Customer;
import com.platform.house.domain.CustomerFollow;
import com.platform.house.domain.CustomerTake;
import com.platform.house.dto.CustomerDto;
import com.platform.house.dto.CustomerFollowDto;
import com.platform.house.dto.CustomerTakeDto;
import com.platform.house.form.CustomerSearchForm;
import com.platform.house.repo.CustomerFollowRepo;
import com.platform.house.repo.CustomerRepo;
import com.platform.house.repo.CustomerTakeRepo;
import com.platform.house.utils.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Office on 2019/1/28.
 */
@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerFollowRepo customerFollowRepo;

    @Autowired
    private CustomerTakeRepo customerTakeRepo;

    @Transactional(readOnly = true)
    public PageVo<CustomerDto> search(CustomerSearchForm customerSearchForm) {
        PageRequest pageRequest = new PageRequest(customerSearchForm.getPageRequestPage(), customerSearchForm.getPageSize(), new Sort(Sort.Direction.DESC, "id"));

        List<Long> filterCustomerIdByFollow = null;
        List<Long> filterCustomerIdByTake = null;
        if (StringUtils.isNotBlank(customerSearchForm.getCustomerSearchType())) {

            if (CustomerSearchType.DGJ.toString().equals(customerSearchForm.getCustomerSearchType())) {
                String currentMonth = DateUtils.formatDate(new Date(), "yyyy-MM");
                List<CustomerFollow> matchCustomerFollow = customerFollowRepo.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.notEqual(root.get("type").as(String.class), CustomerFollowType.AUTO.toString()));
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                });
                filterCustomerIdByFollow = matchCustomerFollow.stream().map(CustomerFollow::getCustomerId).distinct().collect(Collectors.toList());
            }

            if (CustomerSearchType.BYDK.toString().equals(customerSearchForm.getCustomerSearchType())) {
                String currentMonth = DateUtils.formatDate(new Date(), "yyyy-MM");
                List<CustomerTake> matchCustomerTake = customerTakeRepo.findAll((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.greaterThan(root.get("createTime").as(String.class), currentMonth));
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                });
                filterCustomerIdByTake = matchCustomerTake.stream().map(CustomerTake::getCustomerId).distinct().collect(Collectors.toList());
            }
        }
        final List<Long> finalFilterCustomerIdByFollow = filterCustomerIdByFollow;
        final List<Long> finalFilterCustomerIdByTake = filterCustomerIdByTake;
        Page<Customer> customerPage = customerRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            String keywords = StringUtils.trimToEmpty(customerSearchForm.getKeywords());
            if (!Strings.isNullOrEmpty(keywords)) {
                predicates.add(cb.or(
                        cb.like(root.get("name").as(String.class), "%"+keywords+"%"),
                        cb.like(root.get("mobile").as(String.class), "%"+keywords+"%"),
                        cb.like(root.get("nameBak").as(String.class), "%"+keywords+"%"),
                        cb.like(root.get("mobileBak").as(String.class), "%"+keywords+"%")
                ));
            }
            if (StringUtils.isNotBlank(customerSearchForm.getCustomerSearchType())) {
                if (CustomerSearchType.ABK.toString().equals(customerSearchForm.getCustomerSearchType())) {
                    predicates.add(cb.or(
                            cb.equal(root.get("level").as(String.class), CustomerLevel.A.toString()),
                            cb.equal(root.get("level").as(String.class), CustomerLevel.B.toString())
                    ));
                }
            }
            if (finalFilterCustomerIdByFollow != null) {
                predicates.add(root.get("id").in(finalFilterCustomerIdByFollow).not());
            }
            if (finalFilterCustomerIdByTake != null) {
                predicates.add(root.get("id").in(finalFilterCustomerIdByTake));
            }
            List<Long> createIdList = customerSearchForm.getCreateIdList();
            if (createIdList != null) {
                predicates.add(root.get("creator").in(createIdList));
            }
            predicates.add(cb.equal(root.get("enabled"), 1));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);
        if (!customerPage.hasContent()) {
            return new PageVo<>(pageRequest);
        }
        List<CustomerDto> dtos = customerPage.getContent().stream().map(CustomerDto::new).collect(Collectors.toList());
        return new PageVo<>(dtos, pageRequest, customerPage.getTotalElements());
    }

    public CustomerDto mappingToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto(customer);
        CustomerFollow customerFollow = new CustomerFollow();
        customerFollow.setCustomerId(customer.getId());
        Example<CustomerFollow> example = Example.of(customerFollow);
        List<CustomerFollow> customerFollowList = customerFollowRepo.findAll(example);
        List<CustomerFollowDto> customerFollowDtoList = customerFollowList.stream().map(CustomerFollowDto::new).sorted(Comparator.comparing(CustomerFollowDto::getId).reversed()).collect(Collectors.toList());
        customerDto.setCustomerFollowList(customerFollowDtoList);

        CustomerTake customerTake = new CustomerTake();
        customerTake.setCustomerId(customer.getId());
        Example<CustomerTake> exampleOfTake = Example.of(customerTake);
        List<CustomerTake> customerTakeList = customerTakeRepo.findAll(exampleOfTake);
        List<CustomerTakeDto> customerTakeDtoList = customerTakeList.stream().map(CustomerTakeDto::new).sorted(Comparator.comparing(CustomerTakeDto::getId).reversed()).collect(Collectors.toList());
        customerDto.setCustomerTakeList(customerTakeDtoList);
        return customerDto;
    }
}
