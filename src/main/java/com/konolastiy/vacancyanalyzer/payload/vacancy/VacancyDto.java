package com.konolastiy.vacancyanalyzer.payload.vacancy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacancyDto {

    @NotBlank
    private String name;

    @NotBlank
    private String cityName;

    @NotNull
    private LocalDateTime date;

    @NotBlank
    private String companyName;

    @NotBlank
    private String shortDescription;

    private Integer sourceId;
}
