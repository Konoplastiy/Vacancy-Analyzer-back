package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.service.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.FRONT_URL;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/v1/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyMapper vacancyMapper;
    private final VacancyService vacancyService;

    @Operation(summary = "Find all Vacancies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacancies are found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    @CrossOrigin(origins = FRONT_URL)
    public ResponseEntity<Page<VacancyDto>> findAllByValue(@PageableDefault final Pageable pageable,
                                                           @RequestParam(name = "searchText", required = false) final String searchText) {
        final Page<VacancyDto> vacancies = vacancyService
                .findAllVacancies(pageable, searchText)
                .map(vacancyMapper::vacancyToDto);
        log.info("By value: {} was found vacancies: {}. Size of page = {}. Page number = {}. Page sort = {}",
                searchText, vacancies, pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort());
        return ResponseEntity.status(HttpStatus.OK).body(vacancies);
    }
}
