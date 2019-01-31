package com.taotao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Collection implements Serializable {

    private String id;
    private String userId;
    private String houseId;

    private House house;

}
