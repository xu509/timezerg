
package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.RelationService;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class RelationController {

    @Autowired
    RelationService relationService;

    @CrossOrigin
    @PostMapping("/relation/list")
    public Object list(@RequestBody JSONObject params){
        return relationService.getList(params);
    }

    @CrossOrigin
    @PostMapping("/relation/edit/init")
    public Object search(@RequestBody JSONObject params){
        return new Result(ResultMessage.OK);
//        return institutionService.selectByTitle(params);
    }


}
