package com.konolastiy.vacancyanalyzer.common.validator.email;

import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.validation.ConstraintValidatorContext;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmailValidatorTest {

  @Mock private ConstraintValidatorContext context;

  @InjectMocks private EmailValidator emailValidator;

  @ParameterizedTest(name = "#{index} - Run test with email = {0}")
  @MethodSource("validEmailProvider")
  public void test_isValidEmailWhenRegexValid(String email) {
    Assertions.assertTrue(emailValidator.isValid(email, context));
  }

  @ParameterizedTest(name = "#{index} - Run test with email = {0}")
  @MethodSource("invalidEmailProvider")
  public void test_isValidEmailWhenRegexInvalid(String email) {
    assertFalse(emailValidator.isValid(email, context));
  }

  static Stream<String> validEmailProvider() {
    return Stream.of(
        "firstlast@gmail.com", // test default email
        "123456789012345678901234@iana.org", // test only numbers
        "$A12345@iana.org", // test start with symbols
        "!def!xyz%abc@example.com", // test start with symbols
        "first.last@iana.org", // test dot in the middle of the name
        "test+test@iana.org" // test +
        );
  }

  static Stream<String> invalidEmailProvider() {
    return Stream.of(
        "first.last", // invalid, only name and dot
        "doug@", // invalid, only name and @
        "@iana.org", // invalid, start with @
        "hello world@iana.org", // invalid, space
        "test@[123.123.123.123", // invalid, at least one digit
        "phil.h\\@\\@ck@haacked.com", // invalid, add \\ and two @
        " ", // invalid, empty
        "cal(foo(bar)@iamcal.com"); // invalid, brackets
  }
}
