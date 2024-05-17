package com.konolastiy.vacancyanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.EmailServiceTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class EmailServiceTest {

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenSendEmail_thenCorrectEmailSent() {
        emailService.send(TO, SUBJECT, CONTENT);
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}