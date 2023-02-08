package com.givaudan.contacts.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.support.HttpHeaders;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ElasticsearchConfigurations extends ElasticsearchConfiguration {

    private final ContactsProperties contactsProperties;

    @Override
    public ClientConfiguration clientConfiguration() {

        HttpHeaders compatibilityHeaders = new HttpHeaders();
        compatibilityHeaders.add("Accept", "application/vnd.elasticsearch+json");
        compatibilityHeaders.add("Content-Type", "application/vnd.elasticsearch+json");

        return ClientConfiguration.builder()
                .connectedTo(contactsProperties.getElasticsearch().getHostAndPort())
                .withConnectTimeout(Duration.ofSeconds(contactsProperties.getElasticsearch().getConnectTimeout()))
                .withSocketTimeout(Duration.ofSeconds(contactsProperties.getElasticsearch().getSocketTimeout()))
                .withDefaultHeaders(compatibilityHeaders)
                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    return headers;
                })
                .build();
    }

    @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(
                Arrays.asList(
                        new ZonedDateTimeWritingConverter(),
                        new ZonedDateTimeReadingConverter(),
                        new InstantWritingConverter(),
                        new InstantReadingConverter(),
                        new LocalDateWritingConverter(),
                        new LocalDateReadingConverter()
                )
        );
    }

    @WritingConverter
    static class ZonedDateTimeWritingConverter implements Converter<ZonedDateTime, String> {

        @Override
        public String convert(ZonedDateTime source) {
            return Optional.ofNullable(source).map(s -> s.toInstant().toString()).orElse(null);
        }
    }

    @ReadingConverter
    static class ZonedDateTimeReadingConverter implements Converter<String, ZonedDateTime> {

        @Override
        public ZonedDateTime convert(String source) {
            return Optional.ofNullable(source).map(s -> Instant.parse(s).atZone(ZoneId.systemDefault())).orElse(null);
        }
    }

    @WritingConverter
    static class InstantWritingConverter implements Converter<Instant, String> {
        @Override
        public String convert(Instant source) {
            return Optional.ofNullable(source).map(Instant::toString).orElse(null);
        }
    }

    @ReadingConverter
    static class InstantReadingConverter implements Converter<String, Instant> {

        @Override
        public Instant convert(String source) {
            return Optional.ofNullable(source).map(Instant::parse).orElse(null);
        }
    }

    @WritingConverter
    static class LocalDateWritingConverter implements Converter<LocalDate, String> {
        @Override
        public String convert(LocalDate source) {
            return Optional.ofNullable(source).map(LocalDate::toString).orElse(null);
        }
    }

    @ReadingConverter
    static class LocalDateReadingConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            return Optional.ofNullable(source).map(LocalDate::parse).orElse(null);
        }
    }

}
