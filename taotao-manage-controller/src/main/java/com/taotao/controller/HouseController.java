package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taotao.entity.House;
import com.taotao.entity.PictureResource;
import com.taotao.entity.Result;
import com.taotao.service.HouseService;
import com.taotao.service.PictureResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin
public class HouseController {

    @Reference(version = "1.0")
    private HouseService houseService;

    @Reference(version = "1.0")
    private PictureResourceService pictureResourceService;

    @RequestMapping("/add/house")
    @ResponseBody
    @Transactional
    public Result insertHouse(@RequestBody String house, @RequestParam("pIds")String pIds) {
        House bean = JSON.parseObject(house, new TypeReference<House>() {});
        bean.setCreateTime(new Date());
        bean.setId(UUID.randomUUID().toString());
        int result = houseService.insertHouse(bean);

        this.savePictureResource(pIds, bean);

        if (result == 0) {
            return new Result("500", false, "添加失败");
        } else {
            return new Result("200", true, "添加成功");
        }
    }

    @RequestMapping("/find/house")
    @ResponseBody
    public Map<String, Object> findHouse(@RequestParam("userId")String userId,
                                 @RequestParam("startPage")int startPage, @RequestParam("pageSize")int pageSize) {
        int total = houseService.findTotalHouse();
        List<House> beans = houseService.findHouse(userId, null, startPage, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("house", beans);
        return result;
    }

    @RequestMapping("/find/index/house")
    @ResponseBody
    public List<House> findIndexHouse(@RequestParam("rent")String rent) {
        return houseService.findHouse(null, rent, 0, 6);
    }

    @RequestMapping("/delete/house")
    @ResponseBody
    public Result deleteHouse(@RequestParam("id")String id) {
        int result = houseService.deleteHouse(id);
        if (result == 0) {
            return new Result("500", false, "删除失败");
        } else {
            return new Result("200", true, "删除成功");
        }
    }

    @RequestMapping("/update/house")
    @ResponseBody
    public Result updateHouse(@RequestBody String house, @RequestParam("pIds")String pIds) {
        House bean = JSON.parseObject(house, new TypeReference<House>() {});
        this.savePictureResource(pIds, bean);

        int result = houseService.updateHouse(bean);
        if (result == 0) {
            return new Result("500", false, "更新失败");
        } else {
            return new Result("200", true, "更新成功");
        }
    }

    private void savePictureResource(String pIds, House bean) {
        if (pIds != null && !("".equals(pIds))) {
            List<PictureResource> pr = new ArrayList<>();
            pIds = pIds.substring(0, pIds.length() - 1);
            String[] savePIds = pIds.split(",");
            for (int i = 0; i < savePIds.length; i++) {
                PictureResource pictureResource = new PictureResource();
                pictureResource.setId(UUID.randomUUID().toString());
                pictureResource.setFileId(savePIds[i]);
                pictureResource.setRelatedObjectId(bean.getId());
                pictureResource.setRelatedObjectType("house");
                pr.add(pictureResource);
            }
            pictureResourceService.insertPictureResource(pr);
        }
    }

}
