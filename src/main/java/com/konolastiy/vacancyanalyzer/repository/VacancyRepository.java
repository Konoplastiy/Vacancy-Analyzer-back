package com.konolastiy.vacancyanalyzer.repository;


import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
