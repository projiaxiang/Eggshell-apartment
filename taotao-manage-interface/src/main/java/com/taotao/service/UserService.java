package com.taotao.service;

import com.taotao.entity.User;

public interface UserService {

    public User findUser(String username, String password);

    public int updateUser(User bean);

    public int updatePassword(int id, String password);

}
