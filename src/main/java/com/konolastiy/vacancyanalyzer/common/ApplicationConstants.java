package com.konolastiy.vacancyanalyzer.common;

import com.konolastiy.vacancyanalyzer.entity.Blog;
import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import com.konolastiy.vacancyanalyzer.payload.blog.BlogDto;
import com.konolastiy.vacancyanalyzer.payload.newslettersubscriber.NewsletterSubscriberDto;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

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
        public static final String EXPERIENCE_LEVELS_JSON = "/json/ExperienceLevels.json";
        public static final String PROGRAMMING_LANGUAGE_JSON = "/json/ProgrammingLanguages.json";

    }

    /**
     * Constants related to dates and formatting.
     */
    @UtilityClass
    public class Validation {
        public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

        /**
         * A regular expression for validating email addresses.
         */
        public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$";
        public static final Pattern EMAIL_PATTERN = Pattern.compile(Validation.EMAIL_REGEX);
    }

    /**
     * Constants related to other configurations.
     */
    @UtilityClass
    public class ConfigConstants {
        public static final Integer THREAD_POOL_SIZE = 3;
        public static final String CHROME_DRIVER_NAME = "webdriver.chrome.driver";
        public static final String[] HTTP_METHODS = {"POST", "GET"};
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
        public static final String SUBSCRIBER_ALREADY_EXITS_MESSAGE = "The subscriber already exists with email: %s";
    }

    @UtilityClass
    public class ExperienceLevelConstants {
        public static final String SENIOR = "Senior";
        public static final String MIDDLE = "Middle";
        public static final String JUNIOR = "Junior";
        public static final String DEFAULT_EXPERIENCE_LEVEL = "Others";
    }

    @UtilityClass
    public class BlogControllerTest {
        public static final BlogDto TEST_BLOG_DTO = new BlogDto();
        public static final Blog TEST_BLOG = new Blog();
    }

    @UtilityClass
    public class NewsletterSubscriberControllerTest {
        public static final NewsletterSubscriberDto TEST_SUBSCRIBER_DTO = new NewsletterSubscriberDto();
        public static final NewsletterSubscriber TEST_SUBSCRIBER = new NewsletterSubscriber();
        public static final String ALREADY_ERROR_MES = "Already subscribed";
        public static final String TEST_EMAIL = "test@example.com";
    }

    @UtilityClass
    public class EmailServiceTest {
        public static final String TO = "test@example.com";
        public static final String SUBJECT = "Test Subject";
        public static final String CONTENT = "Hello, this is a test email.";
    }

}
