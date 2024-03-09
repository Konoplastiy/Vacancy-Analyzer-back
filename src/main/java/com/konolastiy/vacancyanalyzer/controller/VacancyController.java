package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<String> fetchAndSaveVacancies() {
        vacancyService.getDataAndSave();
        return ResponseEntity.ok("Fetching and saving vacancies process started successfully.");
    }
}
