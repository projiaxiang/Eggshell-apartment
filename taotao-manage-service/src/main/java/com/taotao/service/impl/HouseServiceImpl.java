package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.dao.HouseDao;
import com.taotao.entity.House;
import com.taotao.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0")
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Override
    public int insertHouse(House bean) {
        return houseDao.insertHouse(bean);
    }

    @Override
    public List<House> findHouse(String userId, String rent, int startPage, int pageSize) {
        return houseDao.findHouse(userId, rent, startPage, pageSize);
    }

    @Override
    public int deleteHouse(String id) {
        return houseDao.deleteHouse(id);
    }

    @Override
    public int updateHouse(House bean) {
        return houseDao.updateHouse(bean);
    }

    @Override
    public int findTotalHouse() {
        return houseDao.findTotalHouse();
    }
}
