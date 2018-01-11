package com.casic.demo.controller;

import com.casic.demo.entity.Permission;
import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.Role;
import com.casic.demo.service.RoleAndPermissionService;
import com.casic.demo.service.UserService;
import com.casic.demo.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleAndPermissionService roleAndPermissionService;

    @Autowired
    UserService userService;

    @Autowired
    ResultGenerator generator;

    @RequestMapping("/addRole")
    public RestResult addRole(Role role,Integer pId) {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(roleAndPermissionService.findPermissionById(pId));
        return generator.getSuccessResult(roleAndPermissionService.saveRole(role));
    }

    @RequestMapping("/addPermission")
    public RestResult addPermission(Permission permission) {
        return generator.getSuccessResult(roleAndPermissionService.savePermission(permission));
    }

    @RequestMapping("/updatePublicResource")
    public RestResult updatePublicResource() {
        userService.updatePublicResource();
        return generator.getSuccessResult();
    }
}
