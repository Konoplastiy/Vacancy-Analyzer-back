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

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.GOOGLE_HOME_URL;

public class DouUaCollector implements Callable<List<com.konolastiy.vacancyanalyzer.entity.Vacancy>> {

    private final Source source;
    private final String link;
    private final VacancyRepository vacancyRepository;

    public DouUaCollector(Source source, String link, VacancyRepository vacancyRepository) {
        this.source = source;
        this.link = link;
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public List<Vacancy> call() throws IOException {
        return collectVacancies();
    }

    private List<Vacancy> collectVacancies() throws IOException {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        try {
            Document document = Jsoup.connect(String.format(link))
                    .ignoreContentType(true)
                    .referrer(GOOGLE_HOME_URL)
                    .header("Accept-Encoding", "gzip, deflate, br, zstd")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                    .maxBodySize(Integer.MAX_VALUE)
                    .get();

            Elements vacancyElements = document.select(".l-vacancy");

            for (Element vacancyElement : vacancyElements) {
                Vacancy vacancy = new Vacancy();
                vacancy.setUrlVacancy(vacancyElement.select("a.vt").attr("href"));
                vacancy.setCityName(vacancyElement.select(".cities").text());
                vacancy.setCompanyName(vacancyElement.select("a.company").text());
                vacancy.setShortDescription(vacancyElement.select("div.sh-info").text());
                vacancy.setDate(vacancyElement.select(".date").text());
                String salary = vacancyElement.select(".salary").text();
                vacancy.setSalary(!salary.isEmpty() ? salary : "0 - ЗП не вказана");
                vacancy.setVacancyName(vacancyElement.select("div.title > a.vt").first().text().strip());


                vacancy.setSource(source);
                vacancies.add(vacancy);
            }

            return vacancyRepository.saveAll(vacancies);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

