package com.taotao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class House implements Serializable {

    private String id;
    private String title;
    private String heating;
    private String isFirst;
    private String size;
    private String apartment;
    private String subway;
    private String direction;
    private String floor;
    private String money;
    private String introduction;
    private Date createTime;
    private String configure;
    private String rent;
    private String province;
    private String city;
    private String county;
    private String userId;

    List<Picture> picture;

}
