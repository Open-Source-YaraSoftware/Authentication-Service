package com.workshopngine.platform.authenticationmanagement.authentication.application.internal.commandservices;

import com.workshopngine.platform.authenticationmanagement.authentication.application.internal.outboundservices.acl.ExternalKeycloakService;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.SignUpCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.services.UserCommandService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final ExternalKeycloakService externalKeycloakService;

    @Override
    public void handle(SignUpCommand command) {
        var newUser = new User(command);
        externalKeycloakService.createUser(newUser);
    }
}
