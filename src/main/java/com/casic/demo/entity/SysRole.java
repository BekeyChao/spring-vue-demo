package com.casic.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 */
@Entity
public class SysRole implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true,nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;

    public SysRole() {
    }

    public SysRole(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
