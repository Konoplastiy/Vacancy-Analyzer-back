package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.service.DouService;
import com.konolastiy.vacancyanalyzer.service.RobotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final RobotaService robotaService;
    private final DouService douService;

    @GetMapping
    public ResponseEntity<String> fetchAndSaveVacancies() {
//        robotaService.getAllVacanciesRobotaUa();
//        douService.parseVacancies();
        return ResponseEntity.ok("Fetching and saving vacancies process started successfully.");
    }
}
