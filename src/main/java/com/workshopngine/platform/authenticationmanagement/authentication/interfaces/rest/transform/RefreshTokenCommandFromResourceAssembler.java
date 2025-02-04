package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.commands.RefreshTokenCommand;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.CreateRefreshTokenResource;

public class RefreshTokenCommandFromResourceAssembler {
    public static RefreshTokenCommand toCommandFromResource(CreateRefreshTokenResource resource){
        return new RefreshTokenCommand(resource.refreshToken());
    }
}
