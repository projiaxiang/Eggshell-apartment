package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.dao.UserDao;
import com.taotao.entity.User;
import com.taotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUser(String username, String password) {
        return userDao.findUser(username, password);
    }

    @Override
    public int updateUser(User bean) {
        return userDao.updateUser(bean);
    }

    @Override
    public int updatePassword(int id, String password) {
        return userDao.updatePassword(id, password);
    }

    @Override
    public int insertUser(User bean) {
        return userDao.insertUser(bean);
    }
}
