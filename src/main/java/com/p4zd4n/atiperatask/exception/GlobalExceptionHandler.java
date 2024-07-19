package com.p4zd4n.atiperatask.exception;

import com.p4zd4n.atiperatask.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException exception
    ) {
        logger.warn("Handling UserNotFoundException: {}", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception
    ) {
        logger.warn("Handling HttpMediaTypeNotSupportedException: {}", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .header("Content-Type", "application/json")
                .body(response);
    }

    @ExceptionHandler(NoRepositoriesFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoRepositoriesFoundException(
            NoRepositoriesFoundException exception
    ) {
        logger.warn("Handling NoRepositoriesFoundException: {}", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(
            HttpClientErrorException.Forbidden exception
    ) {
        logger.warn("Handling HttpClientErrorException.Forbidden: {}", exception.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Rate limit exceeded! Generate token on https://github.com/settings/tokens and fill " +
                        "github.auth.token property with your token in application.properties file to increase limits!")
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
