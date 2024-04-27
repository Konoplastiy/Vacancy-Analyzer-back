package com.konolastiy.vacancyanalyzer.config.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Objects;

import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.ConfigConstants.HTTP_METHODS;
import static com.konolastiy.vacancyanalyzer.common.ApplicationConstants.UrlConstants.FRONT_URL;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration.setAllowedOrigins(Arrays.asList(Objects.requireNonNull(FRONT_URL)));
        configuration.setAllowedMethods(Arrays.asList(HTTP_METHODS));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}