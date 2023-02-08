package com.givaudan.contacts.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ContactsProperties.class)
@RequiredArgsConstructor
public class ContactsConfiguration {

}
