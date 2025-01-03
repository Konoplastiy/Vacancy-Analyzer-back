package com.konolastiy.vacancyanalyzer.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SourceNotFoundException extends RuntimeException {

  public SourceNotFoundException(String message) {
    super(message);
  }
}
