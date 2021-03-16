package com.platform.house.repo;

import com.platform.house.domain.Permission;
import com.platform.house.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RolePermissionRepo extends JpaRepository<RolePermission, Long> {

    @Query("select p from RolePermission rp join rp.permission p join rp.role r where r.id in (:roleIds)")
    List<Permission> getPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
