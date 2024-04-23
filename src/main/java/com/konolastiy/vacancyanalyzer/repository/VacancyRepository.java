package com.konolastiy.vacancyanalyzer.repository;


import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("FROM Vacancy v WHERE v.vacancyName LIKE %:searchText% OR v.cityName LIKE %:searchText% " +
            "OR v.companyName LIKE %:searchText% OR v.shortDescription LIKE %:searchText% " +
            "OR v.experienceLevel LIKE %:searchText% ORDER BY v.id ASC")
    Page<Vacancy> findAllVacanciesByValue(Pageable pageable,
                                          @Param("searchText") String searchText);

    Integer countBySourceAndExperienceLevel(Source source, String experienceLevel);
}
