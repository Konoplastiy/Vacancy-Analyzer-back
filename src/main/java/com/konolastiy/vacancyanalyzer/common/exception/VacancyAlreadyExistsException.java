package com.konolastiy.vacancyanalyzer.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VacancyAlreadyExistsException extends RuntimeException {

    public VacancyAlreadyExistsException(String message) {
        super(message);
    }
}
