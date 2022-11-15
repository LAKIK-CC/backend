package com.backend.lakik.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.lakik.model.RoleModel;
import com.backend.lakik.model.UserModel;
import com.backend.lakik.repository.UserDb;
import com.backend.lakik.util.PasswordEncoder;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel createUser(String username, String namaLengkap, String email, String password, RoleModel role) {        
        UserModel user = UserModel.builder()
            .username(username)
            .email(email)
            .namaLengkap(namaLengkap)
            .password(PasswordEncoder.encode(password))
            .role(role)
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
