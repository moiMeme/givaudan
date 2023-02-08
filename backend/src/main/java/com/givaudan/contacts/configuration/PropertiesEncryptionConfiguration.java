package com.givaudan.contacts.configuration;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesEncryptionConfiguration {

    public static final String JASYPT_BEAN = "jasyptStringEncryptor";

    @Value("${contacts.properties-encryptor.password:}")
    private String password;
    @Value("${contacts.properties-encryptor.algorithm:}")
    private String algorithm;
    @Value("${contacts.properties-encryptor.iv-generator-classname:}")
    private String ivGeneratorClassName;

    @Bean(JASYPT_BEAN)
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPoolSize(1);
        config.setPassword(prepareEncryptionPassword());
        if (StringUtils.isNotBlank(algorithm)) {
            config.setAlgorithm(algorithm);
        }
        if (StringUtils.isNotBlank(ivGeneratorClassName)) {
            config.setIvGeneratorClassName(ivGeneratorClassName);
        }
        encryptor.setConfig(config);
        return encryptor;
    }


    private String prepareEncryptionPassword() {
        if (StringUtils.isNotBlank(password)) {
            return password;
        }
        return "ne1FsZog8Vdgq5+TE6cyXtEtN6164k3bqDWCbEWR9MG2Cew=";
    }
}

