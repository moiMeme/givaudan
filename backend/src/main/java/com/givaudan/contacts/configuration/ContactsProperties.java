package com.givaudan.contacts.configuration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Setter
@Getter
@Validated
@ConfigurationProperties(prefix = "contacts")
public class ContactsProperties {
    @Valid
    @NotNull
    private Security security;
    private Cache cache;
    private Elasticsearch elasticsearch;

    @Getter
    @Setter
    @Valid
    public static class Security {
        @NotNull
        private Jwt jwt;

    }
    @Getter
    @Setter
    @Valid
    public static class Jwt {
        @NotBlank
        private String secret;
        private Long tokenValidityInSeconds = 86400L;
        private Long tokenValidityInSecondsForRememberMe = 2592000L;
    }

    @Getter
    @Setter
    public static class Cache {
        private Long timeToLiveSeconds = 3600L;
        private Integer maxEntries = 100;
    }
    @Getter
    @Setter
    @Valid
    public static class Elasticsearch {
        @NotBlank
        private String hostAndPort;
        private Integer connectTimeout = 5;
        private Integer socketTimeout = 3;
    }
}
