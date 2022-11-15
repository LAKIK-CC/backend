package com.backend.lakik.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.lakik.model.UserModel;

@Repository
public interface UserDb extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}
