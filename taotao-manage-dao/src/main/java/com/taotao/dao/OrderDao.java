package com.taotao.dao;

import com.taotao.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author WangJiaCheng
 * @create 2019-04-21 15:21
 */

@Mapper
public interface OrderDao {

    @Insert("INSERT INTO `order` (houseId, number, userId, createTime)" +
            " values(#{houseId}, #{number}, #{userId}, #{createTime})")
    int insertOrder(Order bean);

    @Select("<script>" +
            "select * from `order` where 1 = 1 " +
            "<if test=\"userId !=null \"> and userId = #{userId} </if> " +
            "<if test=\"houseId !=null \"> and houseId = #{houseId} </if> " +
            "order by createTime desc limit #{startPage}, #{pageSize}" +
            "</script>")
    @Results({
            @Result(column = "houseId", property = "houseId"),
            @Result(column = "houseId", property = "house",
                    one = @One(
                            select = "com.taotao.dao.HouseDao.findHouseById"
                    ))
    })
    List<Order> selectOrderByUserId(Map<String, Object> map);

    @Select("select count(*) as total from `order` where userId = #{userId}")
    int findTotalHouse(Map<String, Object> map);

    @Delete("delete from `order` where houseId = #{houseId} and userId = #{userId}")
    int deleteOrder(@Param("houseId")String houseId, @Param("userId")String userId);

}
