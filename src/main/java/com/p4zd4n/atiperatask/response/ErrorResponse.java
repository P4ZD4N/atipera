package com.p4zd4n.atiperatask.response;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        String message
) {}
