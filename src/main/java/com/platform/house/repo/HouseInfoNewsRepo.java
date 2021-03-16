package com.platform.house.repo;

import com.platform.house.domain.HouseNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Office on 2019/3/18.
 */
public interface HouseInfoNewsRepo extends JpaRepository<HouseNews, Long> {
    @Query("select i from HouseNews i where i.houseId = ?1")
    List<HouseNews> findHouseNewsByHouseId(Long houseId);
}
