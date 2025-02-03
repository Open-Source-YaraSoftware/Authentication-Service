package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.SignInCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }
}
