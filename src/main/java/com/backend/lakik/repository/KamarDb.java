package com.backend.lakik.repository;

import com.backend.lakik.model.KamarModel;
import com.backend.lakik.model.UserModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KamarDb extends JpaRepository<KamarModel, Long> {
  List<KamarModel> findAllByUserModel(UserModel userModel, Sort sort);
}
