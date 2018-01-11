package com.casic.demo.service;

import com.casic.demo.entity.User;
import com.casic.demo.repository.RoleRepository;
import com.casic.demo.repository.UserRepository;
import com.casic.demo.security.PermissionSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户服务层实现类
 * Created by bekey on 2017/12/20.
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionSecurityMetadataSource metadataSource;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User checkLogin(String name, String password) {
        return userRepository.findUserByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("用户不存在");
    }

    @Override
    public void updatePublicResource() {
        metadataSource.updateRequests();
    }

}
