package com.example.petproject.repository;

import com.example.petproject.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.lang.annotation.Native;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepositoryImplementation<User, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmailOrUsername(String email, String username);

    @Modifying
    @Query(value = "DELETE FROM app_user WHERE id = ?1 ", nativeQuery = true)
    void hardDelete(String id);

}
