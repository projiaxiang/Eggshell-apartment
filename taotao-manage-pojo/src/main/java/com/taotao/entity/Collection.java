package com.taotao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Collection implements Serializable {

    private String id;
    private String userId;
    private String houseId;
    private Date createTime;

    private House house;

}
