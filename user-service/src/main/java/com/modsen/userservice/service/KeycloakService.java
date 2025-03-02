package com.modsen.userservice.service;

import com.modsen.userservice.dto.UsersCreateDto;

public interface KeycloakService {

    String createUser(UsersCreateDto usersCreateDto);
}
