package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @CrossOrigin
    @PostMapping("/tag/search")
    public Object search(@RequestBody JSONObject params){
        return tagService.selectByTitle(params);
    }

}
