package com.modsen.userservice.service;

import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;
import com.modsen.userservice.dto.UsersUpdateDto;

public interface UserService {
    UsersResponseDto findUserById(String id);

    UsersResponseDto createUser(UsersCreateDto usersCreateDto);

    UsersResponseDto updateUser(String keycloakId, UsersUpdateDto usersUpdateDto);
}
