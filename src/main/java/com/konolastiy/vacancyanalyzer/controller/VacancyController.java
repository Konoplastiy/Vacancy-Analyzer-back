package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.service.DouService;
import com.konolastiy.vacancyanalyzer.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final DouService douService;


    @GetMapping
    public ResponseEntity<String> fetchAndSaveVacancies() {
        vacancyService.getAllVacanciesRabotaUa();
        douService.parseAndSaveVacancies();
        return ResponseEntity.ok("Fetching and saving vacancies process started successfully.");
    }
}
