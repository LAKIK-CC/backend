package com.backend.lakik.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.lakik.model.KamarModel;
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
            .listKamar(new ArrayList<KamarModel>())
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

    @Override
    public void addKamar(String username, KamarModel kamar) {
        UserModel user = getUserByUsername(username);
        List<KamarModel> tempListKamar = user.getListKamar();
        tempListKamar.add(kamar);
        Collections.sort(tempListKamar);
        user.setListKamar(tempListKamar);
        userDb.save(user);
    }

    @Override
    public void removeKamar(String username, KamarModel kamar) {
        UserModel user = getUserByUsername(username);
        List<KamarModel> tempListKamar = user.getListKamar();
        tempListKamar.remove(kamar);
    }

    @Override
    public List<KamarModel> getAllUserKamar(String username) {
        UserModel user = getUserByUsername(username);
        return user.getListKamar();
    }
}
