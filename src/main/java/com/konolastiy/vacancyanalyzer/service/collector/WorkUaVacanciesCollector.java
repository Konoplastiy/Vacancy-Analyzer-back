package com.konolastiy.vacancyanalyzer.service.collector;

import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.VacancyService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ConfigConstants.CHROME_DRIVER_NAME;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.CHROME_DRIVER_PATH;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.WORK_HOME_PAGE_URL;

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
        List<Vacancy> vacancies = new ArrayList<>();
        System.setProperty(CHROME_DRIVER_NAME, CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(link);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("card-hover")));

            List<WebElement> cardElements = driver.findElements(By.className("card-hover"));
            for (WebElement cardElement : cardElements) {
                VacancyDto vacancyDto = new VacancyDto();
                WebElement linkElement = cardElement.findElement(By.cssSelector("h2 a"));
                vacancyDto.setUrlVacancy(WORK_HOME_PAGE_URL + linkElement.getAttribute("href"));
                vacancyDto.setVacancyName(linkElement.getText());
                vacancyDto.setCompanyName(cardElement.findElement(By.cssSelector(".add-top-xs > span > span.strong-600")).getText());
                vacancyDto.setDate(cardElement.findElement(By.cssSelector(".text-default-7.add-top")).getText());
                vacancyDto.setShortDescription(cardElement.findElement(By.cssSelector(".ellipsis")).getText());

                String city = cardElement.findElement(By.cssSelector(".add-top-xs > span:not(.add-right-xs)")).getText();
                vacancyDto.setCityName(city.contains(vacancyDto.getCompanyName()) ? city.replaceAll(vacancyDto.getCompanyName(), "") : city);

                vacancyDto.setProgrammingLanguage(vacancyService.vacancySetProgramingLanguage(vacancyDto));
                vacancyDto.setExperienceLevel(vacancyService.vacancySetExperience(vacancyDto));
                vacancyDto.setSourceId(source);

                Vacancy vacancy = vacancyMapper.vacancyFromDto(vacancyDto);
                vacancy.setSource(source);
                vacancies.add(vacancy);
            }

            vacancyRepository.saveAll(vacancies);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        return vacancies;
    }
}
