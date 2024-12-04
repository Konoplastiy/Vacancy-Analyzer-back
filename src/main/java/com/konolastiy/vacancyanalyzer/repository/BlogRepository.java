package com.konolastiy.vacancyanalyzer.repository;

import com.konolastiy.vacancyanalyzer.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {}
