package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto;

public record UserResource(
    String id,
    String username,
    String email
) {
}
