package com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.queries.GetUserByIdQuery;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.services.UserCommandService;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.services.UserQueryService;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.CreateAssignRoleResource;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.dto.UserResource;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform.AssignRoleCommandFromResourceAssembler;
import com.workshopngine.platform.authenticationmanagement.authentication.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "User Endpoints")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @GetMapping
    @Operation(summary = "Get a user by its token", description = "Get a user by its token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> getUserByToken(Principal principal) {
        var query = new GetUserByIdQuery(principal.getName());
        var user = userQueryService.handle(query);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get a user by id", description = "Get a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> getUserById(@PathVariable String userId) {
        var query = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(query);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    @PostMapping({"/{userId}/roles"})
    @Operation(summary = "Assign a role to a user", description = "Assign a role to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role assigned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> assignRoleToUser(@PathVariable String userId, @RequestBody CreateAssignRoleResource roleName) {
        var command = AssignRoleCommandFromResourceAssembler.toCommandFromResource(userId, roleName);
        userCommandService.handle(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
