package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class NodeController {

    @Autowired
    NodeService nodeService;


//    @PostMapping
//    public Object tableList(){
//
//    }

    @GetMapping("/test")
    public Object test(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",true);
        jsonObject.put("msg","new message");
        return jsonObject;
    }

}
