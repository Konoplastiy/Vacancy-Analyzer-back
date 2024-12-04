package com.konolastiy.vacancyanalyzer.common.utils;

import com.konolastiy.vacancyanalyzer.common.enums.Platform;
import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.repository.BlogRepository;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.service.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializationData implements CommandLineRunner {

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String ddlAuto;

  private final SourceRepository sourceRepository;
  private final BlogRepository blogRepository;
  private final JobCollectionService jobCollectionService;

  @Override
  public void run(String... args) throws Exception {
    if (ddlAuto.startsWith("create-drop")) {
      Source source1 = new Source();
      source1.setName(Platform.ROBOTAUA);
      source1.setLink(Platform.ROBOTAUA.getLink());

      Source source2 = new Source();
      source2.setName(Platform.DJINNI);
      source2.setLink(Platform.DJINNI.getLink());

      Source source3 = new Source();
      source3.setName(Platform.DOU);
      source3.setLink(Platform.DOU.getLink());

      Source source4 = new Source();
      source4.setName(Platform.WORKUA);
      source4.setLink(Platform.WORKUA.getLink());

      sourceRepository.saveAll(Arrays.asList(source1, source2, source3, source4));

      for (int i = 0; i < 25; i++) {
        List<Blog> blog = new ArrayList<>();
        Blog blogi = new Blog();
        blogi.setTitle("Як знайти роботу в IT 2024?");
        blogi.setContent("Lorem ipsum dolor sit amet, consectetur adipisicing elit.");
        blogi.setDatePublic(LocalDate.now());
        blogi.setCountViews(87 + i);
        blog.add(blogi);
        blogRepository.saveAll(blog);
      }

      jobCollectionService.fetchAllVacanciesFromAllPlatforms();
    }
  }
}
