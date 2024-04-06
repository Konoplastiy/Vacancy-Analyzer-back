package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.collector.DjinniVacancyCollector;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ConfigConstants.THREAD_POOL_SIZE;

@Service
@Slf4j
@RequiredArgsConstructor
public class DjinniService {


    private final VacancyRepository vacancyRepository;
    private final SourceRepository sourceRepository;

    private static final Logger logger = LoggerFactory.getLogger(DjinniService.class);

    @Transactional
    public void getAllVacanciesDjinni() {
        Source source = sourceRepository.findById(2)
                .orElseThrow(() -> new NotFoundException("Source with id 2 not found"));

        String link = source.getLink();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Callable<List<Vacancy>>> tasks = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            tasks.add(new DjinniVacancyCollector(source, link + i, vacancyRepository));
        }

        try {
            List<Future<List<Vacancy>>> futures = executorService.invokeAll(tasks);

            for (Future<List<Vacancy>> future : futures) {
                try {
                    vacancyRepository.saveAll(future.get());
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }

            }
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted while executing tasks: " + e.getMessage());
        }
    }

}
