package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final ObjectMapper objectMapper;

    public String vacancySetExperience(VacancyDto vacancyDto) throws IOException {
        Map<String, String[]> experienceLevels = objectMapper.readValue(
                getClass().getResource("src/main/resources/json/experience_levels.json"),
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
}
