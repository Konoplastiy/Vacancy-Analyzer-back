package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DouService {

    private static final String LINK = "https://jobs.dou.ua/vacancies/";

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;

    public List<Vacancy> getAllVacanciesDouUa() throws IOException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        // Setting up Selenium WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // To run Chrome in headless mode
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(LINK);
            String htmlDocument = driver.getPageSource();
            Document htmlFragment = Jsoup.parse(htmlDocument);

            // Your logic for parsing vacancies using JSoup
            Document document = getDocument();
            Elements vacancyElements = document.select(".l-vacancy");
            List<VacancyDto> vacancyDtos = new ArrayList<>();
            for (Element vacancyElement : vacancyElements) {
                VacancyDto vacancyDto = new VacancyDto();
                vacancyDto.setName(vacancyElement.select("position_selector").text());
                vacancyDto.setCompanyName(vacancyElement.select("company_selector").text());
                vacancyDto.setShortDescription(vacancyElement.select("description_selector").text());
                vacancyDto.setDate(LocalDateTime.parse(vacancyElement.select("date_selector").text()));
                vacancyDto.setCityName(vacancyElement.select(".cities").text());

                vacancyDtos.add(vacancyDto);
            }

            // Now you can save these DTOs to the database after mapping them to entities
            List<Vacancy> vacancies = new ArrayList<>();
            for (VacancyDto vacancyDto : vacancyDtos) {
                vacancies.add(vacancyMapper.fromDto(vacancyDto));
            }

            return vacancyRepository.saveAll(vacancies);

        } finally {
            driver.quit();
        }
    }

    protected Document getDocument() throws IOException {
        return Jsoup.connect(String.format(LINK))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36")
                .referrer("https://google.com/").get();
    }
}

