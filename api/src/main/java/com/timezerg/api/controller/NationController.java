package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.NationService;
import com.timezerg.api.service.NodeService;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
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

    @CrossOrigin
    @PostMapping("/nation/edit/init/tag")
    public Object editInitTag(@RequestBody JSONObject params){
        return nationService.editInitTag(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/save")
    public Object edit(@RequestBody JSONObject params){
        return nationService.edit(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/tag/save")
    public Object editTagSave(@RequestBody JSONObject params){
        return nationService.addTag(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/tag/delete")
    public Object editTagDelete(@RequestBody JSONObject params){
        return nationService.deleteTag(params);
    }

}
