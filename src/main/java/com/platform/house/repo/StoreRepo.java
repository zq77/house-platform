package com.platform.house.repo;

import com.platform.house.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepo extends JpaRepository<Store, Long> {
    @Query("select s from Store s where s.name=?1")
    Store getByName(String name);

    Page<Store> findAll(Specification<Store> spec, Pageable pageable);
}
