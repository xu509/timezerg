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

    @CrossOrigin
    @PostMapping("/giant/list")
    public Object list(@RequestBody JSONObject params){
        return giantService.getList(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/init")
    public Object editInit(@RequestBody JSONObject params){
        return giantService.editInit(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/save")
    public Object editSave(@RequestBody JSONObject params){
        return giantService.editSave(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/init/relate")
    public Object editInitRelate(@RequestBody JSONObject params){
        return giantService.editInitRelate(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/relate/nation/save")
    public Object editAddNation(@RequestBody JSONObject params){
        return giantService.editAddNation(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/relate/nation/delete")
    public Object editDeleteNation(@RequestBody JSONObject params){
        return giantService.editDeleteNation(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/relate/reference/save")
    public Object editAddReference(@RequestBody JSONObject params){
        return giantService.editAddReference(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/relate/reference/delete")
    public Object editDeleteReference(@RequestBody JSONObject params){
        return giantService.editDeleteReference(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/relate/node/save")
    public Object editAddNode(@RequestBody JSONObject params){
        return giantService.editAddNode(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/relate/node/delete")
    public Object editDeleteNode(@RequestBody JSONObject params){
        return giantService.editDeleteNode(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/init/tag")
    public Object editInitTag(@RequestBody JSONObject params){
        return giantService.editInitTag(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/tag/save")
    public Object editAddTag(@RequestBody JSONObject params){
        return giantService.editAddTag(params);
    }

    @CrossOrigin
    @PostMapping("/giant/edit/tag/delete")
    public Object editDeleteTag(@RequestBody JSONObject params){
        return giantService.editDeleteTag(params);
    }
}
