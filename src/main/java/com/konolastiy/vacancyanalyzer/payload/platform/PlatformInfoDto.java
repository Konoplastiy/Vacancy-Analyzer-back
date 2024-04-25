package com.konolastiy.vacancyanalyzer.payload.platform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(
        name = "PlatformInfoResponse",
        description = "Schema to hold platform response information"
)
public class PlatformInfoDto {

    @Schema(description = "Name of the platform", example = "DOU")
    private String namePlatform;

    @Schema(description = "Number of senior level vacancies", example = "10")
    private Integer senior;

    @Schema(description = "Number of middle level vacancies", example = "20")
    private Integer middle;

    @Schema(description = "Number of junior level vacancies", example = "30")
    private Integer junior;

    @Schema(description = "Number of vacancies with other experience levels", example = "5")
    private Integer others;
}
