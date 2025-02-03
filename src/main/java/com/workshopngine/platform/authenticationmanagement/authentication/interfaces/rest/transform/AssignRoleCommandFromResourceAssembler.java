package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.AssignRoleCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.CreateAssignRoleResource;

public class AssignRoleCommandFromResourceAssembler {
    public static AssignRoleCommand toCommandFromResource(String userId, CreateAssignRoleResource resource) {
        return new AssignRoleCommand(userId, resource.role());
    }
}
