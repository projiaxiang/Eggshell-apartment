package com.taotao.service;

import com.taotao.entity.Collection;

import java.util.List;
import java.util.Map;

public interface CollectionService {

    public int insertCollection(Collection bean);

    public List<Collection> selectCollectionByUserId(Map<String, Object> map);

}
