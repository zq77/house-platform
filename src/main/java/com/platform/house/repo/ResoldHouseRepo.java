package com.platform.house.repo;

import com.platform.house.domain.ResoldHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResoldHouseRepo extends JpaRepository<ResoldHouse, Long> {
    Page<ResoldHouse> findAll(Specification<ResoldHouse> spec, Pageable pageable);
}
