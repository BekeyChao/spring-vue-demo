package com.casic.demo.service;

import com.casic.demo.entity.Permission;
import com.casic.demo.entity.Role;

public interface RoleAndPermissionService {
    Permission findPermissionById(Integer id);

    Role findRoleById(Integer id);

    Role saveRole(Role role);

    Permission savePermission(Permission permission);

    Role findRoleByRoleName(String roleName);

    void updatePublicResource();
}
