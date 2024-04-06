package com.konolastiy.vacancyanalyzer.payload.vacancy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konolastiy.vacancyanalyzer.entity.Source;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyDto {

    @NotBlank(message = "Vacancy name must not be blank")
    private String vacancyName;

    @NotBlank(message = "City name must not be blank")
    private String cityName;

    @NotBlank(message = "Date must not be blank")
    private String date;

    @NotBlank(message = "Company name must not be blank")
    private String companyName;

    @NotBlank(message = "Short description must not be blank")
    @Size(max = 400, message = "Short description must be at most 400 characters")
    private String shortDescription;

    @NotBlank(message = "Url vacancy must not be blank")
    private String urlVacancy;

    @NotBlank(message = "Salary must not be blank")
    private String salary;

    @NotNull(message = "Source must not be null")
    private Source sourceId;
}
