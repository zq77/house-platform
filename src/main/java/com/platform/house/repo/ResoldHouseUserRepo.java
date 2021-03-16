package com.platform.house.repo;

import com.platform.house.domain.ResoldHouseUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResoldHouseUserRepo extends JpaRepository<ResoldHouseUser, Long> {
    List<ResoldHouseUser> findAll(Specification<ResoldHouseUser> spec);
}
