package com.p4zd4n.atiperatask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Value("${github.auth.token}")
    private String authToken;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateHeaderModifier(authToken)));
        return restTemplate;
    }

    public static class RestTemplateHeaderModifier implements ClientHttpRequestInterceptor {

        private final String authToken;

        public RestTemplateHeaderModifier(String authToken) {
            this.authToken = authToken;
        }

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution
        ) throws IOException {
            if (authToken != null && !authToken.isEmpty()) {
                request.getHeaders().set("Authorization", "Bearer " + authToken);
            }
            return execution.execute(request, body);
        }
    }
}