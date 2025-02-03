package com.workshopngine.platform.authenticationmanagement.authentication.application.internal.outboundservices.acl;

import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
            if (!Objects.equals(response.getStatus(), 201)) {
                throw new IllegalArgumentException("Error creating user");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating user", e);
        }
    }

    public User getUserById(String userId){
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();
        return new User(userRepresentation.getUsername(), userRepresentation.getEmail());
    }

    private UserRepresentation mapUserToUserRepresentation(User user){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(true);
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
}
