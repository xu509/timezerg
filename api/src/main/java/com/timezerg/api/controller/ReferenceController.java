package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class ReferenceController {

    @Autowired
    ReferenceService referenceService;

    @CrossOrigin
    @PostMapping("/reference/save")
    public Object save(@RequestBody JSONObject params) {
        return referenceService.add(params);
    }

    @CrossOrigin
    @PostMapping("/reference/list")
    public Object list(@RequestBody JSONObject params) {
        return referenceService.getList(params);
    }

    @CrossOrigin
    @PostMapping("/reference/search")
    public Object search(@RequestBody JSONObject params) {
        return referenceService.selectByTitle(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/init")
    public Object editInit(@RequestBody JSONObject params) {
        return referenceService.editInit(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/save")
    public Object edit(@RequestBody JSONObject params) {
        return referenceService.editSaveBasic(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/init/relate")
    public Object editInitRelate(@RequestBody JSONObject params) {
        return referenceService.editInitRelate(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/giant/save")
    public Object editRelateGiantSave(@RequestBody JSONObject params) {
        return referenceService.editAddRelateGiant(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/giant/delete")
    public Object editRelateGiantDelete(@RequestBody JSONObject params) {
        return referenceService.editDeleteRelateGiant(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/node/save")
    public Object editRelateNodeSave(@RequestBody JSONObject params) {
        return referenceService.editAddRelateNode(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/node/delete")
    public Object editRelateNodeDelete(@RequestBody JSONObject params) {
        return referenceService.editDeleteRelateNode(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/period/save")
    public Object editRelatePeriodSave(@RequestBody JSONObject params) {
        return referenceService.editAddRelatePeriod(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/period/delete")
    public Object editRelatePeriodDelete(@RequestBody JSONObject params) {
        return referenceService.editDeleteRelatePeriod(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/nation/save")
    public Object editRelateNationSave(@RequestBody JSONObject params) {
        return referenceService.editAddRelateNation(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/relate/nation/delete")
    public Object editRelateNationDelete(@RequestBody JSONObject params) {
        return referenceService.editDeleteRelateNation(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/init/tag")
    public Object editInitTag(@RequestBody JSONObject params) {
        return referenceService.editInitTag(params);
    }


    @CrossOrigin
    @PostMapping("/reference/edit/tag/save")
    public Object editTagSave(@RequestBody JSONObject params) {
        return referenceService.editAddTag(params);
    }

    @CrossOrigin
    @PostMapping("/reference/edit/tag/delete")
    public Object editTagDelete(@RequestBody JSONObject params) {
        return referenceService.editDeleteTag(params);
    }


}
