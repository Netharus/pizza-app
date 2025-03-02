package com.modsen.userservice.service.impl;

import com.modsen.userservice.domain.User;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersUpdateDto;
import com.modsen.userservice.exceptions.ErrorMessages;
import com.modsen.userservice.exceptions.UserNotFoundException;
import com.modsen.userservice.mapper.UserMapper;
import com.modsen.userservice.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public String createUser(UsersCreateDto usersCreateDto) {
        User user = userMapper.fromUserCreateDto(usersCreateDto);
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setAttributes(Map.of(
                "fullName", List.of(user.getFullName()),
                "phoneNumber", List.of(user.getPhoneNumber())
        ));
        userRepresentation.setCredentials(Collections.singletonList(createCredential(usersCreateDto.password())));

        Response response = keycloak.realm(realm).users().create(userRepresentation);
        log.info(String.format("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo()));

        return CreatedResponseUtil.getCreatedId(response);
    }

    @Override
    @Transactional
    public void updateUser(String keycloakId, UsersUpdateDto usersUpdateDto) {
        UserResource userResource = keycloak.realm(realm).users().get(keycloakId);
        if (userResource == null) {
            throw new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, keycloakId));
        }

        UserRepresentation userRepresentation = userResource.toRepresentation();

        userRepresentation.setUsername(usersUpdateDto.username());
        userRepresentation.setEmail(usersUpdateDto.email());
        userRepresentation.setAttributes(Map.of(
                "fullName", List.of(usersUpdateDto.fullName()),
                "phoneNumber", List.of(usersUpdateDto.phoneNumber())
        ));

        userResource.update(userRepresentation);
    }

    private CredentialRepresentation createCredential(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

}
