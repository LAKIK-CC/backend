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
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setNamaLengkap(namaLengkap);
        user.setEmail(email);
        user.setPassword(PasswordEncoder.encode(password));
        user.setRole(role);
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
