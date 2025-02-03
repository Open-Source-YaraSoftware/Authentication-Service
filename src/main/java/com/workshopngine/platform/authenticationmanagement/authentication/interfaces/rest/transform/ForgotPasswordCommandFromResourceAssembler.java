package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.ForgotPasswordCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.CreateForgotPasswordResource;

public class ForgotPasswordCommandFromResourceAssembler {
    public static ForgotPasswordCommand toCommandFromResource(CreateForgotPasswordResource resource) {
        return new ForgotPasswordCommand(resource.email());
    }
}
