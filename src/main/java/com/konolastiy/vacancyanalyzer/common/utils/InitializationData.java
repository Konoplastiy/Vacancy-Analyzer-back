package com.konolastiy.vacancyanalyzer.common.utils;

import com.konolastiy.vacancyanalyzer.common.enums.CompanyName;
import com.konolastiy.vacancyanalyzer.entity.Source;
import com.konolastiy.vacancyanalyzer.repository.SourceRepository;
import com.konolastiy.vacancyanalyzer.service.DouService;
import com.konolastiy.vacancyanalyzer.service.RobotaService;
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
    private final RobotaService robotaService;
    private final DouService douService;

    @Override
    public void run(String... args) throws Exception {
        if (ddlAuto.startsWith("create")) {
            Source source1 = new Source();
            source1.setName(CompanyName.ROBOTA);
            source1.setLink("https://api.rabota.ua/vacancy/search?ukrainian=true&keyWords=програміст&page=");

            Source source2 = new Source();
            source2.setName(CompanyName.DOU);
            source2.setLink("https://api.rabota.ua/vacancy/search?ukrainian=true&keyWords=програміст&page=");

            sourceRepository.save(source1);
            sourceRepository.save(source2);

            //robotaService.getAllVacanciesRobotaUa();
            douService.getAllVacanciesDouUa();
        }
    }
}
