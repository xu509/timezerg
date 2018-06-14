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
    @PostMapping("/nation/edit/init/relate")
    public Object editInitRelate(@RequestBody JSONObject params){
        return nationService.editInitRelate(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/relate/giant/save")
    public Object editRelateGiantSave(@RequestBody JSONObject params){
        return nationService.addRelateGiant(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/relate/giant/delete")
    public Object editRelateGiantDelete(@RequestBody JSONObject params){
        return nationService.deleteRelateGiant(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/relate/institution/save")
    public Object editRelateInstitutionSave(@RequestBody JSONObject params){
        return nationService.addRelateInstitution(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/relate/institution/delete")
    public Object editRelateInstitutionDelete(@RequestBody JSONObject params){
        return nationService.deleteRelateInstitution(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/relate/reference/save")
    public Object editRelateReferenceSave(@RequestBody JSONObject params){
        return nationService.addRelateReference(params);
    }

    @CrossOrigin
    @PostMapping("/nation/edit/relate/reference/delete")
    public Object editRelateReferenceDelete(@RequestBody JSONObject params){
        return nationService.deleteRelateReference(params);
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
