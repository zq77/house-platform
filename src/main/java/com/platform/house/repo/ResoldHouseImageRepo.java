package com.platform.house.repo;

import com.platform.house.domain.ResoldHouseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResoldHouseImageRepo extends JpaRepository<ResoldHouseImage, Long> {

    @Query("select i from ResoldHouseImage i where i.type = 'NORMAL' and i.resoldHouseId = ?1")
    List<ResoldHouseImage> findByResoldHouseId(Long houseId);
}
