package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto;

public record RefreshTokenResource(
        String refreshToken,
        String token
) {
}
