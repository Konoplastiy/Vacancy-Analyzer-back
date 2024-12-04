package com.konolastiy.vacancyanalyzer.service;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ConfigConstants.THREAD_POOL_SIZE;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ErrorMessageConstants.SOURCE_NOT_FOUND_MESSAGE;

import com.konolastiy.vacancyanalyzer.common.exception.SourceNotFoundException;
import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.collector.DjinniVacanciesCollector;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DjinniService {

  private final VacancyRepository vacancyRepository;
  private final SourceRepository sourceRepository;
  private final VacancyMapper vacancyMapper;
  private final VacancyService vacancyService;

  @Async
  @Transactional
  public void getAllVacanciesDjinni() {
    Source source =
        sourceRepository
            .findById(2)
            .orElseThrow(
                () -> new SourceNotFoundException(String.format(SOURCE_NOT_FOUND_MESSAGE, 2)));

    String link = source.getLink();

    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    List<Callable<List<Vacancy>>> tasks = new ArrayList<>();
    for (int i = 1; i <= 40; i++) {
      tasks.add(
          new DjinniVacanciesCollector(
              source, link + i, vacancyRepository, vacancyMapper, vacancyService));
    }

    try {
      List<Future<List<Vacancy>>> futures = executorService.invokeAll(tasks);

      for (Future<List<Vacancy>> future : futures) {
        try {
          future.get();
        } catch (Exception e) {
          log.warn(e.getMessage());
        }
      }
    } catch (InterruptedException e) {
      log.warn("Thread interrupted while executing tasks: " + e.getMessage());
    }
  }
}
