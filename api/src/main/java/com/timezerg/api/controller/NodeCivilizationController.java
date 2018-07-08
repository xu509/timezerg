package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NodeCivilizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class NodeCivilizationController {

    @Autowired
    NodeCivilizationService nodeCivilizationService;

    @CrossOrigin
    @DeleteMapping("/nodecivilization/")
    public Object nodecivilization(@RequestBody JSONObject params) {
        String id = params.getString("id");
        return nodeCivilizationService.delete(id);
    }

    @CrossOrigin
    @PostMapping("/nodecivilization/update/level")
    public Object nodecivilizationUpdateLevel(@RequestBody JSONObject params) {
        String id = params.getString("id");
        return nodeCivilizationService.changeLevel(id);
    }

}
