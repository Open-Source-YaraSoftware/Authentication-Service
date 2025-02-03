package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token, String refreshToken) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), user.getEmail(), token, refreshToken);
    }
}
