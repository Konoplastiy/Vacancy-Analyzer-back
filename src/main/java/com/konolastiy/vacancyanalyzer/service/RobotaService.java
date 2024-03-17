package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.LIST_VACANCIES_API_URL_RABOTA_UA;

@Service
@Slf4j
@RequiredArgsConstructor
public class RobotaService {

    private final VacancyRepository vacancyRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final VacancyMapper vacancyMapper;

    private static final Logger logger = LoggerFactory.getLogger(RobotaService.class);

    @Transactional
    public void getAllVacanciesRobotaUa() {
        int page = 1;

        try {
            while (true) {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                        LIST_VACANCIES_API_URL_RABOTA_UA + page, String.class);
                String responseBody = responseEntity.getBody();

                logger.info("Processing page: {}", page);

                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode documentsNode = root.path("documents");

                List<Vacancy> vacancies = new ArrayList<>();

                if (documentsNode.isEmpty()) {
                    break; // No more pages, exit the loop
                }

                // Iterate through the documents node and convert each JSON object into VacancyDto
                for (JsonNode documentNode : documentsNode) {
                    VacancyDto vacancyDto = objectMapper.treeToValue(documentNode, VacancyDto.class);

                    // Convert VacancyDto into Vacancy Entity using MapStruct
                    vacancies.add(vacancyMapper.fromDto(vacancyDto));
                }

                vacancyRepository.saveAll(vacancies);
                page++;
            }
        } catch (RestClientException | JsonProcessingException e) {
            logger.error("Error occurred while fetching and saving vacancies: {}", e.getMessage(), e);
        }
    }

}