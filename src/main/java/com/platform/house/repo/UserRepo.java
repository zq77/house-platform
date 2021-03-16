package com.platform.house.repo;

import com.platform.house.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username=?1")
    User getUserByname(String userName);

    @Query("select u from User u where u.username != 'admin'")
    List<User> findNotAdminUsers();
    
    User getUserByUnionid(String unionid);
    
    @Query("select count(id) from User")
    Integer getUserCount();

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
