package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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


    @Async
    @Transactional
    public void getDataAndSave() {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    API_URL_RABOTA,
                    HttpMethod.GET,
                    null,
                    String.class);
            String responseBody = responseEntity.getBody();

            // Parse JSON response using ObjectMapper
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode documentsNode = root.path("documents");

            List<Vacancy> vacancies = new ArrayList<>();

            // Iterate through the documents node and convert each JSON object into VacancyDto
            for (JsonNode documentNode : documentsNode) {
                VacancyDto vacancyDto = objectMapper.treeToValue(documentNode, VacancyDto.class);

                // Convert VacancyDto into Vacancy Entity
                Vacancy vacancy = Vacancy.builder()
                        .name(vacancyDto.getName())
                        .cityName(vacancyDto.getCityName())
                        .date(vacancyDto.getDate())
                        .companyName(vacancyDto.getCompanyName())
                        .shortDescription(vacancyDto.getShortDescription())
                        .build();

                vacancies.add(vacancy);
            }
            vacancyRepository.saveAll(vacancies);
        } catch (RestClientException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

