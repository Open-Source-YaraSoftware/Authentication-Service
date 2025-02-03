package com.workshopngine.platform.authenticationmanagement.authentication.application.internal.queryservices;

import com.workshopngine.platform.authenticationmanagement.authentication.application.internal.outboundservices.acl.ExternalKeycloakService;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.aggregates.User;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.model.queries.GetUserByIdQuery;
import com.workshopngine.platform.authenticationmanagement.authentication.domain.services.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final ExternalKeycloakService externalKeycloakService;

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return Optional.of(externalKeycloakService.getUserById(query.userId()));
    }
}
