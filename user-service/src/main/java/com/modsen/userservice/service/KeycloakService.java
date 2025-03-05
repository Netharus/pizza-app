package com.modsen.userservice.service;

import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersUpdateDto;

public interface KeycloakService {

    String createUser(UsersCreateDto usersCreateDto);

    void updateUser(String keycloakId, UsersUpdateDto usersUpdateDto);

    void deleteUser(String keycloakId);
}
