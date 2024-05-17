package com.konolastiy.vacancyanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class JobCollectionServiceTest {

    @Mock
    private DjinniService djinniService;

    @Mock
    private DouService douService;

    @Mock
    private RobotaUaService robotaUaService;

    @Mock
    private WorkUaService workUaService;

    @InjectMocks
    private JobCollectionService jobCollectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenFetchAllVacanciesFromAllPlatforms_thenAllServicesCalled() {
        jobCollectionService.fetchAllVacanciesFromAllPlatforms();

        verify(djinniService).getAllVacanciesDjinni();
        verify(douService).getAllVacanciesDouUa();
        verify(robotaUaService).getAllVacanciesRobotaUa();
        verify(workUaService).getAllVacanciesWorkUa();
    }
}