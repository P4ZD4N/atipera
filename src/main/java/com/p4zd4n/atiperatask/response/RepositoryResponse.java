package com.p4zd4n.atiperatask.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.p4zd4n.atiperatask.model.Branch;
import lombok.Builder;

import java.util.List;

@Builder
public record RepositoryResponse(
        @JsonProperty("repo_name") String name,
        @JsonProperty("owner_login") String ownerLogin,
        @JsonProperty("branches") List<Branch> branches
) {}
