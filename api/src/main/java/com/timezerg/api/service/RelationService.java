package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
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


        if (!StringUtils.isBlank(relation.getTid())){
            Relation tRelation = relationMapper.selectById(relation.getTid());
            if (tRelation == null)
                return new Result(ResultMessage.PARAM_ERROR);

            // 已经存在关系，则删除关系
            if (!StringUtils.isBlank(tRelation.getTitle())){
                return new Result(ResultMessage.PARAM_ERROR,tRelation.getTitle() + " 已经存在对应关系。");
            }

            tRelation.setTid(relation.getId());
            relationMapper.update(tRelation);
        }

        relationMapper.add(relation);
        return new Result(ResultMessage.OK,relation);
    }

    @Transactional
    public Object update(Relation relation){
        if (relation == null)
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());

        if (StringUtils.isBlank(relation.getId()) || relationMapper.selectById(relation.getId()) == null)
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());

        Relation oRelation = relationMapper.selectById(relation.getId());
        if (!oRelation.getTitle().equals(relation.getTitle())){
            if (relationMapper.selectByTitle(relation.getTitle()) != null){
                return new Result(ResultMessage.DUPLICATION_ERROR);
            }
        }

        if (!StringUtils.isBlank(oRelation.getTid())){
            Relation relation1 = relationMapper.selectById(oRelation.getTid());
            relation1.setTid(null);
            relationMapper.update(relation1);
        }

        if (!StringUtils.isBlank(relation.getTid())){
            Relation tRelation = relationMapper.selectById(relation.getTid());
            if (tRelation == null)
                return new Result(ResultMessage.PARAM_ERROR);

            // 已经存在关系，则删除关系
            if (!StringUtils.isBlank(tRelation.getTid())){
                return new Result(ResultMessage.PARAM_ERROR,tRelation.getTitle() + " 已经存在对应关系。");
            }

            tRelation.setTid(relation.getId());
            relationMapper.update(tRelation);
        }

        relationMapper.update(relation);
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

    @Transactional
    public Object editInit(JSONObject params){
        String id = params.getString("id");
        Relation relation = relationMapper.selectById(id);
        if (relation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject obj = (JSONObject) JSON.toJSON(relation);
        obj.put("relations",relationMapper.selectAll());

        Relation tRelation = relationMapper.selectById(relation.getTid());
        obj.put("trelation",tRelation);

        return new Result(ResultMessage.OK,obj);
    }

    @Transactional
    public Object editSave(JSONObject params){
        String id = params.getString("id");
        Relation relation = relationMapper.selectById(id);
        if (relation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        relation.setTitle(params.getString("title"));

        String tid = params.getString("tid");
        if (tid.length() == 18 && relationMapper.selectById(tid) != null){
            relation.setTid(tid);
        } else {
            Relation relation1 = new Relation();
            relation1.setTitle(tid);
            tid = Utils.generateId();
            relation1.setId(tid);
            Result r1 = (Result) add(relation1);
            if (!Result.isOk(r1)){
                return new Result(ResultMessage.PARAM_ERROR);
            }
        }

        relation.setTid(tid);
        return update(relation);
    }

}
