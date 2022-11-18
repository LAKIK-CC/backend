package com.backend.lakik.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.lakik.model.RoleModel;
import com.backend.lakik.model.UserModel;
import com.backend.lakik.repository.UserDb;
import com.backend.lakik.rest.dto.RegisterRequest;
import com.backend.lakik.util.PasswordEncoder;
import com.backend.lakik.util.UUIDGenerator;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel createUser(RegisterRequest userData, RoleModel role) {
        UserModel user = UserModel.builder()
            .idUser(UUIDGenerator.generateUUID())
            .username(userData.getUsername())
            .email(userData.getEmail())
            .password(PasswordEncoder.encode(userData.getPassword()))
            .role(role)
            .alamatKos(userData.getAlamatKos())
            .deskripsiKos(userData.getDeskripsiKos())
            .namaKos(userData.getNamaKos())
            .nomorTeleponKos(userData.getNomorTeleponKos())
            .build();
        
        return userDb.save(user);
    }

    @Override
    public UserModel getUserByUsername(String username) {
        Optional<UserModel> user = userDb.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }
}
