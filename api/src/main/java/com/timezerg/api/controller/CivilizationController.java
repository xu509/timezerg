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

    @PostMapping("/civilization/index")
    public Object index(@RequestBody JSONObject params){
        return civilizationService.index(params);
    }

    @PostMapping("/civilization/edit/init/nodes")
    public Object editInitNodes(@RequestBody JSONObject params){
        return civilizationService.editInitNodes(params);
    }

    @PostMapping("/civilization/edit/nodes/updatelevel")
    public Object updateNodeLevel(@RequestBody JSONObject params){
        return civilizationService.updateNodeLevel(params);
    }

    @PostMapping("/civilization/edit/nodes/sync")
    public Object syncNode(@RequestBody JSONObject params){
        return civilizationService.syncNode(params);
    }


    @PostMapping("/civilization/edit/init/relate")
    public Object editInitRelate(@RequestBody JSONObject params){
        return civilizationService.editInitRelate(params);
    }

    /**
     *  同步索引
     */
    @PostMapping("/civilization/relate/period/sync")
    public Object syncSort(@RequestBody JSONObject params){
        return civilizationService.syncCivilizationPeriod(params);
    }

    /**
     *  交换位置
     */
    @PostMapping("/civilization/relate/period/exchange")
    public Object exchange(@RequestBody JSONObject params){
        return civilizationService.exchangePeriod(params);
    }

    /**
     *  添加
     */
    @PostMapping("/civilization/relate/period/save")
    public Object relatePeriodSave(@RequestBody JSONObject params){
        return civilizationService.savePeriod(params);
    }

    /**
     *  删除
     */
    @PostMapping("/civilization/relate/period/delete")
    public Object relatePeriodDelete(@RequestBody JSONObject params){
        return civilizationService.deletePeriod(params);
    }

    /**
     *  api
     */
    @PostMapping("/api/civilization/list")
    public Object apiGetList(@RequestBody JSONObject params){
        return civilizationService.getApiList(params);
    }




}
