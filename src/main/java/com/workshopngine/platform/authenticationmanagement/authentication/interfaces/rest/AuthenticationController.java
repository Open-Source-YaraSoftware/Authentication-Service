package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.services.UserCommandService;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.*;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;

    @PostMapping("/sign-up")
    @Operation(summary = "Sign up", description = "Sign up a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpResource resource) {
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        userCommandService.handle(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in", description = "Sign in a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User signed in"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var authenticatedUser = userCommandService.handle(command);
        if (authenticatedUser == null) return ResponseEntity.notFound().build();
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.getLeft(), authenticatedUser.getMiddle(), authenticatedUser.getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Forgot password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> forgotPassword(@RequestBody CreateForgotPasswordResource resource) {
        var command = ForgotPasswordCommandFromResourceAssembler.toCommandFromResource(resource);
        userCommandService.handle(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token", description = "Refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<RefreshTokenResource> refreshToken(@RequestBody CreateRefreshTokenResource resource) {
        var command = RefreshTokenCommandFromResourceAssembler.toCommandFromResource(resource);
        var tokens = userCommandService.handle(command);
        if (tokens == null) return ResponseEntity.notFound().build();
        var refreshTokenResource = RefreshTokenResourceFromEntityAssembler.toResourceFromEntity(tokens.left, tokens.right);
        return new ResponseEntity<>(refreshTokenResource, HttpStatus.OK);
    }
}
