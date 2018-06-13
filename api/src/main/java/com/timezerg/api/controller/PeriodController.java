package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class PeriodController {

    @Autowired
    PeriodService periodService;

    @CrossOrigin
    @PostMapping("/period/save")
    public Object save(@RequestBody JSONObject params) {
        return periodService.add(params);
    }

    @CrossOrigin
    @PostMapping("/period/list")
    public Object list(@RequestBody JSONObject params) {
        return periodService.getList(params);
    }

    @CrossOrigin
    @PostMapping("/period/search")
    public Object search(@RequestBody JSONObject params) {
        return periodService.selectByTitle(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/init")
    public Object editInit(@RequestBody JSONObject params) {
        return periodService.editInit(params);
    }


    @CrossOrigin
    @PostMapping("/period/edit/init/relate")
    public Object editInitRelate(@RequestBody JSONObject params) {
        return periodService.editInitRelate(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/relate/civilization/save")
    public Object editRelateAdd(@RequestBody JSONObject params) {
        return periodService.editAddRelateCivilization(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/relate/civilization/delete")
    public Object editRelateDelete(@RequestBody JSONObject params) {
        return periodService.editDeleteRelateCivilization(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/relate/nation/save")
    public Object editNationAdd(@RequestBody JSONObject params) {
        return periodService.editAddRelateNation(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/relate/nation/delete")
    public Object editNationDelete(@RequestBody JSONObject params) {
        return periodService.editDeleteRelateNation(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/init/tag")
    public Object editInitTag(@RequestBody JSONObject params) {
        return periodService.editInitTags(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/tag/save")
    public Object editTagAdd(@RequestBody JSONObject params) {
        return periodService.editAddTag(params);
    }

    @CrossOrigin
    @PostMapping("/period/edit/tag/delete")
    public Object editTagDelete(@RequestBody JSONObject params) {
        return periodService.editDeleteTag(params);
    }



    @CrossOrigin
    @PostMapping("/period/edit/save")
    public Object edit(@RequestBody JSONObject params) {
        return periodService.edit(params);
    }


}
