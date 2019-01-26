package com.taotao.util;

import com.taotao.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadUtil {

    public static String uploadImage(MultipartFile picture, String savePath) {
        if (picture.isEmpty()) {
            return "404";
        }

        //获取文件在服务器的储存位置
        File filePath = new File(savePath);

        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }

        String originalFileName = picture.getOriginalFilename();

        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + "." + type;

        //在指定路径下创建一个文件
        File targetFile = new File(savePath, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "500";
        }
    }

}
