package com.casic.demo.service;

import com.casic.demo.entity.Permission;
import com.casic.demo.entity.SysRole;
import com.casic.demo.repository.PermissionRepository;
import com.casic.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RoleAndPermissionService")
public class RoleAndPermissionServiceImpl implements RoleAndPermissionService {
    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Autowired
    public RoleAndPermissionServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission findPermissionById(Integer id) {
        return permissionRepository.findOne(id);
    }

    @Override
    public SysRole findRoleById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public SysRole saveRole(SysRole sysRole) {
        return roleRepository.save(sysRole);
    }

    @Override
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public SysRole findRoleByRoleName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public void updatePublicResource() {

    }

    @Override
    public SysRole saveRole(String name, Integer[] pids) {
        List<Permission> permissions = new ArrayList<>();
        for (Integer pid: pids) {
            permissions.add(permissionRepository.findOne(pid));
        }
        SysRole sysRole = new SysRole(name, permissions);
        return roleRepository.save(sysRole);
    }
}
