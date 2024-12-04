package com.konolastiy.vacancyanalyzer.common.validator.email;

import com.konolastiy.vacancyanalyzer.common.ApplicationConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import org.springframework.lang.NonNull;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  @Override
  public boolean isValid(@NonNull String email, @NonNull ConstraintValidatorContext context) {
    return validateEmail(email);
  }

  private boolean validateEmail(@NonNull final String email) {
    final Matcher matcher = ApplicationConstants.Validation.EMAIL_PATTERN.matcher(email);
    return matcher.matches();
  }
}
