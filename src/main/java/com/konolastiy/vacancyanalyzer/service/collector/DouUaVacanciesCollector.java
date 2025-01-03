package com.konolastiy.vacancyanalyzer.service.collector;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.GOOGLE_HOME_URL;

import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.repository.VacancyRepository;
import com.konolastiy.vacancyanalyzer.service.VacancyService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RequiredArgsConstructor
public class DouUaVacanciesCollector implements Callable<List<Vacancy>> {

  private final Source source;
  private final String link;
  private final VacancyRepository vacancyRepository;
  private final VacancyMapper vacancyMapper;
  private final VacancyService vacancyService;

  @Override
  public List<Vacancy> call() throws IOException {
    return collectVacancies();
  }

  private List<Vacancy> collectVacancies() throws IOException {
    ArrayList<Vacancy> vacancies = new ArrayList<>();
    try {
      Document document =
          Jsoup.connect(String.format(link))
              .ignoreContentType(true)
              .referrer(GOOGLE_HOME_URL)
              .timeout(10000)
              .header("Accept-Encoding", "gzip, deflate, br, zstd")
              .userAgent(
                  "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
              .maxBodySize(Integer.MAX_VALUE)
              .get();

      Elements vacancyElements = document.select(".l-vacancy");

      for (Element vacancyElement : vacancyElements) {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setUrlVacancy(vacancyElement.select("a.vt").attr("href"));
        vacancyDto.setCityName(vacancyElement.select(".cities").text());
        vacancyDto.setCompanyName(vacancyElement.select("a.company").text());
        vacancyDto.setShortDescription(vacancyElement.select("div.sh-info").text());
        vacancyDto.setDate(vacancyElement.select(".date").text());
        String salary = vacancyElement.select(".salary").text();
        vacancyDto.setSalary(!salary.isEmpty() ? salary : "0");
        vacancyDto.setVacancyName(vacancyElement.select("div.title > a.vt").first().text().strip());
        vacancyDto.setExperienceLevel(vacancyService.vacancySetExperience(vacancyDto));
        vacancyDto.setSourceId(source);

        Vacancy vacancy = vacancyMapper.vacancyFromDto(vacancyDto);
        vacancy.setSource(vacancyDto.getSourceId());
        vacancies.add(vacancy);
      }

      return vacancyRepository.saveAll(vacancies);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
