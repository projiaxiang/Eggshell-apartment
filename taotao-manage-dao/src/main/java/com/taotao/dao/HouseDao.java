package com.taotao.dao;

import com.taotao.entity.House;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseDao {

    @Insert("insert into house values(#{id}, #{title}, #{heating}, #{isFirst}, #{size}" +
            ", #{apartment}, #{subway}, #{direction}, #{floor}, #{money}, #{introduction}" +
            ", #{createTime}, #{configure}, #{rent}, #{province}, #{city}, #{county}, #{userId})")
    int insertHouse(House bean);

    @Select("<script>" +
            "select * from house where 1 = 1 " +
            "<if test=\"userId !=null \"> and userId = #{userId} </if> " +
            "<if test=\"rent != 'all' \"> and rent = #{rent} </if> " +
            "<if test=\"id !=null \"> and id = #{id} </if> " +
            "<if test=\"address != 'all' \"> and (title like #{address} or city like #{address}) </if> " +
            "order by createTime desc limit #{startPage}, #{pageSize}" +
            "</script>")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(column = "id", property = "picture",
                many = @Many(
                        select = "com.taotao.dao.PictureDao.findPictureByHouseId"
            ))
    })
    List<House> findHouse(Map<String, Object> house);

    @Select("<script>" +
     "select count(*) as total from house where 1 = 1 " +
     "<if test=\"rent != 'all' \"> and rent = #{rent} </if> " +
     "<if test=\"address != 'all' \"> and (title like #{address} or city like #{address}) </if> " +
     "</script>")
    int countHouseByAddress(Map<String, Object> house);

    @Select("select * from house where id = #{houseId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(column = "id", property = "picture",
                    many = @Many(
                            select = "com.taotao.dao.PictureDao.findPictureByHouseId"
                    ))
    })
    House findHouseById(@Param("houseId")String houseId);

    @Select("select count(*) as total from house")
    int findTotalHouse();

    @Delete("delete from house where id = #{id}")
    int deleteHouse(@Param("id")String id);

    @Update("update house set title = #{title}, heating = #{heating}, isFirst = #{isFirst}" +
            ", size = #{size}, apartment = #{apartment}, subway = #{subway}" +
            ", direction = #{direction}, floor = #{floor}, money = #{money}, introduction = #{introduction}" +
            ", configure = #{configure}, rent = #{rent}, province = #{province}" +
            ", city = #{city}, county = #{county}, userId = #{userId} where id = #{id}")
    int updateHouse(House bean);

}
