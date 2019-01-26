package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.entity.Picture;
import com.taotao.service.PictureService;
import com.taotao.util.UploadUtil;
import com.taotao.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@CrossOrigin
public class PictureController {

    @Value("${house.imagePath}")
    private String savePath;

    @Reference(version = "1.0")
    private PictureService pictureService;

    @RequestMapping("/upload")
    @Transactional
    public Result upload(@RequestParam("picture") MultipartFile image) {
        String result = UploadUtil.uploadImage(image, savePath);
        if (result == "404") {
            return new Result("404", false, "文件不能为空");
        } else if (result == "500") {
            return new Result("500", false, "文件上传失败");
        } else {
            Picture bean = new Picture(UUID.randomUUID().toString(), result, result.substring(result.lastIndexOf(".") + 1), image.getSize());
            int id = pictureService.insertFile(bean);
            if (id == 0) {
                return new Result("500", false, "文件上传失败");
            } else {
                return new Result("200", true, bean.getId());
            }
        }
    }

}
