package com.projects.microsensors.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:message.properties")
@Getter
public class MessageProperties {

    @Value("${internal-server-error}")
    private String internalServerErrorMessage;

    @Value("${unauthorized-error}")
    private String unauthorizedErrorMessage;

    @Value("${bad-request-error}")
    private String badRequestErrorMessage;

    @Value("${access-denied-error}")
    private String accessDeniedErrorMessage;

    @Value("${not-found-error}")
    private String notFoundErrorMessage;
}