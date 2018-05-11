package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.CivilizationService;
import com.timezerg.api.service.DynastyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class DynastyController {

    @Autowired
    DynastyService dynastyService;

    @CrossOrigin
    @PostMapping("/dynasty/save")
    public Object save(@RequestBody JSONObject params){
        return dynastyService.add(params);
    }

//    @CrossOrigin
//    @PostMapping("/civilization/list")
//    public Object list(@RequestBody JSONObject params){
//        return nodeService.getList(params);
//    }



}
