package com.modsen.userservice.service;

import com.modsen.userservice.domain.User;
import com.modsen.userservice.dto.PageContainerDto;
import com.modsen.userservice.dto.ProfileResponseDto;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;
import com.modsen.userservice.dto.UsersUpdateDto;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UsersResponseDto findUserById(String id);

    UsersResponseDto createUser(UsersCreateDto usersCreateDto);

    User createUserFromKeycloak(User user);

    UsersResponseDto updateUser(String keycloakId, UsersUpdateDto usersUpdateDto);

    PageContainerDto<UsersResponseDto> findAll(Pageable pageable, String keyword);

    void deleteUser(String keycloakId);

    ProfileResponseDto getProfile(String userId);

    UsersResponseDto assignRole(String keycloakId, String role);


    Boolean isAdmin(String userId);
}
