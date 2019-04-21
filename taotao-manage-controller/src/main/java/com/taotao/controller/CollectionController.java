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
        bean.setCreateTime(new Date());
        int result = collectionService.insertCollection(bean);
        if (result == 1) {
            return new Result("200", true, "添加成功");
        } else {
            return new Result("500", false, "添加失败");
        }
    }

    @RequestMapping("/find/collection/by/userId")
    @ResponseBody
    public Map<String, Object> findCollectionByUserId(@RequestParam("userId")String userId,
                                                   @RequestParam("houseId")String houseId,
                                                   @RequestParam("token")String token,
                                                   @RequestParam("startPage")int startPage,
                                                   @RequestParam("pageSize")int pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        int total = collectionService.findTotalHouse(map);
        map.put("startPage", startPage);
        map.put("pageSize", pageSize);
        if (houseId != null && !houseId.equals("")) {
            map.put("houseId", houseId);
        }
        List<Collection> list =  collectionService.selectCollectionByUserId(map);
        map.clear();
        map.put("total", total);
        map.put("collection", list);
        return map;
    }

    @RequestMapping("/delete/collection")
    @ResponseBody
    public Result deleteHouse(@RequestParam("houseId")String houseId, @RequestParam("userId")String userId) {
        int result = collectionService.deleteCollection(houseId, userId);
        if (result == 0) {
            return new Result("500", false, "删除失败");
        } else {
            return new Result("200", true, "删除成功");
        }
    }

}
