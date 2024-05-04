package com.konolastiy.vacancyanalyzer.payload.platform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(
        name = "PlatformInfoResponse",
        description = "Schema to hold platform response information"
)
public class PlatformInfoDto {

    @NotBlank
    @Schema(description = "Name of the platform", example = "DOU")
    private String namePlatform;

    @Nullable
    @Schema(description = "Number of senior level vacancies", example = "10")
    private Integer senior;

    @Nullable
    @Schema(description = "Number of middle level vacancies", example = "20")
    private Integer middle;

    @Nullable
    @Schema(description = "Number of junior level vacancies", example = "30")
    private Integer junior;

    @Nullable
    @Schema(description = "Number of vacancies with other experience levels", example = "5")
    private Integer others;
}
