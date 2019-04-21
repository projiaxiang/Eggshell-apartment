package com.taotao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangJiaCheng
 * @create 2019-04-21 15:19
 */

@Data
public class Order implements Serializable {

    private int id;

    private String houseId;

    private String number;

    private int userId;

    private Date createTime;

    private House house;

}
