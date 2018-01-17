package com.casic.demo.controller;

import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.SysUser;
import com.casic.demo.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户控制层
 * RestController 该类下所有返回值默认以json格式进行返回
 * RequestMapping 匹配url地址 /user
 * Validated 代表该类启用参数验证，通过添加注解可以验证参数
 * @author bekey
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ResultGenerator generator;

    @Autowired
    public UserController(ResultGenerator generator) {
        this.generator = generator;
    }

    @RequestMapping(value = "/getUser")
    public RestResult getUser() {
        //获取当前用户信息
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return generator.getSuccessResult(sysUser);
    }

}
