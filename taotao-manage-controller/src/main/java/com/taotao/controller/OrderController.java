package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taotao.entity.Order;
import com.taotao.entity.Result;
import com.taotao.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangJiaCheng
 * @create 2019-04-21 15:25
 */

@RestController
@CrossOrigin
public class OrderController {

    @Reference(version = "1.0.0")
    private OrderService orderService;

    @PostMapping("/api/order/insert/order")
    public Result insertOrder(@RequestBody String order) {

        Order bean = JSON.parseObject(order, new TypeReference<Order>() {});

        bean.setCreateTime(new Date());
        bean.setNumber(String.valueOf(new Date().getTime()));

        int result = orderService.insertOrder(bean);
        if (result == 1) {
            return new Result("200", true, "添加成功");
        } else {
            return new Result("500", false, "添加失败");
        }

    }


    @RequestMapping("/find/order/by/userId")
    @ResponseBody
    public Map<String, Object> findCollectionByUserId(@RequestParam("userId")String userId,
                                                      @RequestParam("houseId")String houseId,
                                                      @RequestParam("startPage")int startPage,
                                                      @RequestParam("pageSize")int pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        int total = orderService.findTotalHouse(map);
        map.put("startPage", startPage);
        map.put("pageSize", pageSize);
        if (houseId != null && !houseId.equals("")) {
            map.put("houseId", houseId);
        }
        List<Order> list =  orderService.selectOrderByUserId(map);
        map.clear();
        map.put("total", total);
        map.put("order", list);
        return map;
    }

    @RequestMapping("/delete/order")
    public Result deleteHouse(@RequestParam("houseId")String houseId, @RequestParam("userId")String userId) {
        int result = orderService.deleteOrder(houseId, userId);
        if (result == 0) {
            return new Result("500", false, "删除失败");
        } else {
            return new Result("200", true, "删除成功");
        }
    }

}
