package com.p4zd4n.atiperatask.service;

import com.p4zd4n.atiperatask.exception.UserNotFoundException;
import com.p4zd4n.atiperatask.model.Branch;
import com.p4zd4n.atiperatask.model.LastCommit;
import com.p4zd4n.atiperatask.model.Repository;
import com.p4zd4n.atiperatask.response.RepositoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GitHubService {

    private static final String API_URL = "https://api.github.com";

    private RestTemplate restTemplate;

    public List<RepositoryResponse> getRepositories(String username) {

        String url = API_URL + "/users/" + username + "/repos?type=all";

        try {
            Repository[] repositories = restTemplate.getForObject(url, Repository[].class);

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

        String url = API_URL + "/repos/" + username + "/" + repository + "/branches";

        Branch[] branches = restTemplate.getForObject(url, Branch[].class);

        return Arrays.stream(branches)
                .map(branch -> {
                    String commitUrl = API_URL + "/repos/" + username + "/" + repository + "/commits/" + branch.name();
                    LastCommit lastCommit = restTemplate.getForObject(commitUrl, LastCommit.class);
                    return new Branch(branch.name(), lastCommit);
                })
                .collect(Collectors.toList());
    }
}
