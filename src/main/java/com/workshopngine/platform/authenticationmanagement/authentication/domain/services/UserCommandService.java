package com.workshopngine.platform.authenticationmanagement.authentication.domain.services;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.AssignRoleCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.ForgotPasswordCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.SignInCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.Triple;

public interface UserCommandService {
    void handle(SignUpCommand command);
    void handle(AssignRoleCommand command);
    void handle(ForgotPasswordCommand command);
    Triple<User, String, String> handle(SignInCommand command);
}
