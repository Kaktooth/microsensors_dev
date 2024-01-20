package com.projects.microsensors.configuration.handler;

import com.projects.microsensors.configuration.MessageProperties;
import com.projects.microsensors.exception.NotFoundException;
import com.projects.microsensors.model.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageProperties messageProperties;

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorMessage> handleAuthenticationException(
            @NonNull final HttpServletRequest request,
            @NonNull final Exception exception) {
        log.error("Exception was thrown due authentication:", exception);
        final var message = ErrorMessage.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .date(new Date())
                .description(messageProperties.getUnauthorizedErrorMessage())
                .url(request.getRequestURL().toString())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(
            @NonNull final HttpServletRequest request,
            @NonNull final NotFoundException exception) {
        log.error("Exception was thrown because resource was not found:", exception);
        final var message = ErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .date(new Date())
                .description(messageProperties.getNotFoundErrorMessage())
                .url(request.getRequestURL().toString())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(
            @NonNull final HttpServletRequest request,
            @NonNull final Exception exception) {
        log.error("Exception was thrown because passed data was not valid:", exception);
        final var message = ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .date(new Date())
                .description(messageProperties.getBadRequestErrorMessage())
                .url(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(
            @NonNull final HttpServletRequest request,
            @NonNull final AccessDeniedException exception) {
        log.error("Exception was thrown because access was denied:", exception);
        final var message = ErrorMessage.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .date(new Date())
                .description(messageProperties.getAccessDeniedErrorMessage())
                .url(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleAllExceptions(
            @NonNull final Exception exception,
            @NonNull final HttpServletRequest request) {
        log.error("Exception was thrown due unknown exception:", exception);
        final var responseStatus =
                exception.getClass().getAnnotation(ResponseStatus.class);
        final var status =
                responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        final var message = ErrorMessage.builder()
                .status(status.value())
                .date(new Date())
                .description(messageProperties.getInternalServerErrorMessage())
                .url(request.getRequestURL().toString())
                .build();
        return new ResponseEntity<>(message, status);
    }
}
