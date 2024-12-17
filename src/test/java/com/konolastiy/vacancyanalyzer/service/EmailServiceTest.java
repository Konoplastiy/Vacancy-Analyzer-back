package com.konolastiy.vacancyanalyzer.service;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.TestConstants.content;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.TestConstants.subject;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.TestConstants.to;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock private JavaMailSender mailSender;
  @InjectMocks private EmailService emailService;

  @Test
  void test_toSendSuccessful() {
    doNothing().when(mailSender).send(any(SimpleMailMessage.class));

    emailService.send(to, subject, content);

    verify(mailSender).send(any(SimpleMailMessage.class));
  }
}
