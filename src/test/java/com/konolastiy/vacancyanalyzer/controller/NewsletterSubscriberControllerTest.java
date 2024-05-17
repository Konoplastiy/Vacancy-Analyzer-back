package com.konolastiy.vacancyanalyzer.controller;

import com.konolastiy.vacancyanalyzer.common.exception.SubscriberAlreadyExistsException;
import com.konolastiy.vacancyanalyzer.common.mapper.NewsletterSubscriberMapper;
import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import com.konolastiy.vacancyanalyzer.payload.newslettersubscriber.NewsletterSubscriberDto;
import com.konolastiy.vacancyanalyzer.service.NewsletterSubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.NewsletterSubscriberControllerTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewsletterSubscriberControllerTest {

    @Mock
    private NewsletterSubscriberService newsletterSubscriberService;

    @Mock
    private NewsletterSubscriberMapper newsletterSubscriberMapper;

    @InjectMocks
    private NewsletterSubscriberController newsletterSubscriberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewsletterSubscriber_WhenValidSubscriber_ReturnsCreatedStatus() {
        when(newsletterSubscriberMapper.subscriberFromDto(any(NewsletterSubscriberDto.class))).thenReturn(TEST_SUBSCRIBER);
        when(newsletterSubscriberService.saveNewsletterSubscriber(any(NewsletterSubscriber.class))).thenReturn(TEST_SUBSCRIBER);
        when(newsletterSubscriberMapper.subscriberToDto(any(NewsletterSubscriber.class))).thenReturn(TEST_SUBSCRIBER_DTO);
        ResponseEntity<NewsletterSubscriberDto> response = newsletterSubscriberController.createNewsletterSubscriber(TEST_SUBSCRIBER_DTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(TEST_SUBSCRIBER_DTO, response.getBody());
        verify(newsletterSubscriberService).saveNewsletterSubscriber(TEST_SUBSCRIBER);
        verify(newsletterSubscriberMapper).subscriberToDto(TEST_SUBSCRIBER);
    }

    @Test
    void createNewsletterSubscriber_WhenInvalidRequest_ReturnsBadRequest() {
        when(newsletterSubscriberMapper.subscriberFromDto(any(NewsletterSubscriberDto.class))).thenReturn(TEST_SUBSCRIBER);
        when(newsletterSubscriberService.saveNewsletterSubscriber(any(NewsletterSubscriber.class)))
                .thenThrow(new SubscriberAlreadyExistsException(ALREADY_ERROR_MES));

        SubscriberAlreadyExistsException exception = assertThrows(SubscriberAlreadyExistsException.class, () ->
                newsletterSubscriberController.createNewsletterSubscriber(TEST_SUBSCRIBER_DTO)
        );
        assertEquals(ALREADY_ERROR_MES, exception.getMessage());
        verify(newsletterSubscriberMapper).subscriberFromDto(TEST_SUBSCRIBER_DTO);
        verify(newsletterSubscriberService).saveNewsletterSubscriber(TEST_SUBSCRIBER);
    }
}