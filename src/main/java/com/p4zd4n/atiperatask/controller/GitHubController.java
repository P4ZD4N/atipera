package com.p4zd4n.atiperatask.controller;

import com.p4zd4n.atiperatask.exception.GlobalExceptionHandler;
import com.p4zd4n.atiperatask.exception.NoRepositoriesFoundException;
import com.p4zd4n.atiperatask.response.RepositoryResponse;
import com.p4zd4n.atiperatask.service.GitHubService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private GitHubService gitHubService;
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @GetMapping("/repositories/{username}")
    public ResponseEntity<List<RepositoryResponse>> getRepositories(
            @PathVariable String username,
            @RequestHeader(HttpHeaders.ACCEPT) String accept
    ) throws HttpMediaTypeNotSupportedException {
        logger.info("Received request to get repositories for user: {}", username);

        if (!accept.equals(MediaType.APPLICATION_JSON_VALUE)) {
            logger.warn("Unsupported media type requested: {}", accept);
            throw new HttpMediaTypeNotSupportedException("Only application/json media type is supported!");
        }

        List<RepositoryResponse> repositoryResponses = gitHubService.getRepositories(username);

        if (repositoryResponses.isEmpty()) {
            logger.warn("No repositories found for user: {}", username);
            throw new NoRepositoriesFoundException(username);
        } else {
            logger.info("Returning {} repositories for user: {}", repositoryResponses.size(), username);
            return new ResponseEntity<>(repositoryResponses, HttpStatus.OK);
        }
    }
}
