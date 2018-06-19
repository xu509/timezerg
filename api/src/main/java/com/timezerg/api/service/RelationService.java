package com.timezerg.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.RelationMapper;
import com.timezerg.api.model.Relation;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class RelationService {

    @Autowired
    RelationMapper relationMapper;

    @Transactional
    public Object add(Relation relation){
        if (relation == null)
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());

        if (relationMapper.selectByTitle(relation.getTitle()) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(relation.getId()))
            relation.setId(Utils.generateId());

        relationMapper.add(relation);
        return new Result(ResultMessage.OK,relation);
    }

    @Transactional
    public Object getList(JSONObject params){

        Integer start = params.getInteger("start");
        Integer size = params.getInteger("size");

        List<HashMap> list;
        Long total;


        list = relationMapper.getList(new Object[]{start,size});
        total = relationMapper.getTotal();

        JSONObject r = new JSONObject();
        r.put("data", list);
        r.put("total", total);
        return new Result(ResultMessage.OK, r);
    }

}
