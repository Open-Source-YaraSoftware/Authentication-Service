package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto;

public record CreateRefreshTokenResource(
        String refreshToken
) {
    public CreateRefreshTokenResource {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Invalid refreshToken");
        }
    }
}
