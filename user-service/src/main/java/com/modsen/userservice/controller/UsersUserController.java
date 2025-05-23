package com.modsen.userservice.controller;

import com.modsen.userservice.dto.ProfileResponseDto;
import com.modsen.userservice.dto.UsersCreateDto;
import com.modsen.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersUserController {

    private final UserService userService;

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponseDto getCurrentUserProfile(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return userService.getProfile(userId);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void getCurrentUserRegistration(@RequestBody UsersCreateDto user) {
        userService.createUser(user);
    }

    @GetMapping("/isAdmin")
    ResponseEntity<Boolean> isAdmin(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return ResponseEntity.ok(userService.isAdmin(userId));
    }
}
