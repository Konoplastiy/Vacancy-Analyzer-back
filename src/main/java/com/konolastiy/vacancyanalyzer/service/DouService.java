package com.konolastiy.vacancyanalyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DouService {

    private static final String LINK = "https://jobs.dou.ua/vacancies/?search=";
    private static final int VACANCIES_PER_REQUEST = 40;

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;

    public String parseAndSaveVacancies() {
        try {
            String link = LINK;

            Document document = Jsoup.connect(link).get();
            int foundVacancies = getTotalAmountOfVacancies(document);

            int totalShifts = getTotalShifts(foundVacancies);

            for (int shift = 0; shift <= totalShifts; shift++) {
                int offset = calculateOffset(shift);

                String jsonUrl = String.format("https://jobs.dou.ua/vacancies/xhr-load/");
                String params = String.format("csrfmiddlewaretoken=%s&count=%d", getCsrfMiddlewareToken(document), offset);

                String jsonResponse = new RestTemplate().postForObject(jsonUrl, params, String.class);
                Document ajaxDocument = Jsoup.parse(jsonResponse);

                Elements vacancyElements = ajaxDocument.select("li.l-vacancy .vacancy");

                for (Element vacancyElement : vacancyElements) {
                    String name = vacancyElement.select(".title a.vt").first().text();
                    String companyName = vacancyElement.select(".title a.company").first().text();
                    String cityName = vacancyElement.select(".title span.cities").first().text();
                    LocalDateTime date = LocalDateTime.now(); // Set the date as needed
                    String shortDescription = vacancyElement.select(".sh-info").first().text();

                    VacancyDto vacancyDto = VacancyDto.builder()
                            .name(name)
                            .companyName(companyName)
                            .cityName(cityName)
                            .date(date)
                            .shortDescription(shortDescription)
                            .build();

                    Vacancy vacancy = vacancyMapper.fromDto(vacancyDto);
                    vacancyRepository.save(vacancy);
                }
            }

            return "Vacancies parsed and saved successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to parse and save vacancies!";
        }
    }

    private int getTotalAmountOfVacancies(Document document) {
        String titleFoundVacancies = document.select(".b-inner-page-header h1").first().text();
        int firstSpace = titleFoundVacancies.indexOf(' ');
        titleFoundVacancies = titleFoundVacancies.substring(0, firstSpace);

        return Integer.parseInt(titleFoundVacancies);
    }

    private int getTotalShifts(int totalVacancies) {
        return totalVacancies / VACANCIES_PER_REQUEST;
    }

    private int calculateOffset(int shift) {
        return shift * VACANCIES_PER_REQUEST;
    }

    private String getCsrfMiddlewareToken(Document document) {
        return document.select("input[name=csrfmiddlewaretoken]").attr("value");
    }
}
