package com.konolastiy.vacancyanalyzer.common;

import lombok.experimental.UtilityClass;

/**
 * Contains various constants used in the vacancy-analyzer application.
 */
@UtilityClass
public class ApplicationConstants {

    /**
     * Constants related to URLs.
     */
    @UtilityClass
    public class UrlConstants {
        public static final String FRONT_URL = "localhost:4000";
        public static final String CHROME_DRIVER_PATH = "src/main/resources/chromedriver.exe";
    }

    /**
     * Constants related to dates and formatting.
     */
    @UtilityClass
    public class DateConstants {
        public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    }

    /**
     * Constants related to other configurations.
     */
    @UtilityClass
    public class ConfigConstants {
        public static final Integer THREAD_POOL_SIZE = 3;
    }
}
