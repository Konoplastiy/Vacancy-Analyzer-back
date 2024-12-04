package com.konolastiy.vacancyanalyzer.repository;

import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, Long> {
  Optional<NewsletterSubscriber> findByEmail(String email);
}
