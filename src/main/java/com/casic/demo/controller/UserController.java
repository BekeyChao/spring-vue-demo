package com.casic.demo.controller;

import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.User;
import com.casic.demo.service.RoleAndPermissionService;
import com.casic.demo.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ResultGenerator generator;

    // 登陆验证工作已交由Security框架处理
//    @RequestMapping(value = "/login")
//    public RestResult login(@NotNull(message = "用户名不能为空") String username,@NotNull(message = "密码不能为空") String password, HttpSession session) {

//    }

    @RequestMapping(value = "/successLogin")
    public RestResult success() {
        //获取当前用户信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return generator.getSuccessResult(user);
    }

}
