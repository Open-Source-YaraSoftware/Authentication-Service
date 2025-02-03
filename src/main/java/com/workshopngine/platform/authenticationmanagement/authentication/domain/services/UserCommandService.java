package com.workshopngine.platform.authenticationmanagement.authentication.domain.services;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.SignUpCommand;

public interface UserCommandService {
    void handle(SignUpCommand command);
}
