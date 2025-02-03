package com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands;

public record SignUpCommand(
    String username,
    String password,
    String email
) { }