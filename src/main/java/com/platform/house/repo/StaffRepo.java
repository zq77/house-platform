package com.platform.house.repo;

import com.platform.house.domain.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {
    Page<Staff> findAll(Specification<Staff> spec, Pageable pageable);

    @Query("select s from Staff s where s.user.id=?1")
    List<Staff> findByUserId(Long userId);

    @Query("select s from Staff s join s.role where s.user.id=?1")
    List<Staff> findWithRoleByUserId(Long userId);

    @Query("select s from Staff s where s.store.id=?1")
    List<Staff> findByStoreId(Long storeId);

    @Query("select s from Staff s where s.user.id=?1 and s.store.id=?2")
    Staff findByUserIdAndStoreId(Long userId, Long storeId);

    @Query("select s from Staff s join fetch s.user u where u.id=?1 and s.store.id=?2")
    Staff findWithUserByUserIdAndStoreId(Long userId, Long storeId);
}
