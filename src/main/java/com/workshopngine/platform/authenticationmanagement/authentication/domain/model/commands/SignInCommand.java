package com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands;

public record SignInCommand(
        String email,
        String password
) {
}
