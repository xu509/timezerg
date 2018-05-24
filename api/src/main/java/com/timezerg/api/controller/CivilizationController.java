package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.config.AppConfig;
import com.timezerg.api.service.CivilizationService;
import com.timezerg.api.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by xnx on 2018/5/10.
 */

@CrossOrigin
@RestController
public class CivilizationController {

    @Autowired
    CivilizationService civilizationService;

    @PostMapping("/civilization/save")
    public Object save(@RequestBody JSONObject params){
        return civilizationService.add(params);
    }

    @PostMapping("/civilization/list")
    public Object list(@RequestBody JSONObject params){
        return civilizationService.getList(params);
    }

    @PostMapping("/civilization/search")
    public Object search(@RequestBody JSONObject params){
        return civilizationService.selectByTitle(params);
    }

    @PostMapping("/civilization/edit/init")
    public Object editInit(@RequestBody JSONObject params){
        return civilizationService.editInit(params);
    }

    @PostMapping("/civilization/edit/save")
    public Object editSave(@RequestBody JSONObject params){
        return civilizationService.edit(params);
    }

    @PostMapping("/civilization/timeline")
    public Object timeLine(@RequestBody JSONObject params){
        return civilizationService.timeLine(params);
    }

    @PostMapping("/civilization/upload")
    public Object uploadCover(@RequestParam("file") MultipartFile file) throws IOException {
        String filetype = FileUtil.getExtension(file.getOriginalFilename());
        String fileName = Utils.generateId() + filetype;
        File temp = FileUtil.convert(file);

        QCloudUtil.getInstance().uploadFile(temp,AppConfig.IMG_FOLDER.CIVILIZATION + fileName);

        JSONObject obj = new JSONObject();
        obj.put("path",AppConfig.IMG_FOLDER.CIVILIZATION + fileName);
        obj.put("domain",AppConfig.Image_Domain);


        return new Result(ResultMessage.OK,obj);
    }

    @PostMapping("/civilization/first")
    public Object first(@RequestBody JSONObject params){
        return civilizationService.setFirstSort(params);
    }

    @PostMapping("/civilization/down")
    public Object lastest(@RequestBody JSONObject params){
        return civilizationService.setDownSort(params);
    }

}
