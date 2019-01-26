package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.dao.PictureResourceDao;
import com.taotao.entity.PictureResource;
import com.taotao.service.PictureResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0")
public class PictureResourceServiceImpl implements PictureResourceService {

    @Autowired
    private PictureResourceDao pictureResourceDao;

    @Override
    public int insertPictureResource(List<PictureResource> pr) {
        return pictureResourceDao.insertPictureResource(pr);
    }
}
