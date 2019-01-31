package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taotao.entity.Collection;
import com.taotao.entity.Result;
import com.taotao.service.CollectionService;
import com.taotao.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin
public class CollectionController {

    @Reference(version = "1.0.0")
    private CollectionService collectionService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/api/collection/insertCollection")
    @ResponseBody
    @Transactional
    public Result insertCollection(@RequestBody String collection, @RequestHeader("Authorization")String token) {
        Collection bean = JSON.parseObject(collection, new TypeReference<Collection>() {});
        bean.setId(UUID.randomUUID().toString());
        int result = collectionService.insertCollection(bean);
        if (result == 1) {
//            List<Collection> list = null;
//            Boolean flag = redisUtil.hasKey("collection" + token);
//            if (flag) {
//                list = (List<Collection>) redisUtil.get("collection" + token);
//                list.add(bean);
//                redisUtil.lSet("collection" + token, list);
//            } else {
//                Map<String, Object> map = new HashMap<>();
//                map.put("userId", bean.getUserId());
//                list =  collectionService.selectCollectionByUserId(map);
//                redisUtil.lSet("collection" + token, list);
//            }
            return new Result("200", true, "添加成功");
        } else {
            return new Result("500", false, "添加失败");
        }
    }

    @RequestMapping("/find/collection/by/userId")
    @ResponseBody
    public List<?> findCollectionByUserId(@RequestParam("userId")String userId,
                                          @RequestParam("houseId")String houseId,
                                                   @RequestParam("token")String token) {
//        Boolean flag = redisUtil.hasKey("collection" + token);
//        if (flag) {
//            List<Object> objs = (List<Object>) redisUtil.lGet("collection" + token, 0, -1);
//            return objs;
//        } else {
//            Map<String, Object> map = new HashMap<>();
//            map.put("userId", userId);
//            List<Collection> list =  collectionService.selectCollectionByUserId(map);
//            if (list.size() != 0) {
//                redisUtil.lSet("collection" + token, list);
//            }
//            return list;
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("houseId", houseId);
        List<Collection> list =  collectionService.selectCollectionByUserId(map);
        return list;
    }

}
