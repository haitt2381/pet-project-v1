package com.example.petproject.repository;

import com.example.petproject.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepositoryImplementation<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmailOrUsername(String email, String username);

//    @Query("UPDATE User set isActive = ?")
//    User updateIsActive();
}