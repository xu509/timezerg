
package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.InstitutionService;
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
public class InstitutionController {

    @Autowired
    InstitutionService institutionService;

    @CrossOrigin
    @PostMapping("/institution/save")
    public Object save(@RequestBody JSONObject params){
        return institutionService.add(params);
    }

    @CrossOrigin
    @PostMapping("/institution/list")
    public Object list(@RequestBody JSONObject params){
        return institutionService.getList(params);
    }

    @CrossOrigin
    @PostMapping("/institution/search")
    public Object search(@RequestBody JSONObject params){
        return institutionService.selectByTitle(params);
    }

//    @CrossOrigin
//    @PostMapping("/institution/edit/init")
//    public Object editInit(@RequestBody JSONObject params){
//        return nationService.editInit(params);
//    }
//
//    @CrossOrigin
//    @PostMapping("/institution/edit/save")
//    public Object edit(@RequestBody JSONObject params){
//        return nationService.edit(params);
//    }

}
