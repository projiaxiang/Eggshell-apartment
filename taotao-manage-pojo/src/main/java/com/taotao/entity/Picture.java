package com.taotao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Picture implements Serializable {

    private String id;
    private String name;
    private String type;
    private Long size;


    public Picture(String id, String name, String type, Long size) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
    }
}
