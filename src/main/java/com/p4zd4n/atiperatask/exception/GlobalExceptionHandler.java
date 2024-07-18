package com.p4zd4n.atiperatask.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(
            UserNotFoundException exception
    ) {
        logger.warn("Handling UserNotFoundException: {}", exception.getMessage());

        Map<String, Object> response = Map.of(
                "status", HttpStatus.NOT_FOUND.value(),
                "message", exception.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception
    ) {
        logger.warn("Handling HttpMediaTypeNotSupportedException: {}", exception.getMessage());

        Map<String, Object> response = Map.of(
                "status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                "message", exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .header("Content-Type", "application/json")
                .body(response);
    }

    @ExceptionHandler(NoRepositoriesFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoRepositoriesFoundException(
            NoRepositoriesFoundException exception
    ) {
        logger.warn("Handling NoRepositoriesFoundException: {}", exception.getMessage());

        Map<String, Object> response = Map.of(
                "status", HttpStatus.NOT_FOUND.value(),
                "message", exception.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
