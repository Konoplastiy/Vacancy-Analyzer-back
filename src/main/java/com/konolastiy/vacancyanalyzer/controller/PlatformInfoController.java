package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.payload.platform.PlatformInfoDto;
import com.konolastiy.vacancyanalyzer.service.PlatformInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.FRONT_URL;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/v1/platforms")
@RequiredArgsConstructor
public class PlatformInfoController {

    private final PlatformInfoService platformInfoService;

    @GetMapping()
    @CrossOrigin(origins = FRONT_URL)
    public ResponseEntity<List<PlatformInfoDto>> getAllPlatformCounts() {
        List<PlatformInfoDto> response = platformInfoService.getPlatformCountsVacancies();
        return ResponseEntity.ok().body(response);
    }
}
