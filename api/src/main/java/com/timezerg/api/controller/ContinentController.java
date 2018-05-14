package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.CivilizationService;
import com.timezerg.api.service.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@CrossOrigin
@RestController
public class ContinentController {

    @Autowired
    ContinentService continentService;

    @PostMapping("/continent/save")
    public Object save(@RequestBody JSONObject params){
        return continentService.add(params);
    }



}
