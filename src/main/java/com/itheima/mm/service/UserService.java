package com.itheima.mm.service;

import com.itheima.mm.dao.UserDao;
import com.itheima.mm.pojo.User;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * @author: Vanbban
 * @create 2020-12-16 16:49
 */
public class UserService {
    public User findUser(User paramentUser ) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User loginUser=userDao.findByUsername(paramentUser.getUsername());
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        if (loginUser!=null) {
            if (loginUser.getPassword().equals(paramentUser.getPassword())) {
                return loginUser;
            }else{
                throw new RuntimeException("密码错误");
            }

        }else{
            throw new RuntimeException("用户名错误");
        }
    }
}
