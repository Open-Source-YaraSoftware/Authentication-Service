package com.workshopngine.platform.authenticationmanagement.authentication.infrastructure.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleOAuth2User {
    private String id;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
    private String idToken;
    private String issuedAt;
    private String expiresAt;
    private String provider;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
