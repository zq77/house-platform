package com.platform.house.repo;

import com.platform.house.domain.MycenterHouseResold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Office on 2019/1/22.
 */
@Repository
public interface MycenterHouseResoldRepo extends JpaRepository<MycenterHouseResold, Long> {
}
