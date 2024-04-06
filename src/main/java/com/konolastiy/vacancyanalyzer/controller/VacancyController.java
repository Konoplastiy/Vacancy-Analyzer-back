package com.konolastiy.vacancyanalyzer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    // TODO: Implement CRUD operations for vacancies in the service layer and expose them through the controller

    @GetMapping
    public ResponseEntity<String> fetchAndSaveVacancies() {

        return ResponseEntity.ok("Fetching and saving vacancies process started successfully.");
    }
}
