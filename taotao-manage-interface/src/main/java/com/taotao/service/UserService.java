package com.taotao.service;

import com.taotao.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findUser(String username, String password);

    int updateUser(User bean);

    int updatePassword(int id, String password);

    int insertUser(User bean);

    List<User> listUsers(Map<String, Object> user);

    Integer deleteUser(int id);

    Integer countUser();
}
