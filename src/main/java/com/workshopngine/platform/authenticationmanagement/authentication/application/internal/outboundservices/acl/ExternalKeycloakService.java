package com.workshopngine.platform.authenticationmanagement.authentication.application.internal.outboundservices.acl;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import com.workshopngine.platform.authenticationmanagement.authentication.infrastructure.config.model.ExecutionActions;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ExternalKeycloakService {
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public ExternalKeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void createUser(User user){
        UserRepresentation userRepresentation = mapUserToUserRepresentation(user);
        List<CredentialRepresentation> credentialRepresentation = mapUserToCredentialRepresentation(user);
        userRepresentation.setCredentials(credentialRepresentation);
        UsersResource usersResource = getUsersResource();
        try (Response response = usersResource.create(userRepresentation)) {
            if (!Objects.equals(response.getStatus(), 201)) { throw new IllegalArgumentException("Error creating user");}
            String locationHeader = response.getHeaderString("Location");
            String userId = extractUserIdFromLocation(locationHeader);
            sendEmailVerification(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating user", e);
        }
    }

    public User getUserById(String userId){
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();
        return new User(userRepresentation.getUsername(), userRepresentation.getEmail());
    }

    public void assignRoleToUser(String userId, String roleName){
        UserResource userResource = getUsersResource().get(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    public void forgotPassword(String email){
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = usersResource.searchByEmail(email, true).getFirst();
        if (userRepresentation == null) throw new IllegalArgumentException("User with email %s not found".formatted(email));
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.executeActionsEmail(List.of(ExecutionActions.UPDATE_PASSWORD));
    }

    private UserRepresentation mapUserToUserRepresentation(User user){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(false);
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private List<CredentialRepresentation> mapUserToCredentialRepresentation(User user){
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);
        return List.of(credentialRepresentation);
    }

    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();
    }

    @Async
    protected void sendEmailVerification(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    private String extractUserIdFromLocation(String locationHeader) {
        if (locationHeader == null || locationHeader.isEmpty()) throw new IllegalArgumentException("Location header is missing in response");
        if (!locationHeader.contains("/users/")) throw new IllegalArgumentException("Unexpected Location header format: " + locationHeader);
        return locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
    }

    private RolesResource getRolesResource(){
        return keycloak.realm(realm).roles();
    }
}
