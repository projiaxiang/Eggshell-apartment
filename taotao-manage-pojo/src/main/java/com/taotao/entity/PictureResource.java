package com.taotao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PictureResource implements Serializable {

    private String id;
    private String fileId;
    private String relatedObjectId;
    private String relatedObjectType;

    private List<Picture> picture;

}
