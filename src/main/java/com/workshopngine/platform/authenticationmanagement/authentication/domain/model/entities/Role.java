package com.workshopngine.platform.authenticationmanagement.authentication.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Role {
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
