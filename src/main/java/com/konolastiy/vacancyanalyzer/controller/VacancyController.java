package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.common.mapper.VacancyMapper;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import com.konolastiy.vacancyanalyzer.service.VacancyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/vacancies")
@RequiredArgsConstructor
@Slf4j
public class VacancyController {

    private final VacancyMapper vacancyMapper;
    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<List<VacancyDto>> findAllByValue(@PageableDefault final Pageable pageable,
                                                           @RequestParam(name = "searchText",
                                                                   required = false)
                                                           @NotBlank final String searchText) {
        if(searchText == null || searchText.isEmpty()) {
            final List<VacancyDto> vacancies = vacancyService.findAll(pageable).stream().map(vacancyMapper::toDto)
                    .collect(Collectors.toList());
            log.info("Vacancies were found : {}. Size of page = {}. Page number = {}. Page sort = {}", vacancies,
                    pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort());
            return new ResponseEntity<>(vacancies, HttpStatus.OK);
        }

        final List<VacancyDto> vacancies = vacancyService.findAll(pageable, searchText)
                .stream().map(vacancyMapper::toDto).collect(Collectors.toList());
        log.info(
                "By value: {} was found vacancies: {}. Size of page = {}. Page number = {}. Page sort = {}",
                searchText, vacancies, pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort());
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }
}
