package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private static String API_URL_RABOTA = "https://api.rabota.ua/vacancy/search?ukrainian=true&keyWords=програміст";

    private final VacancyRepository vacancyRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final VacancyMapper vacancyMapper;


    @Transactional
    public void getDataAndSave() {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    API_URL_RABOTA,
                    HttpMethod.GET,
                    null,
                    String.class);
            String responseBody = responseEntity.getBody();

            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode documentsNode = root.path("documents");

            List<Vacancy> vacancies = new ArrayList<>();

            // Iterate through the documents node and convert each JSON object into VacancyDto
            for (JsonNode documentNode : documentsNode) {
                VacancyDto vacancyDto = objectMapper.treeToValue(documentNode, VacancyDto.class);

                // Convert VacancyDto into Vacancy Entity using MapStruct
                Vacancy vacancy = vacancyMapper.fromDto(vacancyDto);
                vacancies.add(vacancy);
            }
            vacancyRepository.saveAll(vacancies);
        } catch (RestClientException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

