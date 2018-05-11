package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class NodeController {

    @Autowired
    NodeService nodeService;

    @CrossOrigin
    @PostMapping("/node/save")
    public Object save(@RequestBody JSONObject params){
        return nodeService.add(params);
    }

    @CrossOrigin
    @PostMapping("/node/list")
    public Object list(@RequestBody JSONObject params){
        return nodeService.getList(params);
    }


    @GetMapping("/test")
    public Object test(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",true);
        jsonObject.put("msg","new message");
        return jsonObject;
    }

}
