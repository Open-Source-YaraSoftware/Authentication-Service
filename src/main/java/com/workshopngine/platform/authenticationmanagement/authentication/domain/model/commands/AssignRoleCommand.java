package com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands;

public record AssignRoleCommand(
        String userId,
        String roleName
) {
}