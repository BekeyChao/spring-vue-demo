package com.casic.demo.entity;

import javax.persistence.*;

/**
 * 控制的资源
 */
@Entity
public class Permission {
    @Id
    @GeneratedValue
    private Integer id;

    // url资源 符合Ant Path Url表达式
    private String resources;

    //允许的方法 数据库中储存字段
    private String method;

    // 权限描述
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
