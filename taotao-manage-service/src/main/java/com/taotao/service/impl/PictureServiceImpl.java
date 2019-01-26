package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.dao.PictureDao;
import com.taotao.entity.Picture;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0")
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public int insertFile(Picture bean) {
        return pictureDao.insertFile(bean);
    }
}
