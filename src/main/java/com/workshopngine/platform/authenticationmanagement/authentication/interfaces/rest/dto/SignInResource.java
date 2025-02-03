package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto;

public record SignInResource(
        String email,
        String password
) {
    public SignInResource {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}
