package com.backend.lakik.service;

import com.backend.lakik.model.RoleModel;
import com.backend.lakik.model.UserModel;
import com.backend.lakik.rest.dto.RegisterRequest;

public interface UserRestService {
    UserModel createUser(RegisterRequest userData, RoleModel role);
    UserModel getUserByUsername(String username);
}
