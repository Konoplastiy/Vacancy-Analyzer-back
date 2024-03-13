package com.konolastiy.vacancyanalyzer.common;

import lombok.experimental.UtilityClass;

/**
 * Contains various constants used in the vacancy-analyzer application.
 */
@UtilityClass
public class ApplicationConstants {

    /**
     * Inner utility class for storing URL constants.
     */
    @UtilityClass
    public class UrlConstants {

        public static final String LIST_VACANCIES_API_URL_RABOTA_UA =
                "https://api.rabota.ua/vacancy/search?ukrainian=true&keyWords=програміст&page=";

    }
}
