package com.taotao.dao;

import com.taotao.entity.PictureResource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PictureResourceDao {

    @Insert("<script>" +
            "insert into file_resource value" +
            "<foreach collection=\"pr\" index=\"index\" item=\"item\" separator=\",\">" +
            "(#{item.id}, #{item.fileId}, #{item.relatedObjectType}, #{item.relatedObjectId})" +
            "</foreach>" +
            "</script>")
    int insertPictureResource(@Param("pr")List<PictureResource> pr);

}
