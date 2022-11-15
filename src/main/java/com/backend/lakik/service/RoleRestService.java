package com.backend.lakik.service;

import com.backend.lakik.model.RoleModel;

public interface RoleRestService {
    RoleModel getRoleByRoleName(String roleName);
}

