package com.modsen.userservice.service.impl;

import com.modsen.userservice.domain.User;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;
import com.modsen.userservice.dto.UsersUpdateDto;
import com.modsen.userservice.exceptions.AlreadyExistsException;
import com.modsen.userservice.exceptions.ErrorMessages;
import com.modsen.userservice.exceptions.UserNotFoundException;
import com.modsen.userservice.mapper.UserMapper;
import com.modsen.userservice.repository.UserRepository;
import com.modsen.userservice.service.KeycloakService;
import com.modsen.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final KeycloakService keycloakService;

    @Override
    public UsersResponseDto findUserById(String keycloakId) {
        return userMapper
                .fromUserToUsersResponseDto(getUserByKeycloakId(keycloakId));
    }

    @Override
    @Transactional
    public UsersResponseDto createUser(UsersCreateDto usersCreateDto) {
        User user = userMapper.fromUserCreateDto(usersCreateDto);
        isUserUnique(user);
        user.setKeycloakId(keycloakService.createUser(usersCreateDto));
        return userMapper.fromUserToUsersResponseDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UsersResponseDto updateUser(String keycloakId, UsersUpdateDto usersUpdateDto) {
        User existingUser = getUserByKeycloakId(keycloakId);
        User updatedUser = userMapper.fromUserUpdateDto(usersUpdateDto);

        isUserUniqueUpdate(updatedUser,keycloakId);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setFullName(updatedUser.getFullName());

        existingUser = userRepository.save(existingUser);

        keycloakService.updateUser(keycloakId,usersUpdateDto);

        return userMapper.fromUserToUsersResponseDto(existingUser);
    }

    private void isUserUnique(User user) {
        userRepository.findByEmailOrUsernameOrPhoneNumber(
                user.getEmail(), user.getUsername(), user.getPhoneNumber()
        ).ifPresent(existingUser -> {
            checkUniqueness(user, existingUser);
        });
    }

    private void isUserUniqueUpdate(User updatedUser, String keycloakId) {
        userRepository.findByEmailOrUsernameOrPhoneNumber(
                updatedUser.getEmail(), updatedUser.getUsername(), updatedUser.getPhoneNumber()
        ).ifPresent(existingUser -> {
            if (!existingUser.getKeycloakId().equals(keycloakId)) {
                checkUniqueness(updatedUser, existingUser);
            }
        });
    }

    private void checkUniqueness(User user, User existingUser) {
        List<String> conflicts = new ArrayList<>();

        if (existingUser.getEmail().equals(user.getEmail())) {
            conflicts.add(String.format(ErrorMessages.USER_ALREADY_EXISTS_EMAIL, user.getEmail()));
        }
        if (existingUser.getUsername().equals(user.getUsername())) {
            conflicts.add(String.format(ErrorMessages.USER_ALREADY_EXISTS_USERNAME, user.getUsername()));
        }
        if (existingUser.getPhoneNumber().equals(user.getPhoneNumber())) {
            conflicts.add(String.format(ErrorMessages.USER_ALREADY_EXISTS_PHONE_NUMBER, user.getPhoneNumber()));
        }

        throw new AlreadyExistsException(String.join(", ", conflicts));
    }

    private User getUserByKeycloakId(String keycloakId) {
        return userRepository
                .findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, keycloakId)));
    }

}
