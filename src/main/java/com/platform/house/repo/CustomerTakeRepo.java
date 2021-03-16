package com.platform.house.repo;

import com.platform.house.domain.CustomerTake;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Office on 2019/1/22.
 */
@Repository
public interface CustomerTakeRepo extends JpaRepository<CustomerTake, Long> {
    List<CustomerTake> findAll(Specification<CustomerTake> spec);
}
