package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform;

import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.RefreshTokenResource;

public class RefreshTokenResourceFromEntityAssembler {
    public static RefreshTokenResource toResourceFromEntity(String refreshToken, String token){
        return new RefreshTokenResource(refreshToken, token);
    }
}
