package com.casic.demo.entity;

import com.casic.demo.security.PermissionGrantedAuthority;
import com.casic.demo.security.RequestMatcherManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户表实体类
 * Entity 代表这是实体类，要交给Hibernate管理
 * 。 @Size @NotNull 都是validation框架的注解，更多验证请参见网络资料
 * Created by bekey on 2017/12/20.
 */
@Entity
public class User implements UserDetails{
    @Id
    @GeneratedValue
    private Integer id; //主键 自增
    @Column(unique = true,nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;
    private String nickname;

    private Boolean enable = Boolean.TRUE;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enable;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        getRoles().forEach(role -> {
            String roleName = role.getName();
            RequestMatcherManager manager = new RequestMatcherManager();
            role.getPermissions().forEach(permission -> {
                manager.addMatchers(permission.getResource(), permission.getMethod());
            });
            auths.add(new PermissionGrantedAuthority(roleName, manager));
        });
        return auths;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "用户" + username;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
