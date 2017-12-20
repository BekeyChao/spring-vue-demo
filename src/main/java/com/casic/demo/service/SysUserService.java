package com.casic.demo.service;

import com.casic.demo.entity.SysUser;

/**
 * 用户服务层接口
 * Created by bekey on 2017/12/20.
 */
public interface SysUserService {
    /**
     * 注册用户
     * @param user
     * @return 注册成功将用户信息返回，否则返回null
     */
    SysUser saveUser(SysUser user);

    /**
     * 检查用户名密码是否正确
     * @param name 用户名
     * @param password 密码
     * @return 验证通过则将用户信息返回，否则返回null
     */
    SysUser checkLogin(String name,String password);
}
