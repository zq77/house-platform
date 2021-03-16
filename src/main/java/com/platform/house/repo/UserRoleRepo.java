package com.platform.house.repo;

import com.platform.house.domain.Role;
import com.platform.house.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

    @Query("select r from UserRole ur join ur.role r join ur.user u where u.id=?1")
    List<Role> getRolesByUserId(long userId);
}
