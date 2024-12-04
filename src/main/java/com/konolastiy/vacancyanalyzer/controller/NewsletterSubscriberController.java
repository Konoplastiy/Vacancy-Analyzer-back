package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.common.mapper.NewsletterSubscriberMapper;
import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import com.konolastiy.vacancyanalyzer.payload.newslettersubscriber.NewsletterSubscriberDto;
import com.konolastiy.vacancyanalyzer.service.NewsletterSubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/v1/subscribers")
@RequiredArgsConstructor
public class NewsletterSubscriberController {

  private final NewsletterSubscriberService newsletterSubscriberService;
  private final NewsletterSubscriberMapper newsletterSubscriberMapper;

  @Operation(summary = "Creates a new blog")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request, possibly already subscribed email"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping
  public ResponseEntity<NewsletterSubscriberDto> createNewsletterSubscriber(
      @RequestBody @Valid final NewsletterSubscriberDto subscriberDto) {
    NewsletterSubscriber subscriber = newsletterSubscriberMapper.subscriberFromDto(subscriberDto);
    NewsletterSubscriber savedSubscriber =
        newsletterSubscriberService.saveNewsletterSubscriber(subscriber);

    log.info("New subscriber with email: {} was created", savedSubscriber.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(subscriberDto);
  }
}
