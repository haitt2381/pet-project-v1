package com.example.petproject.repository;

import com.example.petproject.entity.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepositoryImplementation<User, UUID> {

    Optional<User> findByEmail(String email);

}