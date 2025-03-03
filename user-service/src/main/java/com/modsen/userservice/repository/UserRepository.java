package com.modsen.userservice.repository;

import com.modsen.userservice.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKeycloakId(String keycloakId);

    @Query("select u from User u where lower(u.fullName) like lower(concat('%',:keyword,'%')) " +
            "or lower(u.email) like lower(concat('%',:keyword,'%'))" +
            "or lower(u.keycloakId) like lower(concat('%',:keyword,'%'))" +
            "or cast(u.id as string) like lower(concat('%',:keyword,'%'))" +
            "or lower(u.phoneNumber) like lower(concat('%',:keyword,'%'))")
    Page<User> findAll(Pageable pageable, @Param("keyword") String keyword);

    Optional<User> findByEmailOrUsernameOrPhoneNumber(String email, String username, String phoneNumber);

    void deleteByKeycloakId(String keycloakId);
}
