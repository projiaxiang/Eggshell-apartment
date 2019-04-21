package com.taotao.service;

import com.taotao.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * @author WangJiaCheng
 * @create 2019-04-21 15:23
 */
public interface OrderService {

    int insertOrder(Order bean);

    List<Order> selectOrderByUserId(Map<String, Object> map);

    int findTotalHouse(Map<String, Object> map);

    int deleteOrder(String houseId, String userId);

}
