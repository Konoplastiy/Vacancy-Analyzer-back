package com.konolastiy.vacancyanalyzer.repository;

import com.konolastiy.vacancyanalyzer.entity.PlatformInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformInfoRepository extends JpaRepository<PlatformInfo, Long> {
}
