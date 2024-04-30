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
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ErrorMessageConstants.NO_DESCRIPTION_FOUND_MESSAGE;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.CHROME_DRIVER_PATH;

public class RobotoUaCollector implements Callable<List<Vacancy>> {

    private final Source source;
    private final String link;
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final VacancyService vacancyService;

    public RobotoUaCollector(Source source,
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
        WebDriver driverNextPage = new ChromeDriver(options);

        try {
            driver.get(link);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("card")));

            List<WebElement> cardElements = driver.findElements(By.className("card"));
            for (WebElement cardElement : cardElements) {
                VacancyDto vacancyDto = new VacancyDto();
                vacancyDto.setUrlVacancy(cardElement.getAttribute("href"));
                vacancyDto.setVacancyName(cardElement.findElement(By.tagName("h2")).getText());
                List<WebElement> santamb10 = cardElement.findElements(By.cssSelector("div.santa-flex div.santa-mb-10:not(h2):not(.ng-star-inserted)"));
                if (santamb10.size() == 2) {
                    vacancyDto.setSalary(santamb10.get(0).getText().strip());
                    vacancyDto.setCityName(santamb10.get(1).getText().strip());
                } else {
                    vacancyDto.setSalary("0");
                    vacancyDto.setCityName(santamb10.get(0).getText());
                }
                vacancyDto.setCompanyName(cardElement.findElement(By.className("santa-mr-20")).getText());
                vacancyDto.setDate(cardElement.findElement(By.cssSelector(".santa-typo-secondary.santa-text-black-500")).getText());

                if (vacancyDto.getCityName().contains(vacancyDto.getCompanyName())) {
                    vacancyDto.setCityName(vacancyDto.getCityName().replaceAll(vacancyDto.getCompanyName(), ""));
                }

                driverNextPage.get(vacancyDto.getUrlVacancy());
                vacancyDto.setShortDescription(getShortDescription(driverNextPage));

                vacancyDto.setExperienceLevel(vacancyService.vacancySetExperience(vacancyDto));
                vacancyDto.setSourceId(source);

                Vacancy vacancy = vacancyMapper.vacancyFromDto(vacancyDto);
                vacancy.setSource(source);
                vacancies.add(vacancy);
            }

            vacancyRepository.saveAll(vacancies);

        } catch (Exception e) {
            System.out.println("Error collecting vacancies: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                driverNextPage.quit();
            }
        }
        return vacancies;
    }

    private String getShortDescription(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> paragraphs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".full-desc p")));

        for (WebElement paragraph : paragraphs) {
            String text = paragraph.getText().replace("\n", "").strip();
            if (text.length() >= 50) {
                return text.substring(0, Math.min(text.length(), 200));
            }
        }
        return NO_DESCRIPTION_FOUND_MESSAGE;
    }

}