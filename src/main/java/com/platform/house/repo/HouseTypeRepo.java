package com.platform.house.repo;

import com.platform.house.domain.House;
import com.platform.house.domain.HouseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-08-04 15:02
 */
@Repository
public interface HouseTypeRepo extends JpaRepository<HouseType, Long> {

    @Query("select t from HouseType t where t.houseId = ?1 ")
    List<HouseType> findAllByHouseId(Long houseId);


    List<HouseType> findAll(Specification<HouseType> spec);
}
