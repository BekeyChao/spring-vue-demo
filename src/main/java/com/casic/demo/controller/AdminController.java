package com.casic.demo.controller;

import com.casic.demo.entity.RestResult;
import com.casic.demo.service.RoleAndPermissionService;
import com.casic.demo.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    RoleAndPermissionService roleAndPermissionService;

    @Autowired
    ResultGenerator generator;

    @RequestMapping("/isAdmin")
    public RestResult isAdmin() {
        return generator.getSuccessResult("恭喜你，就是管理员");
    }

    @RequestMapping("/addRole")
    public RestResult addRole(String name, Integer pid) {
        Integer[] pids = {pid};
        return generator.getSuccessResult( "新建角色成功" ,roleAndPermissionService.saveRole(name, pids));
    }
}
