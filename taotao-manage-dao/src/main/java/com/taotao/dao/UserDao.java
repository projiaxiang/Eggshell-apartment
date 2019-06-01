package com.taotao.dao;

import com.taotao.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    @Select("select * from user where username = #{username} and password = #{password}")
    User findUser(@Param("username")String username, @Param("password")String password);

    @Update("update user set name = #{name}, phone = #{phone}, sex = #{sex} where id = #{id}")
    int updateUser(User bean);

    @Update("update user set password = #{password} where id = #{id}")
    int updatePassword(@Param("id")int id, @Param("password")String password);

    @Insert("insert into user(username, password, name, sex, phone) " +
            "values(#{username}, #{password}, #{name}, #{sex}, #{phone})")
    int insertUser(User bean);

    @Select("select * from user where role = 1 limit #{startPage}, #{pageSize}")
    List<User> listUsers(Map<String, Object> user);

    @Delete("delete from user where id = #{id}")
    Integer deleteUser(@Param("id")int id);

    @Select("select count(*) from user where role = 1")
    Integer countUser();

}
