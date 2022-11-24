package com.backend.lakik.repository;

import com.backend.lakik.model.KamarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KamarDb extends JpaRepository<KamarModel, Long> {
  
}
