package com.taotao.dao;

import com.taotao.entity.Picture;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PictureDao {

    @Insert("insert into file(id, name, type, size) values(#{id}, #{name}, #{type}, #{size})")
    int insertFile(Picture bean);

    @Select("select b.* from file_resource a " +
            "left join file b on a.fileId = b.id " +
            "where a.relatedObjectId = #{relatedObjectId}")
    List<Picture> findPictureByHouseId(@Param("id")String relatedObjectId);

}
