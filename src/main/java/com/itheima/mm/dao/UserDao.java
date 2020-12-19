package com.itheima.mm.dao;

import com.itheima.mm.pojo.User;

/**
 * @author: Vanbban
 * @create 2020-12-16 16:56
 */
public interface UserDao {
    User findByUsername(String username);
}
