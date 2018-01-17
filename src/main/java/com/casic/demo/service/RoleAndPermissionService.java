package com.casic.demo.service;

import com.casic.demo.entity.Permission;
import com.casic.demo.entity.SysRole;

public interface RoleAndPermissionService {
    Permission findPermissionById(Integer id);

    SysRole findRoleById(Integer id);

    SysRole saveRole(SysRole sysRole);

    Permission savePermission(Permission permission);

    SysRole findRoleByRoleName(String roleName);

    void updatePublicResource();

    SysRole saveRole(String name, Integer[] pids);
}
