package com.konolastiy.vacancyanalyzer.service.collector;

import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
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

public class DjinniVacancyCollector implements Callable<List<Vacancy>> {

    private final Source source;
    private final String link;
    private final VacancyRepository vacancyRepository;

    public DjinniVacancyCollector(Source source, String link, VacancyRepository vacancyRepository) {
        this.source = source;
        this.link = link;
        this.vacancyRepository = vacancyRepository;
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
                .get();

        Elements vacanciesOnPage = page.select(".list-jobs__item.job-list__item");

        List<Vacancy> vacancies = new ArrayList<>();

        for (Element element : vacanciesOnPage) {
            Vacancy vacancy = new Vacancy();
            Element linkVacancy = element.select(".job-list-item__link").first();
            vacancy.setCompanyName(element.select("a.mr-2").text().strip());
            vacancy.setVacancyName(element.select("a.h3.job-list-item__link").text().strip());
            vacancy.setDate(element.select(".mr-2 nobr").text());
            vacancy.setCityName(element.select("span.location-text").text().strip());
            vacancy.setShortDescription(element.select("div.job-list-item__description").text().strip());
            vacancy.setUrlVacancy(DJINNI_HOME_PAGE_URL + linkVacancy.attr("href"));
            vacancy.setSource(source);
            vacancies.add(vacancy);
        }

        // TODO: Implement DTO and Mapper for entity-data conversion.
        vacancyRepository.saveAll(vacancies);

        return vacancies;
    }
}
