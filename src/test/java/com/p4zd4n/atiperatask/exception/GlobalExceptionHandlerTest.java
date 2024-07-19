package com.p4zd4n.atiperatask.exception;

import com.p4zd4n.atiperatask.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleUserNotFoundException_shouldReturnNotFound() {

        String username = "testUser";

        UserNotFoundException exception = new UserNotFoundException(username);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleUserNotFoundException(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(responseEntity.getBody().message()).isEqualTo("User " + username +" not found!");
    }

    @Test
    void handleHttpMediaTypeNotSupportedException_shouldReturnUnsupportedMediaType() {

        HttpMediaTypeNotSupportedException exception = new HttpMediaTypeNotSupportedException("Only application/json media type is supported!");

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleHttpMediaTypeNotSupportedException(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        assertThat(responseEntity.getBody().status()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        assertThat(responseEntity.getBody().message()).isEqualTo("Only application/json media type is supported!");
        assertThat(responseEntity.getHeaders().getFirst("Content-Type")).isEqualTo("application/json");
    }

    @Test
    void handleNoRepositoriesFoundException_shouldReturnNotFound() {

        String username = "testUser";

        NoRepositoriesFoundException exception = new NoRepositoriesFoundException(username);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleNoRepositoriesFoundException(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody().status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(responseEntity.getBody().message()).isEqualTo("No repositories found for user " + username);
    }

    @Test
    void handleHttpClientErrorExceptionForbidden_shouldReturnForbidden() {

        HttpClientErrorException.Forbidden exception = (HttpClientErrorException.Forbidden) HttpClientErrorException.Forbidden.create(
                HttpStatus.FORBIDDEN, "", null, null, StandardCharsets.UTF_8);

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleHttpClientErrorException(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(responseEntity.getBody().status()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(responseEntity.getBody().message()).isEqualTo(
                "Rate limit exceeded! Generate token on " +
                "https://github.com/settings/tokens and fill " +
                "github.auth.token property with your token in application.properties file to increase limits!");
    }
}
