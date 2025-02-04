package com.workshopngine.platform.authenticationmanagement.authentication.application.internal.outboundservices.acl;

import com.workshopngine.platform.authenticationmanagement.authentication.infrastructure.config.model.GranTypes;
import com.workshopngine.platform.authenticationmanagement.authentication.infrastructure.config.model.KeycloakTokenResponse;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalKeycloakTokenService {
    private final RestTemplate restTemplate;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public KeycloakTokenResponse refreshToken(String refreshToken) {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", GranTypes.REFRESH_TOKEN);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<KeycloakTokenResponse> response = restTemplate.postForEntity(tokenUrl, request, KeycloakTokenResponse.class);

        return response.getBody();
    }

    public AccessTokenResponse getAccessToken(String username, String password) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build();
        return keycloak.tokenManager().getAccessToken();
    }

    public KeycloakTokenResponse getTokensBySigningInWithGoogle(String subjectToken) {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", GranTypes.GOOGLE_SUBJECT_TOKEN);
        formData.add("subject_token_type", GranTypes.GOOGLE_SUBJECT_TOKEN_TYPE);
        formData.add("subject_token", subjectToken);
        formData.add("subject_issuer", GranTypes.GOOGLE_SUBJECT_ISSUER);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<KeycloakTokenResponse> response = restTemplate.postForEntity(tokenUrl, request, KeycloakTokenResponse.class);

        return response.getBody();
    }
}
