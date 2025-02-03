package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto;

public record CreateForgotPasswordResource(
    String email
) {
    public CreateForgotPasswordResource {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }
}
