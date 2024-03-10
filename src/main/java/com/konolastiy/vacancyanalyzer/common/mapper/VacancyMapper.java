package com.konolastiy.vacancyanalyzer.common.mapper;

import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    Vacancy fromDto(VacancyDto vacancyDto);
}
