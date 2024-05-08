package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JsonFileDataService {

    private final ObjectMapper objectMapper;

    public Map<String, String[]> loadJsonDataAsMap(String jsonFilePath) throws IOException {
        return objectMapper.readValue(
                getClass().getResourceAsStream(jsonFilePath),
                new TypeReference<Map<String, String[]>>() {
                }
        );
    }
}
