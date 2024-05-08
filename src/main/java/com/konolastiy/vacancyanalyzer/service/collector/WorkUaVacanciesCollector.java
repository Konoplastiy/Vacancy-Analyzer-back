package com.konolastiy.vacancyanalyzer.service.collector;

import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.VacancyService;

import java.util.List;
import java.util.concurrent.Callable;

public class WorkUaVacanciesCollector implements Callable<List<Vacancy>> {

    private final Source source;
    private final String link;
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final VacancyService vacancyService;

    public WorkUaVacanciesCollector(Source source,
                                    String link,
                                    VacancyRepository vacancyRepository,
                                    VacancyMapper vacancyMapper,
                                    VacancyService vacancyService) {
        this.source = source;
        this.link = link;
        this.vacancyRepository = vacancyRepository;
        this.vacancyMapper = vacancyMapper;
        this.vacancyService = vacancyService;
    }

    @Override
    public List<Vacancy> call() {
        return collectVacancies();
    }

    private List<Vacancy> collectVacancies() {
        return null;
    }
}
