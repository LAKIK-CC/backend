package com.backend.lakik.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.lakik.model.RoleModel;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByNamaRole(String namaRole);
}