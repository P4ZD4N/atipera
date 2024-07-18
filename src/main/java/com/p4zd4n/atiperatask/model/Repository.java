package com.p4zd4n.atiperatask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record Repository(
        String name,
        Owner owner,
        List<Branch> branches,
        @JsonProperty("fork") boolean isForked
) {}
