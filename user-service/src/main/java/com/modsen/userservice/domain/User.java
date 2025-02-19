package com.modsen.userservice.domain;

import com.modsen.userservice.domain.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, unique = true, name = "username")
    private String username;

    @Column(length = 50, nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, unique = true, name = "phone_number")
    private String phoneNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role = Role.USER;
}
