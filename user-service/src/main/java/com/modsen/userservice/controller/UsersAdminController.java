package com.modsen.userservice.controller;

import com.modsen.userservice.dto.PageContainerDto;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.dto.UsersResponseDto;
import com.modsen.userservice.dto.UsersUpdateDto;
import com.modsen.userservice.service.UserService;
import com.modsen.userservice.validator.PageableValid;
import com.modsen.userservice.validator.ValidRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Validated
public class UsersAdminController {

    private final UserService userService;

    @GetMapping("/{keycloakId}")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponseDto findById(@PathVariable String keycloakId) {
        return userService.findUserById(keycloakId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsersResponseDto add(@RequestBody UsersCreateDto user) {
        return userService.createUser(user);
    }

    @PutMapping("/{keycloakId}")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponseDto update(@PathVariable String keycloakId, @RequestBody UsersUpdateDto usersUpdateDto) {
        return userService.updateUser(keycloakId, usersUpdateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<?> findAll(@PageableValid @PageableDefault(sort = "id") Pageable pageable,
                                       @RequestParam(defaultValue = "") String keyword) {
        return userService.findAll(pageable, keyword);
    }

    @DeleteMapping("/{keycloakId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String keycloakId) {
        userService.deleteUser(keycloakId);
    }

    @PutMapping("/roles/assign/{keycloakId}")
    @ResponseStatus(HttpStatus.OK)
    public UsersResponseDto assignRole(@PathVariable String keycloakId, @ValidRole @RequestParam String role) {
        return userService.assignRole(keycloakId, role);
    }
}
