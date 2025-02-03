package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto;

public record AuthenticatedUserResource(
        String id,
        String username,
        String email,
        String token,
        String refreshToken
) {
}
