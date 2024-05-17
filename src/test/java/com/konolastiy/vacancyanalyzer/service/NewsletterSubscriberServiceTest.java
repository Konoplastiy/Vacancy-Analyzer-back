package com.konolastiy.vacancyanalyzer.service;

import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import com.konolastiy.vacancyanalyzer.repository.NewsletterSubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.NewsletterSubscriberControllerTest.TEST_EMAIL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewsletterSubscriberServiceTest {

    @Mock
    private NewsletterSubscriberRepository newsletterSubscriberRepository;

    @InjectMocks
    private NewsletterSubscriberService newsletterSubscriberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenSaveNewsletterSubscriber_WithNewEmail_ShouldSaveSubscriber() {
        NewsletterSubscriber subscriber = new NewsletterSubscriber();
        subscriber.setEmail(TEST_EMAIL);

        when(newsletterSubscriberRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(newsletterSubscriberRepository.save(subscriber)).thenReturn(subscriber);

        NewsletterSubscriber savedSubscriber = newsletterSubscriberService.saveNewsletterSubscriber(subscriber);

        assertNotNull(savedSubscriber);
        verify(newsletterSubscriberRepository).save(subscriber);
    }
}