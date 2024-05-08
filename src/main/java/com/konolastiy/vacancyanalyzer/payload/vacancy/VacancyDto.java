package com.konolastiy.vacancyanalyzer.payload.vacancy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konolastiy.vacancyanalyzer.entity.Source;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(
        name = "VacancyResponse",
        description = "Schema to hold vacancy response information"
)
public class VacancyDto {

    @NotBlank(message = "Vacancy name must not be blank")
    @Schema(description = "Name of the vacancy", example = "Software Engineer")
    private String vacancyName;

    @NotBlank(message = "City name must not be blank")
    @Schema(description = "City where the vacancy is located", example = "New York")
    private String cityName;

    @NotBlank(message = "Date must not be blank")
    @Schema(description = "Date of the vacancy", example = "Today")
    private String date;

    @NotBlank(message = "Company name must not be blank")
    @Schema(description = "Name of the company offering the vacancy", example = "Tech Corp")
    private String companyName;

    @NotBlank(message = "Short description must not be blank")
    @Size(max = 400, message = "Short description must be at most 400 characters")
    @Schema(description = "Short description of the vacancy", example = "Join our team as a Software Engineer!")
    private String shortDescription;

    @NotBlank(message = "Url vacancy must not be blank")
    @Schema(description = "URL link to the vacancy", example = "https://example.com/vacancies/9969521")
    private String urlVacancy;

    @NotBlank(message = "Salary must not be blank")
    @Schema(description = "Salary for the vacancy", example = "2000 USD")
    private String salary;

    @Nullable
    @Schema(description = "Experience level required for the vacancy", example = "Senior")
    private String experienceLevel;

    @Nullable
    @Schema(description = "English level required for the vacancy", example = "Intermediate")
    private String englishLevel;

    @Schema(description = "Source of the vacancy", implementation = Source.class)
    private Source sourceId;
}
