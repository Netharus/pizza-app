package com.modsen.userservice.service.impl;

import com.modsen.userservice.domain.User;
import com.modsen.userservice.domain.enums.Role;
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
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    private final UserMapper userMapper;

    private final static String FULL_NAME = "fullName";
    private final static String PHONE_NUMBER = "phoneNumber";
    private final static String KEYCLOAK_RESPONSE = "Response: %s %s%n";
    private final static String REMOVE_ROLE = "Removed role {} from user {}";
    private final static String ASSIGN_ROLE = "Assigned role {} to user {}";

    @Override
    @Transactional
    public String createUser(UsersCreateDto usersCreateDto) {
        User user = userMapper.fromUserCreateDto(usersCreateDto);
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setAttributes(Map.of(
                FULL_NAME, List.of(user.getFullName()),
                PHONE_NUMBER, List.of(user.getPhoneNumber())
        ));
        userRepresentation.setCredentials(Collections.singletonList(createCredential(usersCreateDto.password())));

        Response response = keycloak
                .realm(realm)
                .users()
                .create(userRepresentation);
        log.info(String.format(KEYCLOAK_RESPONSE, response.getStatus(), response.getStatusInfo()));

        assignRole(CreatedResponseUtil.getCreatedId(response), Role.USER.getValue());

        return CreatedResponseUtil.getCreatedId(response);
    }

    @Override
    @Transactional
    public void updateUser(String keycloakId, UsersUpdateDto usersUpdateDto) {
        UserResource userResource = getUserResource(keycloakId);

        UserRepresentation userRepresentation = userResource.toRepresentation();

        userRepresentation.setUsername(usersUpdateDto.username());
        userRepresentation.setEmail(usersUpdateDto.email());
        userRepresentation.setAttributes(Map.of(
                FULL_NAME, List.of(usersUpdateDto.fullName()),
                PHONE_NUMBER, List.of(usersUpdateDto.phoneNumber())
        ));

        userResource.update(userRepresentation);
    }

    @Override
    @Transactional
    public void deleteUser(String keycloakId) {
        keycloak
                .realm(realm)
                .users()
                .get(keycloakId)
                .remove();
    }

    @Override
    @Transactional
    public void assignRole(String keycloakId, String role) {
        UserResource userResource = getUserResource(keycloakId);
        RolesResource rolesResource = getRolesResource();

        RoleRepresentation newRole = rolesResource.get(role.toUpperCase()).toRepresentation();

        String oppositeRole = role.equalsIgnoreCase(Role.ADMIN.getValue()) ? Role.USER.getValue() : Role.ADMIN.getValue();

        RoleRepresentation oppositeRoleRepresentation = rolesResource.get(oppositeRole.toUpperCase()).toRepresentation();
        List<RoleRepresentation> existingRoles = userResource.roles().realmLevel().listAll();

        if (existingRoles.stream().anyMatch(r -> r.getName().equalsIgnoreCase(oppositeRole))) {
            userResource.roles().realmLevel().remove(Collections.singletonList(oppositeRoleRepresentation));
            log.info(REMOVE_ROLE, oppositeRole, keycloakId);
        }

        userResource.roles().realmLevel().add(Collections.singletonList(newRole));
        log.info(ASSIGN_ROLE, role, keycloakId);
    }

    @Override
    @Transactional
    public User findById(String keycloakId) {
        UserResource userResource = getUserResource(keycloakId);
        UserRepresentation userRepresentation = userResource.toRepresentation();

        return User.builder()
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .fullName(userRepresentation.getAttributes().get(FULL_NAME).getFirst())
                .phoneNumber(userRepresentation.getAttributes().get(PHONE_NUMBER).getFirst())
                .role(getUserRole(userResource))
                .keycloakId(keycloakId)
                .build();
    }

    @Transactional
    protected Role getUserRole(UserResource userResource) {
        Set<String> set = userResource.roles().realmLevel().listAll().stream().map(RoleRepresentation::getName).collect(Collectors.toSet());
        if (set.contains(Role.ADMIN.getValue())) {
            return Role.ADMIN;
        } else return Role.USER;
    }

    private CredentialRepresentation createCredential(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    private UserResource getUserResource(String keycloakId) {
        UserResource userResource = keycloak
                .realm(realm)
                .users()
                .get(keycloakId);
        if (userResource == null) {
            throw new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, keycloakId));
        }
        return userResource;
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }
}
