package com.platform.house.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.house.domain.News;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
	
	Page<News> findAll(Specification<News> spec, Pageable pageable);
	
}
