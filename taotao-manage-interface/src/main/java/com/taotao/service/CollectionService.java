package com.taotao.service;

import com.taotao.entity.Collection;

import java.util.List;
import java.util.Map;

public interface CollectionService {

    int insertCollection(Collection bean);

    List<Collection> selectCollectionByUserId(Map<String, Object> map);

    int findTotalHouse(Map<String, Object> map);

    int deleteCollection(String houseId, String userId);

}
