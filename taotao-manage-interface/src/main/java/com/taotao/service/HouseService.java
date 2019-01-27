package com.taotao.service;

import com.taotao.entity.House;

import java.util.List;
import java.util.Map;

public interface HouseService {

    public int insertHouse(House bean);

    public List<House> findHouse(Map<String, Object> house);

    public int deleteHouse(String id);

    public int updateHouse(House bean);

    public int findTotalHouse();

}
