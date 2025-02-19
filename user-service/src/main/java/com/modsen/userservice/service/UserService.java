package com.modsen.userservice.service;

import com.modsen.userservice.dto.UsersResponseDto;

public interface UserService {
    UsersResponseDto findUserById(Long id);
}
