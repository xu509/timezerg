package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NodeNationService;
import com.timezerg.api.service.NodeService;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class NodeNationController {

    @Autowired
    NodeNationService nodeNationService;

    @CrossOrigin
    @DeleteMapping("/nodenation/")
    public Object nodenation(@RequestBody JSONObject params) {
        String id = params.getString("id");
        return nodeNationService.delete(id);
    }



}
