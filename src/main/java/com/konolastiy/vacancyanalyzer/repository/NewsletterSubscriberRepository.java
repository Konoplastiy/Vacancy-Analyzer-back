package com.konolastiy.vacancyanalyzer.repository;

import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, Long> {
    Optional<Object> findByEmail(String email);
}