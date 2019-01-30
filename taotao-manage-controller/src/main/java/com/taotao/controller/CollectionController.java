package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taotao.entity.Collection;
import com.taotao.entity.Result;
import com.taotao.service.CollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@CrossOrigin
public class CollectionController {

    @Reference(version = "1.0.0")
    private CollectionService collectionService;

    @RequestMapping("/api/collection/insertCollection")
    @ResponseBody
    public Result insertCollection(@RequestBody String collection) {
        Collection bean = JSON.parseObject(collection, new TypeReference<Collection>() {});
        bean.setId(UUID.randomUUID().toString());
        int result = collectionService.insertCollection(bean);
        if (result == 1) {
            return new Result("200", true, "添加成功");
        } else {
            return new Result("500", false, "添加失败");
        }
    }

}
