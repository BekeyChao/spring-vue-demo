package com.casic.demo.service;

import com.casic.demo.entity.SysUser;
import com.casic.demo.repository.UserRepository;
import com.casic.demo.security.PermissionSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户服务层实现类
 * Created by bekey on 2017/12/20.
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    private final  UserRepository userRepository;

    private final PermissionSecurityMetadataSource metadataSource;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PermissionSecurityMetadataSource metadataSource) {
        this.userRepository = userRepository;
        this.metadataSource = metadataSource;
    }

    @Override
    public SysUser saveUser(SysUser sysUser) {
        return userRepository.save(sysUser);
    }

    @Override
    public SysUser checkLogin(String name, String password) {
        return userRepository.findUserByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userRepository.findUserByUsername(username);
        if (sysUser != null) {
            return sysUser;
        }
        throw new UsernameNotFoundException("用户不存在");
    }

    @Override
    public void updatePublicResource() {
        metadataSource.updateRequests();
    }

}
