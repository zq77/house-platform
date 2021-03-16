package com.platform.house.repo;

import com.platform.house.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.value=?1")
    Role getByRoleType(String roleValue);
}
