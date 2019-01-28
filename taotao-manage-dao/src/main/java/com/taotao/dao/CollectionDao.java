package com.taotao.dao;

import com.taotao.entity.Collection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectionDao {

    @Insert("insert into collection values(#{id}, #{houseId}, #{userId})")
    int insertCollection(Collection bean);

}
