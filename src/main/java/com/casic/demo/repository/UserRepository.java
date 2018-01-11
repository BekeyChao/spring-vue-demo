package com.casic.demo.repository;

import com.casic.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户Dao层
 * 继承JapRepository，可以实现一些默认方法，如save/findAll/findOne/delete/count/exists 等
 * Created by bekey on 2017/12/20.
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
}
