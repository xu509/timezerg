package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.GiantService;
import com.timezerg.api.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class GiantController {

    @Autowired
    GiantService giantService;

    @CrossOrigin
    @PostMapping("/giant/save")
    public Object save(@RequestBody JSONObject params){
        return giantService.add(params);
    }

    @CrossOrigin
    @PostMapping("/giant/search")
    public Object search(@RequestBody JSONObject params){
        return giantService.selectByTitle(params);
    }

}
