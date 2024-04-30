package com.konolastiy.vacancyanalyzer.common.mapper;

import com.konolastiy.vacancyanalyzer.entity.Vacancy;
import com.konolastiy.vacancyanalyzer.payload.vacancy.VacancyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(source = "sourceId", target = "source")
    Vacancy vacancyFromDto(VacancyDto vacancyDto);

    @Mapping(source = "source", target = "sourceId")
    VacancyDto vacancyToDto(Vacancy vacancy);
}

