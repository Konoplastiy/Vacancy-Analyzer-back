package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final ObjectMapper objectMapper;

    public void vacancySetExperience() throws IOException {
        Map<String, String[]> experienceLevels = objectMapper.readValue(new ClassPathResource("experience_levels.json").getFile(),
                Map.class);

        List<Vacancy> vacancies = vacancyRepository.findAll();

        for(Vacancy vacancy : vacancies) {
            String title = vacancy.getVacancyName().toLowerCase();
            String experienceLevel = null;

            for (Map.Entry<String, String[]> entry : experienceLevels.entrySet()) {
                for (String keyword : entry.getValue()) {
                    if (title.contains(keyword)) {
                        experienceLevel = entry.getKey();
                        break;
                    }
                }
                if (experienceLevel != null) {
                    break;
                }
            }
            vacancies.add(vacancy);
        }
        vacancyRepository.saveAll(vacancies);
    }

}
