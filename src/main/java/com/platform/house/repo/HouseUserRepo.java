package com.platform.house.repo;

import com.platform.house.domain.HouseUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseUserRepo extends JpaRepository<HouseUser, Long> {
    List<HouseUser> findAll(Specification<HouseUser> spec);

    @Query("select i from HouseUser i where i.houseId = ?1")
    List<HouseUser> findHouseUsersByHouseId(Long houseId);
}
