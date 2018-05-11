package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.CivilizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class CivilizationController {

    @Autowired
    CivilizationService civilizationService;

    @CrossOrigin
    @PostMapping("/civilization/save")
    public Object save(@RequestBody JSONObject params){
        return civilizationService.add(params);
    }

//    @CrossOrigin
//    @PostMapping("/civilization/list")
//    public Object list(@RequestBody JSONObject params){
//        return nodeService.getList(params);
//    }



}
