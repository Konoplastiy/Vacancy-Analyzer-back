package com.konolastiy.vacancyanalyzer.service;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ErrorMessageConstants.SUBSCRIBER_ALREADY_EXITS_MESSAGE;

import com.konolastiy.vacancyanalyzer.common.exception.SubscriberAlreadyExistsException;
import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import com.konolastiy.vacancyanalyzer.repository.NewsletterSubscriberRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsletterSubscriberService {

  private final NewsletterSubscriberRepository newsletterSubscriberRepository;

  @Transactional
  public NewsletterSubscriber saveNewsletterSubscriber(
      @NonNull final NewsletterSubscriber subscriber) {
    final String email = subscriber.getEmail();
    newsletterSubscriberRepository
        .findByEmail(email)
        .ifPresent(
            s -> {
              throw new SubscriberAlreadyExistsException(
                  String.format(SUBSCRIBER_ALREADY_EXITS_MESSAGE, email));
            });

    return newsletterSubscriberRepository.save(subscriber);
  }
}
