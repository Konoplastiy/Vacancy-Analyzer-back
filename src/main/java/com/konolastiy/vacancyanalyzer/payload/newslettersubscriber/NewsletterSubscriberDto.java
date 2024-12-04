package com.konolastiy.vacancyanalyzer.payload.newslettersubscriber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konolastiy.vacancyanalyzer.common.validator.email.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(
    name = "NewsletterSubscriberResponse",
    description = "Schema to hold NewsletterSubscriber response information")
public class NewsletterSubscriberDto {

  @NotBlank
  @ValidEmail
  @Schema(description = "Email address of the subscriber", example = "example@example.com")
  private String email;
}
