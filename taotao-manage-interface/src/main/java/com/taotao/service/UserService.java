package com.taotao.service;

import com.taotao.entity.User;

public interface UserService {

    User findUser(String username, String password);

    int updateUser(User bean);

    int updatePassword(int id, String password);

    int insertUser(User bean);

}
