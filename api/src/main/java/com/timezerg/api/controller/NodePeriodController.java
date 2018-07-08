package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NodePeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class NodePeriodController {

    @Autowired
    NodePeriodService nodePeriodService;

    @CrossOrigin
    @DeleteMapping("/nodeperiod/")
    public Object nodeperiod(@RequestBody JSONObject params) {
        String id = params.getString("id");
        return nodePeriodService.delete(id);
    }

    @CrossOrigin
    @PostMapping("/nodeperiod/update/level")
    public Object nodeperiodUpdateLevel(@RequestBody JSONObject params) {
        String id = params.getString("id");
        return nodePeriodService.changeLevel(id);
    }

}
