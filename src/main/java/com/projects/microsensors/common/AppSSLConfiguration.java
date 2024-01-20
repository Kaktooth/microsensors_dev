package com.projects.microsensors.common;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AppSSLConfiguration {

    private static final String KEY_ALIAS = "SERVER_SSL_KEY-ALIAS";
    private static final String KEY_STORE = "SERVER_SSL_KEY-STORE";
    private static final String KEY_STORE_TYPE = "SERVER_SSL_KEY-STORE-TYPE";
    private static final String CERTIFICATE_PRIVATE_KEY = "SERVER_SSL_CERTIFICATE-PRIVATE-KEY";
    private static final String CERTIFICATE = "SERVER_SSL_CERTIFICATE";
    private static final String TRUST_CERTIFICATE = "SERVER_SSL_TRUST-CERTIFICATE";

    @Value("${server.ssl.key-alias}")
    private String keyAlias;

    @Value("${server.ssl.key-store}")
    private String keyStore;

    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;

    @PostConstruct
    public void logEnvAndConfiguration() {
        log.info("**** Env *****");
        log.info(System.getenv(KEY_ALIAS));
        log.info(System.getenv(KEY_STORE));
        log.info(System.getenv(CERTIFICATE_PRIVATE_KEY));
        log.info(System.getenv(KEY_STORE_TYPE));
        log.info(System.getenv(CERTIFICATE));
        log.info(System.getenv(TRUST_CERTIFICATE));

        log.info("**** Configuration *****");
        log.info(keyAlias);
        log.info(keyStore);
        log.info(keyStoreType);
    }
}
