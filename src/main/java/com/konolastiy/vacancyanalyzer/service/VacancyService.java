package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final ObjectMapper objectMapper;
    private final VacancyRepository vacancyRepository;

    public  String vacancySetExperience(VacancyDto vacancyDto) throws IOException {
        Map<String, String[]> experienceLevels = objectMapper.readValue(
                getClass().getResourceAsStream("/json/experience_levels.json"),
                Map.class);

        String title = vacancyDto.getVacancyName().toLowerCase();

        for (Map.Entry<String, String[]> entry : experienceLevels.entrySet()) {
            String experienceLevel = entry.getKey();
            String[] keywords = entry.getValue();
            for (String keyword : keywords) {
                if (title.contains(keyword)) {
                    vacancyDto.setExperienceLevel(experienceLevel);
                    return experienceLevel;
                }
            }
        }

        vacancyDto.setExperienceLevel("");
        return "";
    }


    @Nullable
    public Page<Vacancy> findAll(final Pageable pageable, final String searchText) {
        return vacancyRepository.findAllVacanciesByValue(pageable, searchText);
    }


    public Page<Vacancy> findAll(final Pageable pageable) {
        return vacancyRepository.findAll(pageable);
    }
}
