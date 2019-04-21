package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.dao.OrderDao;
import com.taotao.entity.Order;
import com.taotao.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author WangJiaCheng
 * @create 2019-04-21 15:24
 */

@Service(version = "1.0.0")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public int insertOrder(Order bean) {
        return orderDao.insertOrder(bean);
    }

    @Override
    public List<Order> selectOrderByUserId(Map<String, Object> map) {
        return orderDao.selectOrderByUserId(map);
    }

    @Override
    public int findTotalHouse(Map<String, Object> map) {
        return orderDao.findTotalHouse(map);
    }

    @Override
    public int deleteOrder(String houseId, String userId) {
        return orderDao.deleteOrder(houseId, userId);
    }
}
