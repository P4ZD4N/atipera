package com.p4zd4n.atiperatask.controller;

import com.p4zd4n.atiperatask.exception.NoRepositoriesFoundException;
import com.p4zd4n.atiperatask.response.RepositoryResponse;
import com.p4zd4n.atiperatask.service.GitHubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GitHubControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GitHubService gitHubService;

    @InjectMocks
    private GitHubController gitHubController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(gitHubController).build();
    }

    @Test
    void getRepositories_success_returnsRepositories() throws Exception {

        RepositoryResponse repo1 = new RepositoryResponse("repo1", "testUser", Collections.emptyList());
        RepositoryResponse repo2 = new RepositoryResponse("repo2", "testUser", Collections.emptyList());
        List<RepositoryResponse> responses = List.of(repo1, repo2);

        when(gitHubService.getRepositories(anyString())).thenReturn(responses);

        mockMvc.perform(get("/api/github/repositories/testUser")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].repo_name").value("repo1"))
                .andExpect(jsonPath("$[1].repo_name").value("repo2"));

        verify(gitHubService, times(1)).getRepositories("testUser");
    }

    @Test
    void getRepositories_noRepositories_throwsNoRepositoriesFoundException() {

        when(gitHubService.getRepositories("testUser")).thenReturn(Collections.emptyList());

        assertThrows(NoRepositoriesFoundException.class, () ->
                gitHubController.getRepositories("testUser", MediaType.APPLICATION_JSON_VALUE)
        );
    }

    @Test
    void getRepositories_invalidMediaType_throwsHttpMediaTypeNotSupportedException() throws Exception {

        mockMvc.perform(get("/api/github/repositories/testUser")
                        .header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(result -> {
                    assertInstanceOf(HttpMediaTypeNotSupportedException.class, result.getResolvedException());
                    assertEquals("Only application/json media type is supported!", result.getResolvedException().getMessage());
                });
    }

    @Test
    void getRepositories_missingAcceptHeader_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/github/repositories/testUser"))
                .andExpect(status().isBadRequest());
    }
}
