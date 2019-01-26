package com.taotao.dao;

import com.taotao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

    @Select("select * from user where username = #{username} and password = #{password}")
    User findUser(@Param("username")String username, @Param("password")String password);

    @Update("update user set name = #{name}, phone = #{phone}, sex = #{sex} where id = #{id}")
    int updateUser(User bean);

    @Update("update user set password = #{password} where id = #{id}")
    int updatePassword(@Param("id")int id, @Param("password")String password);

}
