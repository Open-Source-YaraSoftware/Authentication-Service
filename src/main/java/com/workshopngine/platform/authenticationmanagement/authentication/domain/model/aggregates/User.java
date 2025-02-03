package com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.SignUpCommand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String id;

    private String username;

    private String email;

    private String password;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(SignUpCommand command) {
        this.username = command.username();
        this.email = command.email();
        this.password = command.password();
    }
}
