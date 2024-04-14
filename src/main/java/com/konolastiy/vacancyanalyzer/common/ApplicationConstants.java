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
        public static final String FRONT_URL = "http://localhost:4200";
        public static final String GOOGLE_HOME_URL = "https://www.google.com";
        public static final String DJINNI_HOME_PAGE_URL = "https://djinni.co";
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
        public static final String CHROME_DRIVER_NAME = "webdriver.chrome.driver";
    }

    /**
     * Constants related to error messages.
     */
    @UtilityClass
    public class ErrorMessageConstants {

        /**
         * Error message indicating that no description was found for a vacancy.
         */
        public static final String NO_DESCRIPTION_FOUND_MESSAGE = "Unfortunately, no description was found for this vacancy.";
        public static final String SOURCE_NOT_FOUND_MESSAGE = "Source with id %d not found";
    }

}
