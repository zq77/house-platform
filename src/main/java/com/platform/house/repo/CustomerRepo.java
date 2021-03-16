package com.platform.house.repo;

import com.platform.house.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Office on 2019/1/22.
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);
}
