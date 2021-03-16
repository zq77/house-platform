package com.platform.house.repo;

import com.platform.house.domain.HouseImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HouseImageRepo extends JpaRepository<HouseImage, Long> {

    @Query("select i from HouseImage i where i.type != 'HOUSE_TYPE' and i.houseId = ?1")
    List<HouseImage> findNormalImagesByHouseId(Long houseId);

    @Query("select i from HouseImage i where i.type != 'HOUSE_TYPE' and i.houseId = ?1")
    Page<HouseImage> findNormalImagesByHouseIdAndPage(Long houseId, Pageable pageable);
}
