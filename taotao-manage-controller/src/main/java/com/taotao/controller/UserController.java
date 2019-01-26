package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taotao.entity.Result;
import com.taotao.entity.User;
import com.taotao.service.UserService;
import com.taotao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * 单点登录
 * */

@RestController
@CrossOrigin
public class UserController {

    @Reference(version = "1.0")
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${redis.expire}")
    private long expire;

    @RequestMapping("/login")
    public Result findUser(@RequestParam("username")String username, @RequestParam("password")String password) {
        User bean = userService.findUser(username, password);
        if (bean != null) {
            //为了提高安全性，把密码设置为空
            bean.setPassword(null);
            //把uuid作为缓存的key
            String uuid = UUID.randomUUID().toString();
            redisUtil.set("USER_SESSION:" + uuid, bean);
            return new Result("200", true, uuid);
        }
        return new Result("400", false, "用户名或密码不正确");
    }

    @RequestMapping("/get/user/from/redis")
    public User getUserFormRedis(@RequestParam("key")String key) {
        User bean = (User) redisUtil.get("USER_SESSION:" + key);
        return bean;
    }

    @RequestMapping("/update/user")
    public int updateUser(@RequestBody String user, @RequestParam String token) {
        User bean = JSON.parseObject(user, new TypeReference<User>() {});
        int count = userService.updateUser(bean);
        if (count == 1) {
            //如何更新成功，则更新缓存中的用户信息
            redisUtil.del("USER_SESSION:" + token);
            redisUtil.set("USER_SESSION:" + token, bean);
        }
        return count;
    }

    @RequestMapping("/update/user/password")
    public int updatePassword(@RequestParam int id, @RequestParam String password) {
        return userService.updatePassword(id, password);
    }

}
