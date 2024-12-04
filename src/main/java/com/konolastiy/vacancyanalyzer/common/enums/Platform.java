package com.konolastiy.vacancyanalyzer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Platform {
  ROBOTAUA("https://robota.ua/zapros/programist/ukraine?page="),
  DJINNI("https://djinni.co/jobs/?page="),
  DOU("https://jobs.dou.ua/vacancies/"),
  WORKUA("https://www.work.ua/en/jobs-developer/?page=");

  private final String link;
}
