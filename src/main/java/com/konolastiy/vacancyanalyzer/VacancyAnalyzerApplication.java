package com.konolastiy.vacancyanalyzer;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Vacancy-analyzer REST API Documentation",
                description = "Vacancy-analyzer REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Andrew Konoplastiy",
                        email = "konoplastiy@gmail.com",
                        url = "https://www.vacancy-analyzer.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://vacancy-analyzer.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Kanap-Vacancy-analyzer REST API Documentation",
                url = "https://www.kanapbytes.com/swagger-ui.html"
        )
)
public class VacancyAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacancyAnalyzerApplication.class, args);
    }

}
