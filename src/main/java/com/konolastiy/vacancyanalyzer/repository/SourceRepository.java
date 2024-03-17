package com.konolastiy.vacancyanalyzer.repository;

import com.konolastiy.vacancyanalyzer.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, Integer> {
}