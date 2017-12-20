package com.casic.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 用户表实体类
 * Entity 代表这是实体类，要交给Hibernate管理
 * 。 @Size @NotNull 都是validation框架的注解，更多验证请参见网络资料
 * Created by bekey on 2017/12/20.
 */
@Entity
public class SysUser {
    @Id
    @GeneratedValue
    private Integer id; //主键 自增

    @NotNull(message = "用户名不能为空")
    @Size(min = 6 , max = 18, message = "用户名应设为6至18位")
    @Column(unique = true,nullable = false)
    private String name; //唯一 非空

    @Column(nullable = false)
    @NotNull(message = "密码不能为空")
    @Size(min = 6 , max = 18, message = "密码应设为6至18位")
    private String password; //非空

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
