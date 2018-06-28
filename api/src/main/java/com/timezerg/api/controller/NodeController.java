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
    @PostMapping("/node/search")
    public Object search(@RequestBody JSONObject params) {
        return nodeService.selectByTitle(params);
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

    @CrossOrigin
    @PostMapping("/api/node/detail")
    public Object nodeDetailApi(@RequestBody JSONObject params){
        return nodeService.detail(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/init/relate")
    public Object editInitRelate(@RequestBody JSONObject params){
        return nodeService.initRelate(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/relate/giant/save")
    public Object editRelateGiantSave(@RequestBody JSONObject params){
        return nodeService.addRelateGiant(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/relate/giant/delete")
    public Object editRelateGiantDelete(@RequestBody JSONObject params){
        return nodeService.deleteRelateGiant(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/relate/nation/save")
    public Object editRelateNationSave(@RequestBody JSONObject params){
        return nodeService.addRelateNation(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/relate/nation/delete")
    public Object editRelateNationDelete(@RequestBody JSONObject params){
        return nodeService.deleteRelateNation(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/relate/reference/save")
    public Object editRelateReferenceSave(@RequestBody JSONObject params){
        return nodeService.addRelateReference(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/relate/reference/delete")
    public Object editRelateReferenceDelete(@RequestBody JSONObject params){
        return nodeService.deleteRelateReference(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/init/tag")
    public Object editInitTag(@RequestBody JSONObject params){
        return nodeService.initTag(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/tag/save")
    public Object editTagSave(@RequestBody JSONObject params){
        return nodeService.addTag(params);
    }

    @CrossOrigin
    @PostMapping("/node/edit/tag/delete")
    public Object editTagDelete(@RequestBody JSONObject params){
        return nodeService.deleteTag(params);
    }

    @CrossOrigin
    @GetMapping("/node/test")
    public Object test(@RequestBody JSONObject params) {
        System.out.println("!11");
        return new Result(ResultMessage.OK);
//        return nodeService.add(params);
    }


}
