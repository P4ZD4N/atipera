package com.p4zd4n.atiperatask.service;

import com.p4zd4n.atiperatask.exception.UserNotFoundException;
import com.p4zd4n.atiperatask.model.Branch;
import com.p4zd4n.atiperatask.model.LastCommit;
import com.p4zd4n.atiperatask.model.Repository;
import com.p4zd4n.atiperatask.response.RepositoryResponse;
import com.p4zd4n.atiperatask.util.URICreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final String apiUrl;

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(GitHubService.class);

    public GitHubService(
            RestTemplate restTemplate,
            @Value("${github.api.url}") String apiUrl
    ) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public List<RepositoryResponse> getRepositories(String username) {

        String path = "/users/" + username + "/repos?type=all";
        URI uri = URICreator.createURI(apiUrl, path);

        try {
            Repository[] repositories = restTemplate.getForObject(uri, Repository[].class);

            logger.info("Fetching repositories for user: {}", username);

            return Arrays.stream(repositories)
                    .filter(repository -> !repository.isForked())
                    .map(repository -> {
                        List<Branch> branches = getRepositoryBranches(username, repository.name());
                        return new RepositoryResponse(repository.name(), repository.owner().login(), branches);
                    })
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException(username);
        }
    }

    private List<Branch> getRepositoryBranches(String username, String repository) {

        String path = "/repos/" + username + "/" + repository + "/branches";
        URI uri = URICreator.createURI(apiUrl, path);

        Branch[] branches = restTemplate.getForObject(uri, Branch[].class);

        return Arrays.stream(branches)
                .map(branch -> {
                    String commitUriPath = "/repos/" + username + "/" + repository + "/commits/" + branch.name();
                    URI commitUri = URICreator.createURI(apiUrl, commitUriPath);
                    LastCommit lastCommit = restTemplate.getForObject(commitUri, LastCommit.class);

                    logger.info("Fetching branches for repository: {}", repository);

                    return new Branch(branch.name(), lastCommit);
                })
                .collect(Collectors.toList());
    }
}
