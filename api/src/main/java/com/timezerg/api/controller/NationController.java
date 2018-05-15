package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NationService;
import com.timezerg.api.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class NationController {

    @Autowired
    NationService nationService;

    @CrossOrigin
    @PostMapping("/nation/save")
    public Object save(@RequestBody JSONObject params){
        return nationService.add(params);
    }

    @CrossOrigin
    @PostMapping("/nation/list")
    public Object list(@RequestBody JSONObject params){
        return nationService.getList(params);
    }

    @CrossOrigin
    @PostMapping("/nation/search")
    public Object search(@RequestBody JSONObject params){
        return nationService.selectByTitle(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/init")
    public Object editInit(@RequestBody JSONObject params){
        return nationService.editInit(params);
    }

}
