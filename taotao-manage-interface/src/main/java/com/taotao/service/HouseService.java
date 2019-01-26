package com.taotao.service;

import com.taotao.entity.House;

import java.util.List;

public interface HouseService {

    public int insertHouse(House bean);

    public List<House> findHouse(String userId, String rent, int startPage, int pageSize);

    public int deleteHouse(String id);

    public int updateHouse(House bean);

    public int findTotalHouse();

}
