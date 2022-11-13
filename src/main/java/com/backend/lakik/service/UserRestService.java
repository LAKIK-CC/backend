package com.backend.lakik.service;

import com.backend.lakik.model.RoleModel;
import com.backend.lakik.model.UserModel;

public interface UserRestService {
    UserModel createUser(String username, String namaLengkap, String email, String password, RoleModel role);
    UserModel getUserByUsername(String username);
}
