package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.dao.CollectionDao;
import com.taotao.entity.Collection;
import com.taotao.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionDao collectionDao;

    @Override
    public int insertCollection(Collection bean) {
        return collectionDao.insertCollection(bean);
    }
}
