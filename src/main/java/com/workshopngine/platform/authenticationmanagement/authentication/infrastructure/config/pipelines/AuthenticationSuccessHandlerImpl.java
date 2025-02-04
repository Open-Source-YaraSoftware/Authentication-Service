package com.workshopngine.platform.authenticationmanagement.authentication.infrastructure.config.pipelines;

import com.workshopngine.platform.authenticationmanagement.authentication.application.internal.outboundservices.acl.ExternalKeycloakService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final ExternalKeycloakService externalKeycloakService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String accessToken = extractAccessToken(authentication);
        if (accessToken == null) {
            sendErrorResponse(response);
            return;
        }
        System.out.println("accessToken: " + accessToken);
        ImmutablePair<String, String> tokens = externalKeycloakService.signInWithGoogle(accessToken);
        sendSuccessResponse(response, tokens);
    }

    private String extractAccessToken(Authentication authentication) {
        String clientRegistrationId = getClientRegistrationId(authentication);
        if (clientRegistrationId == null) return null;
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, authentication.getName());
        if (authorizedClient == null || authorizedClient.getAccessToken() == null) return null;
        return authorizedClient.getAccessToken().getTokenValue();
    }

    private String getClientRegistrationId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof OidcUser) {
            return "google";
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            return "github";
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\":\"Username or access token not found.\"}");
    }

    private void sendSuccessResponse(HttpServletResponse response, ImmutablePair<String, String> tokens) throws IOException {
        response.setContentType("application/json");
        String jsonResponse = String.format(
                "{\"token\":\"%s\"" +
                ",\"refreshToken\":\"%s\"}",
                tokens.left,
                tokens.right);
        response.getWriter().write(jsonResponse);
    }
}
