package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.GiantMapper;
import com.timezerg.api.mapper.GiantNationMapper;
import com.timezerg.api.mapper.InstitutionMapper;
import com.timezerg.api.mapper.NationMapper;
import com.timezerg.api.model.Giant;
import com.timezerg.api.model.GiantNation;
import com.timezerg.api.model.Institution;
import com.timezerg.api.model.Nation;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 * 制度
 */
@Service
public class InstitutionService {

    @Autowired
    InstitutionMapper institutionMapper;

    @Transactional
    public Object add(JSONObject params) {
        Institution institution = new Institution();
        institution.setId(Utils.generateId());
        institution.setTitle(params.getString("title"));
        institution.setContent(params.getString("content"));
        institution.setType(params.getInteger("type"));
        institutionMapper.add(institution);
        return new Result(ResultMessage.OK, institution);
    }

    public Object getList(JSONObject params) {
        Object[] p = {params.getInteger("start"), params.getInteger("size")};
        List<HashMap> list = institutionMapper.getList(p);
        JSONObject r = new JSONObject();
        r.put("data", list);
        r.put("total", institutionMapper.getListTotal(p));
        return new Result(ResultMessage.OK, r);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Institution> institutions = institutionMapper.selectLikeByTitle(title);

        JSONArray institutionAry = new JSONArray();
        for (Institution institution : institutions){
            JSONObject object = (JSONObject) JSON.toJSON(institution);
            object.put("iid",institution.getId());
            institutionAry.add(object);
        }



        r.put("exist", institutions.size() > 0);
        r.put("data", institutionAry);

        return new Result(ResultMessage.OK, r);
    }

}
