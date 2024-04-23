package com.konolastiy.vacancyanalyzer.common.utils;

import com.konolastiy.vacancyanalyzer.common.enums.Platform;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.service.DjinniService;
import com.konolastiy.vacancyanalyzer.service.DouService;
import com.konolastiy.vacancyanalyzer.service.RobotaUaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitializationData implements CommandLineRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;
    private final SourceRepository sourceRepository;
    private final RobotaUaService robotaUaService;
    private final DouService douService;
    private final DjinniService djinniService;

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

            sourceRepository.saveAll(Arrays.asList(source1, source2, source3));

            robotaUaService.getAllVacanciesRobotaUa();
            //douService.getAllVacanciesDouUa();
            //djinniService.getAllVacanciesDjinni();
        }
    }
}
