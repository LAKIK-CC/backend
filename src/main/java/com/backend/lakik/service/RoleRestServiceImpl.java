package com.backend.lakik.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.lakik.model.RoleModel;
import com.backend.lakik.repository.RoleDb;

@Service
@Transactional
public class RoleRestServiceImpl implements RoleRestService {
    @Autowired
    private RoleDb roleDb;

    @Override
    public RoleModel getRoleByRoleName(String roleName) {
        Optional<RoleModel> role = roleDb.findByNamaRole(roleName);
        if (role.isPresent()) {
            return role.get();
        }
        return null;
    }

}
