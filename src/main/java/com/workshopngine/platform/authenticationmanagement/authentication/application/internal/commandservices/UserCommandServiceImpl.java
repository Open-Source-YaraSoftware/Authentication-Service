package com.workshopngine.platform.authenticationmanagement.authentication.application.internal.commandservices;

import com.workshopngine.platform.authenticationmanagement.authentication.application.internal.outboundservices.acl.ExternalKeycloakService;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.*;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.entities.Role;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.services.UserCommandService;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
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

    @Override
    public void handle(AssignRoleCommand command) {
        var role = new Role(command.roleName());
        externalKeycloakService.assignRoleToUser(command.userId(), role.getName());
    }

    @Override
    public void handle(ForgotPasswordCommand command) {
        externalKeycloakService.forgotPassword(command.email());
    }

    @Override
    public Triple<User, String, String> handle(SignInCommand command) {
        return externalKeycloakService.signIn(command.email(), command.password());
    }

    @Override
    public ImmutablePair<String, String> handle(RefreshTokenCommand command) {
        return externalKeycloakService.refreshToken(command.refreshToken());
    }
}
