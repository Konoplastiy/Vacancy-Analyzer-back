package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.common.exception.SourceNotFoundException;
import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.collector.DjinniVacanciesCollector;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
public class WorkUaService {

    private final VacancyRepository vacancyRepository;
    private final SourceRepository sourceRepository;
    private final VacancyMapper vacancyMapper;
    private final VacancyService vacancyService;

    private static final Logger logger = LoggerFactory.getLogger(WorkUaService.class);

    @Async
    @Transactional
    public void getAllVacanciesWorkUa() {
        Source source = sourceRepository.findById(4)
                .orElseThrow(() -> new SourceNotFoundException(String.format(SOURCE_NOT_FOUND_MESSAGE, 4)));

        String link = source.getLink();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Callable<List<Vacancy>>> tasks = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            tasks.add(new DjinniVacanciesCollector(source,
                    link + i,
                    vacancyRepository,
                    vacancyMapper,
                    vacancyService));
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