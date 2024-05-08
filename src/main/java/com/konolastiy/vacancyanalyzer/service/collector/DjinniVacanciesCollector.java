package com.konolastiy.vacancyanalyzer.service.collector;

import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.VacancyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.DJINNI_HOME_PAGE_URL;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.GOOGLE_HOME_URL;

public class DjinniVacanciesCollector implements Callable<List<Vacancy>> {

    private final Source source;
    private final String link;
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final VacancyService vacancyService;

    public DjinniVacanciesCollector(Source source,
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
    public List<Vacancy> call() throws IOException {
        return collectVacancies();
    }

    private List<Vacancy> collectVacancies() throws IOException {
        Document page = Jsoup.connect(link)
                .ignoreContentType(true)
                .referrer(GOOGLE_HOME_URL)
                .header("Accept-Encoding", "gzip")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .maxBodySize(Integer.MAX_VALUE)
                .followRedirects(true)
                .timeout(25000)
                .get();

        Elements vacanciesOnPage = page.select(".list-jobs__item.job-list__item");

        List<Vacancy> vacancies = new ArrayList<>();

        for (Element element : vacanciesOnPage) {
            VacancyDto vacancyDto = new VacancyDto();
            Element linkVacancy = element.select(".job-list-item__link").first();
            vacancyDto.setCompanyName(element.select("a.mr-2").text().strip());
            vacancyDto.setVacancyName(element.select("a.h3.job-list-item__link").text().strip());
            vacancyDto.setDate(element.select(".mr-2 nobr").text());
            vacancyDto.setCityName(element.select("span.location-text").text().strip());
            vacancyDto.setShortDescription(element.select("div.job-list-item__description").text().strip());
            vacancyDto.setUrlVacancy(DJINNI_HOME_PAGE_URL + linkVacancy.attr("href"));
            vacancyDto.setExperienceLevel(vacancyService.vacancySetExperience(vacancyDto));
            vacancyDto.setSourceId(source);

            Vacancy vacancy = vacancyMapper.vacancyFromDto(vacancyDto);
            vacancy.setSource(source);
            vacancies.add(vacancy);
        }

        vacancyRepository.saveAll(vacancies);

        return vacancies;
    }
}
