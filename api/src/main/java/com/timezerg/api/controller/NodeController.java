package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NodeService;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
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
    public Object save(@RequestBody JSONObject params) {
        return nodeService.add(params);
    }

    @CrossOrigin
    @PostMapping("/node/list")
    public Object list(@RequestBody JSONObject params){
        return nodeService.getList(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/init")
    public Object editInit(@RequestBody JSONObject params){
        return nodeService.init(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/submit")
    public Object editSubmit(@RequestBody JSONObject params){
        return nodeService.edit(params);
    }

    @GetMapping("/test")
    public Object test(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",true);
        jsonObject.put("msg","new message");
        return jsonObject;
    }

    @CrossOrigin
    @PostMapping("/api/node/detail")
    public Object nodeDetailApi(@RequestBody JSONObject params){
        return nodeService.detail(params);
    }

}
