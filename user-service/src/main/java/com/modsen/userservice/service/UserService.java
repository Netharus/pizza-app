package com.modsen.userservice.service;

import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;

public interface UserService {
    UsersResponseDto findUserById(String id);

    UsersResponseDto createUser(UsersCreateDto usersCreateDto);
}
