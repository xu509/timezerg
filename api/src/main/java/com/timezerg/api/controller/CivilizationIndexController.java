package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.CivilizationIndexService;
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
public class CivilizationIndexController {

    @Autowired
    CivilizationIndexService civilizationIndexService;

    @PostMapping("/civilizationindex/list")
    public Object list(@RequestBody JSONObject params){
        return civilizationIndexService.getList();
    }

    @PostMapping("/civilizationindex/add")
    public Object add(@RequestBody JSONObject params){
        return civilizationIndexService.add(params.getString("cid"));
    }

    @PostMapping("/civilizationindex/del")
    public Object del(@RequestBody JSONObject params){
        return civilizationIndexService.del(params.getString("id"));
    }

}
