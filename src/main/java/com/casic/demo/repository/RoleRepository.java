package com.casic.demo.repository;

import com.casic.demo.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<SysRole,Integer> {
    SysRole findRoleByName(String name);
}
