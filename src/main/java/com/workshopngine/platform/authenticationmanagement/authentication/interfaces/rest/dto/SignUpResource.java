package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto;

public record SignUpResource(
        String username,
        String password,
        String email
) {
    public SignUpResource {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}