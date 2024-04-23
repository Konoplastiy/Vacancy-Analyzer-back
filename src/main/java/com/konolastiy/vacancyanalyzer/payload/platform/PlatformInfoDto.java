package com.konolastiy.vacancyanalyzer.payload.platform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformInfoDto {

    private String namePlatform;
    private Integer senior;
    private Integer middle;
    private Integer junior;
    private Integer others;
}
