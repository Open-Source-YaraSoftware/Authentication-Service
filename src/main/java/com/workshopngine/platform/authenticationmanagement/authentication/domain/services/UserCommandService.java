package com.workshopngine.platform.authenticationmanagement.authentication.domain.services;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.*;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.lang3.tuple.ImmutablePair;

public interface UserCommandService {
    void handle(SignUpCommand command);
    void handle(AssignRoleCommand command);
    void handle(ForgotPasswordCommand command);
    Triple<User, String, String> handle(SignInCommand command);
    ImmutablePair<String, String> handle(RefreshTokenCommand command);
}
