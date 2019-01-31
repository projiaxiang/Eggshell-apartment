package com.taotao.dao;

import com.taotao.entity.Collection;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CollectionDao {

    @Insert("insert into collection values(#{id}, #{houseId}, #{userId})")
    int insertCollection(Collection bean);

    @Select("<script>" +
            "select * from collection where 1 = 1 " +
            "<if test=\"userId !=null \"> and userId = #{userId} </if> " +
            "<if test=\"houseId !=null \"> and houseId = #{houseId} </if> " +
            "</script>")
    @Results({
            @Result(column = "houseId", property = "house",
                    one = @One(
                            select = "com.taotao.dao.HouseDao.findHouseById"
                    ))
    })
    List<Collection> selectCollectionByUserId(Map<String, Object> map);

}
