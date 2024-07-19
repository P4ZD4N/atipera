package com.p4zd4n.atiperatask.service;

import com.p4zd4n.atiperatask.exception.UserNotFoundException;
import com.p4zd4n.atiperatask.model.Branch;
import com.p4zd4n.atiperatask.model.LastCommit;
import com.p4zd4n.atiperatask.model.Owner;
import com.p4zd4n.atiperatask.model.Repository;
import com.p4zd4n.atiperatask.response.RepositoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GitHubServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubService gitHubService;

    private final String username = "testUser";
    private final Owner owner = new Owner(username);

    @Test
    void getRepositories_userExists_returnsRepositories() {

        Branch[] branches = {
                new Branch("main", new LastCommit("sha1")),
                new Branch("develop", new LastCommit("sha2"))
        };

        Repository[] repositories = {
                Repository.builder().name("repo1").owner(owner).branches(Arrays.asList(branches)).build(),
                Repository.builder().name("repo2").owner(owner).branches(Arrays.asList(branches)).build()
        };

        when(restTemplate.getForObject(anyString(), eq(Repository[].class))).thenReturn(repositories);
        when(restTemplate.getForObject(anyString(), eq(Branch[].class))).thenReturn(branches);
        when(restTemplate.getForObject(anyString(), eq(LastCommit.class))).thenReturn(new LastCommit("sha1"));

        List<RepositoryResponse> repositoryResponses = gitHubService.getRepositories(username);

        assertNotNull(repositoryResponses);
        assertEquals(2, repositoryResponses.size());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Repository[].class));
    }

    @Test
    void getRepositories_userDoesNotExist_throwsUserNotFoundException() {

        when(restTemplate.getForObject(anyString(), eq(Repository[].class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(UserNotFoundException.class, () -> gitHubService.getRepositories(username));
    }

    @Test
    void getRepositories_emptyRepositories_returnsEmptyList() {


        when(restTemplate.getForObject(anyString(), eq(Repository[].class)))
                .thenReturn(new Repository[0]);

        List<RepositoryResponse> repositoryResponses = gitHubService.getRepositories(username);

        assertNotNull(repositoryResponses);
        assertTrue(repositoryResponses.isEmpty());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Repository[].class));
    }

    @Test
    void getRepositories_withDifferentBranchNames_returnsCorrectBranches() {

        Branch[] branches = {
                new Branch("main", new LastCommit("sha5")),
                new Branch("feature-branch", new LastCommit("sha7"))
        };

        Repository[] repositories = {
                Repository.builder().name("repo1").owner(owner).build()
        };

        when(restTemplate.getForObject(anyString(), eq(Repository[].class))).thenReturn(repositories);
        when(restTemplate.getForObject(anyString(), eq(Branch[].class))).thenReturn(branches);
        when(restTemplate.getForObject(anyString(), eq(LastCommit.class))).thenReturn(new LastCommit("sha5"));

        List<RepositoryResponse> repositoryResponses = gitHubService.getRepositories(username);

        assertNotNull(repositoryResponses);
        assertEquals(1, repositoryResponses.size());
        assertEquals(2, repositoryResponses.getFirst().branches().size());
        assertEquals("main", repositoryResponses.getFirst().branches().getFirst().name());
        assertEquals("feature-branch", repositoryResponses.getFirst().branches().get(1).name());
    }
}