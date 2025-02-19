package com.modsen.userservice.service.impl;

import com.modsen.userservice.dto.UsersResponseDto;
import com.modsen.userservice.exceptions.UserNotFoundException;
import com.modsen.userservice.mapper.UserMapper;
import com.modsen.userservice.repository.UserRepository;
import com.modsen.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UsersResponseDto findUserById(Long id) {
        return userMapper
                .fromUserToUsersResponseDto(userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id)));
    }

}
