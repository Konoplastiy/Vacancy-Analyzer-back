package com.konolastiy.vacancyanalyzer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobCollectionService {

    private final DjinniService djinniService;
    private final DouService douService;
    private final RobotaUaService robotaUaService;
    private final WorkUaService workUaService;

    @Transactional
    public void fetchAllVacanciesFromAllPlatforms() {
        //djinniService.getAllVacanciesDjinni();
        //douService.getAllVacanciesDouUa();
        robotaUaService.getAllVacanciesRobotaUa();
        workUaService.getAllVacanciesWorkUa();
    }
}
