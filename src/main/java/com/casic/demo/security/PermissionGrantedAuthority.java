package com.casic.demo.security;

import org.springframework.security.core.GrantedAuthority;

public class PermissionGrantedAuthority implements GrantedAuthority{
    private String role;

    private RequestMatcherManager manager;

    public PermissionGrantedAuthority() {
    }

    public PermissionGrantedAuthority(String role, RequestMatcherManager manager) {
        this.role = role;
        this.manager = manager;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RequestMatcherManager getManager() {
        return manager;
    }

    public void setManager(RequestMatcherManager manager) {
        this.manager = manager;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
