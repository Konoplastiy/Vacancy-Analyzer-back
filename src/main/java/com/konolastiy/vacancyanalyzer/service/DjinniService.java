package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DjinniService {

    private final VacancyRepository vacancyRepository;
    private final SourceRepository sourceRepository;

    @Transactional
    public void getAllVacanciesDjinni() {
        Source source = sourceRepository.findById(2)
                .orElseThrow(() -> new NotFoundException("Source with id 2 not found"));

        String link = "https://djinni.co/jobs/?page=";

        Runnable task = () -> {
            int page = 1;
            boolean hasNextPage = true;
            try {
                while (hasNextPage) {
                    Document document = Jsoup.connect(link + page)
                            .ignoreContentType(true)
                            .referrer("https://www.google.com")
                            .timeout(10000)
                            .header("Accept-Encoding", "gzip")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .maxBodySize(Integer.MAX_VALUE)
                            .followRedirects(true)
                            .get();

                    Elements elements = document.select(".list-jobs__item.job-list__item");

                    List<Vacancy> vacancies = new ArrayList<>();

                    for (Element element : elements) {
                        Element linkVacancy = element.select(".job-list-item__link").first();
                        String url = linkVacancy.attr("href");
                        Vacancy vacancy = new Vacancy();
                        vacancy.setCompanyName(element.select("a.mr-2").text().strip());
                        vacancy.setVacancyName(element.select("a.h3.job-list-item__link").text().strip());
                        //vacancy.setDate(LocalDateTime.parse(element.select("mr-2 nobr").text()));
                        vacancy.setCityName(element.select("span.location-text").text().strip());
                        vacancy.setShortDescription(element.select("div.job-list-item__description").text().strip());
                        vacancy.setUrlVacancy("https://djinni.co" + url);
                        vacancy.setSource(source);
                        vacancies.add(vacancy);
                    }

                    vacancyRepository.saveAll(vacancies);

                    hasNextPage = !document.select(".job-list-item__link").isEmpty();
                    page++;

                    Thread.sleep(5000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
