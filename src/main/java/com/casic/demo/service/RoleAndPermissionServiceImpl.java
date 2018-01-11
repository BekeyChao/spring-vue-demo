package com.casic.demo.service;

import com.casic.demo.entity.Permission;
import com.casic.demo.entity.Role;
import com.casic.demo.repository.PermissionRepository;
import com.casic.demo.repository.RoleRepository;
import com.casic.demo.security.PermissionSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RoleAndPermissionService")
public class RoleAndPermissionServiceImpl implements RoleAndPermissionService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Permission findPermissionById(Integer id) {
        return permissionRepository.findOne(id);
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public void updatePublicResource() {

    }
}
