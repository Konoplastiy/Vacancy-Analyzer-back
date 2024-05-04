package com.konolastiy.vacancyanalyzer.common.mapper;

import com.konolastiy.vacancyanalyzer.entity.NewsletterSubscriber;
import com.konolastiy.vacancyanalyzer.payload.newslettersubscriber.NewsletterSubscriberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsletterSubscriberMapper {

    NewsletterSubscriber subscriberFromDto(NewsletterSubscriberDto subscriberDto);

    NewsletterSubscriberDto subscriberToDto(NewsletterSubscriber subscriber);
}