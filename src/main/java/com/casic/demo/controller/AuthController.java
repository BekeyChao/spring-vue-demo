package com.casic.demo.controller;

import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.ResultCode;
import com.casic.demo.entity.Role;
import com.casic.demo.entity.User;
import com.casic.demo.service.RoleAndPermissionService;
import com.casic.demo.service.UserService;
import com.casic.demo.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    private final ResultGenerator generator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleAndPermissionService roleAndPermissionService;

    @Autowired  //自动装配
    public AuthController(UserService userService, ResultGenerator generator) {
        this.userService = userService;
        this.generator = generator;
    }

    /**
     * 匹配 /user/register 地址
     * .在实体前添加 @Valid 注解代表要对这个实体进行验证，如果验证不通过就会报异常
     * bindingResult是对异常信息的包装，不过这里我们不用，而是交由异常处理器进行处理
     * @return 注册成功会将注册信息返回（！因为是demo所以没有考虑安全性）
     */
    @RequestMapping("/register")
    public RestResult register(User user, Integer roleId) {
        Role role = roleAndPermissionService.findRoleById(roleId);
        //密码通过PasswordEncoder加密
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return generator.getSuccessResult("用户注册成功",userService.saveUser(user));
    }

    @RequestMapping("/401")
    public RestResult unauthorized () {
        return generator.getFreeResult(ResultCode.UNAUTHORIZED, "用户未登录",null);
    }

    @RequestMapping("/logout")
    public RestResult logout() {
        return generator.getSuccessResult("登出成功");
    }

    @RequestMapping("/failLogin")
    public RestResult failLogin(HttpServletRequest request) {
        Object exception = request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exception != null) {
            AuthenticationException authException = (AuthenticationException)exception;
            return generator.getFailResult(authException.getLocalizedMessage());
        }
        return generator.getFailResult("未知原因");
    }
}
