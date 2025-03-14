package com.modsen.userservice.service.impl;

import com.modsen.userservice.client.OrderClient;
import com.modsen.userservice.domain.User;
import com.modsen.userservice.domain.enums.Role;
import com.modsen.userservice.dto.PageContainerDto;
import com.modsen.userservice.dto.ProfileResponseDto;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;
import com.modsen.userservice.dto.UsersUpdateDto;
import com.modsen.userservice.exceptions.AlreadyExistsException;
import com.modsen.userservice.exceptions.ErrorMessages;
import com.modsen.userservice.exceptions.ResourceNotAvailable;
import com.modsen.userservice.mapper.UserMapper;
import com.modsen.userservice.repository.UserRepository;
import com.modsen.userservice.service.KeycloakService;
import com.modsen.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeycloakService keycloakService;
    private final OrderClient orderClient;

    @Override
    @Transactional
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

    @Transactional
    @Override
    public User createUserFromKeycloak(User user) {
        isUserUnique(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UsersResponseDto updateUser(String keycloakId, UsersUpdateDto usersUpdateDto) {
        User existingUser = getUserByKeycloakId(keycloakId);
        User updatedUser = userMapper.fromUserUpdateDto(usersUpdateDto);

        isUserUniqueUpdate(updatedUser, keycloakId);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setFullName(updatedUser.getFullName());

        existingUser = userRepository.save(existingUser);

        keycloakService.updateUser(keycloakId, usersUpdateDto);

        return userMapper.fromUserToUsersResponseDto(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public PageContainerDto<UsersResponseDto> findAll(Pageable pageable, String keyword) {
        return userMapper.toUsersPageContainerDto(userRepository.findAll(pageable, keyword));
    }

    @Override
    @Transactional
    public void deleteUser(String keycloakId) {
        getUserByKeycloakId(keycloakId);
        if (Boolean.TRUE.equals(orderClient.isUserUsed(keycloakId).getBody())) {
            throw new ResourceNotAvailable(String.format(ErrorMessages.CANNOT_DELETE_USER, keycloakId));
        }
        keycloakService.deleteUser(keycloakId);
        userRepository.deleteByKeycloakId(keycloakId);
    }

    @Override
    @Transactional
    public ProfileResponseDto getProfile(String userId) {
        return userMapper.toProfileDto(getUserByKeycloakId(userId));
    }

    @Override
    @Transactional
    public UsersResponseDto assignRole(String keycloakId, String role) {
        User user = getUserByKeycloakId(keycloakId);
        user.setRole(Role.valueOf(role.toUpperCase()));
        user = userRepository.save(user);
        keycloakService.assignRole(keycloakId, role);
        return userMapper.fromUserToUsersResponseDto(user);
    }

    @Override
    @Transactional
    public Boolean isAdmin(String userId) {
        return getUserByKeycloakId(userId).getRole().equals(Role.ADMIN);
    }

    private void isUserUnique(User user) {
        List<User> users = userRepository.findByEmailOrUsernameOrPhoneNumber(
                user.getEmail(), user.getUsername(), user.getPhoneNumber()
        );
        validateUserUniqueness(user, users);
    }

    private void isUserUniqueUpdate(User updatedUser, String keycloakId) {
        List<User> users = userRepository.findByEmailOrUsernameOrPhoneNumber(
                updatedUser.getEmail(), updatedUser.getUsername(), updatedUser.getPhoneNumber());
        users.removeIf(user -> user.getKeycloakId().equals(keycloakId));
        validateUserUniqueness(updatedUser, users);
    }

    private void validateUserUniqueness(User user, List<User> users) {
        if (!users.isEmpty()) {
            String conflicts = users.stream()
                    .map(existingUser -> checkUniqueness(user, existingUser))
                    .filter(conflict -> !conflict.isEmpty())
                    .collect(Collectors.joining(", "));

            if (!conflicts.isEmpty()) {
                throw new AlreadyExistsException(conflicts);
            }
        }
    }

    private String checkUniqueness(User user, User existingUser) {
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
        return String.join(", ", conflicts);
    }

    @Transactional
    protected User getUserByKeycloakId(String keycloakId) {
        return userRepository
                .findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    User user = keycloakService.findById(keycloakId);
                    return createUserFromKeycloak(user);
                });
    }

}
