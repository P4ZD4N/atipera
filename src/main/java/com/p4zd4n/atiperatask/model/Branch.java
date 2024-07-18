package com.p4zd4n.atiperatask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Branch(
        String name,
        @JsonProperty("last_commit") LastCommit lastCommit
) {}
