package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.common.exception.SourceNotFoundException;
import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.collector.RobotoUaCollector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ConfigConstants.THREAD_POOL_SIZE;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ErrorMessageConstants.SOURCE_NOT_FOUND_MESSAGE;

@Service
@Slf4j
@RequiredArgsConstructor
public class RobotaUaService {

    private final VacancyRepository vacancyRepository;
    private final SourceRepository sourceRepository;
    private final VacancyMapper vacancyMapper;

    private static final Logger logger = LoggerFactory.getLogger(RobotaUaService.class);

    @Transactional
    public void getAllVacanciesRobotaUa() {
        int page = 1;

        Source source = sourceRepository.findById(1)
                .orElseThrow(() -> new SourceNotFoundException(String.format(SOURCE_NOT_FOUND_MESSAGE, 1)));

        String link = source.getLink();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Callable<List<Vacancy>>> tasks = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            tasks.add(new RobotoUaCollector(source, link + i, vacancyRepository, vacancyMapper));
        }
        try {
            List<Future<List<Vacancy>>> futures = executorService.invokeAll(tasks);

            for (Future<List<Vacancy>> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }

            }
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted while executing tasks: " + e.getMessage());
        }
    }
}