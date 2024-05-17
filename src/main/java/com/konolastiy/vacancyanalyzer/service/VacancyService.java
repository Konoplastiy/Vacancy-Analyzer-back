package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.common.specification.VacancySpecifications;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ExperienceLevelConstants.DEFAULT_EXPERIENCE_LEVEL;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.EXPERIENCE_LEVELS_JSON;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.PROGRAMMING_LANGUAGE_JSON;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final JsonFileDataService jsonFileDataService;
    private final VacancyRepository vacancyRepository;

    @Transactional
    public String vacancySetExperience(VacancyDto vacancyDto) throws IOException {
        String title = vacancyDto.getVacancyName().toLowerCase();

        Map<String, String[]> experienceLevels = jsonFileDataService.loadJsonDataAsMap(EXPERIENCE_LEVELS_JSON);

        return experienceLevels.entrySet().stream()
                .filter(entry -> Arrays.stream(entry.getValue())
                        .anyMatch(title::contains))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(DEFAULT_EXPERIENCE_LEVEL);
    }

    @Transactional
    public String vacancySetProgramingLanguage(VacancyDto vacancyDto) throws IOException {
        String title = vacancyDto.getVacancyName().toLowerCase();

        Map<String, String[]> experienceLevels = jsonFileDataService.loadJsonDataAsMap(PROGRAMMING_LANGUAGE_JSON);

        return experienceLevels.entrySet().stream()
                .filter(entry -> Arrays.stream(entry.getValue())
                        .anyMatch(title::contains))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(DEFAULT_EXPERIENCE_LEVEL);
    }

    @Transactional(readOnly = true)
    public Page<Vacancy> findAllVacancies(final Pageable pageable,
                                          final String searchText,
                                          final String experienceLevel,
                                          final String platformName) {
        Specification<Vacancy> spec = Specification.where(
                        VacancySpecifications.hasTextInAttributes(searchText))
                .and(VacancySpecifications.hasExperienceLevel(experienceLevel))
                .and(VacancySpecifications.hasPlatformName(platformName));

        return vacancyRepository.findAll(spec, pageable);
    }
}
