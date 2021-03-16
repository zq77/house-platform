package com.platform.house.repo;

import com.platform.house.domain.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-06-26 22:40
 */
@Repository
public interface HouseInfoRepo extends JpaRepository<House, Long> {


    Page<House> findAll(Specification<House> spec, Pageable pageable);

}
