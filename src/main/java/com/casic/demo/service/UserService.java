package com.casic.demo.service;

import com.casic.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户服务层接口
 * UserService也比较特殊，需要实现UserDetailsService接口
 * Created by bekey on 2017/12/20.
 */
public interface UserService extends UserDetailsService{
    /**
     * 注册用户
     * @param user
     * @return 注册成功将用户信息返回，否则返回null
     */
    User saveUser(User user);

    /**
     * 检查用户名密码是否正确
     * @param name 用户名
     * @param password 密码
     * @return 验证通过则将用户信息返回，否则返回null
     */
    User checkLogin(String name, String password);

    void updatePublicResource();
}
