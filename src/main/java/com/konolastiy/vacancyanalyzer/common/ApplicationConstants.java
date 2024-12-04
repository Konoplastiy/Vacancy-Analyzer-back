package com.konolastiy.vacancyanalyzer.common;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {

  @UtilityClass
  public class UrlConstants {
    public static final String FRONT_URL = "http://localhost:4200";
    public static final String GOOGLE_HOME_URL = "https://www.google.com";
    public static final String DJINNI_HOME_PAGE_URL = "https://djinni.co";
    public static final String WORK_HOME_PAGE_URL = "https://www.work.ua";
    public static final String CHROME_DRIVER_PATH = "src/main/resources/chromedriver.exe";
    public static final String EXPERIENCE_LEVELS_JSON = "/json/ExperienceLevels.json";
    public static final String PROGRAMMING_LANGUAGE_JSON = "/json/ProgrammingLanguages.json";
  }

  @UtilityClass
  public class Validation {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(Validation.EMAIL_REGEX);
  }

  @UtilityClass
  public class ConfigConstants {
    public static final Integer THREAD_POOL_SIZE = 3;
    public static final String CHROME_DRIVER_NAME = "webdriver.chrome.driver";
    public static final String[] HTTP_METHODS = {"POST", "GET"};
  }

  @UtilityClass
  public class ErrorMessageConstants {
    public static final String NO_DESCRIPTION_FOUND_MESSAGE =
        "Unfortunately, no description was found for this vacancy.";
    public static final String SOURCE_NOT_FOUND_MESSAGE = "Source with id %d not found";
    public static final String SUBSCRIBER_ALREADY_EXITS_MESSAGE =
        "The subscriber already exists with email: %s";
  }

  @UtilityClass
  public class ExperienceLevelConstants {
    public static final String SENIOR = "Senior";
    public static final String MIDDLE = "Middle";
    public static final String JUNIOR = "Junior";
    public static final String DEFAULT_EXPERIENCE_LEVEL = "Others";
  }
}
