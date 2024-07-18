package com.p4zd4n.atiperatask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LastCommit(
        @JsonProperty("sha") String sha
) {}
