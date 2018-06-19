package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.ContinentMapper;
import com.timezerg.api.mapper.GiantMapper;
import com.timezerg.api.mapper.GiantRelationMapper;
import com.timezerg.api.mapper.RelationMapper;
import com.timezerg.api.model.Continent;
import com.timezerg.api.model.GiantRelation;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class GiantRelationService {

    @Autowired
    GiantRelationMapper giantRelationMapper;

    @Autowired
    GiantMapper giantMapper;

    @Autowired
    RelationMapper relationMapper;

    public Object add(GiantRelation giantRelation){
        if (giantRelation == null)
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());

        if (giantMapper.selectById(giantRelation.getFid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"fid");
        }

        if (giantMapper.selectById(giantRelation.getTid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"tid");
        }

        if (relationMapper.selectById(giantRelation.getRid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"rid");
        }

        if (StringUtils.isBlank(giantRelation.getId()))
            giantRelation.setId(Utils.generateId());


        giantRelationMapper.add(giantRelation);
        return new Result(ResultMessage.OK,giantRelation);
    }

    public Object deleteById(String id){

        if (giantRelationMapper.selectById(id) == null)
            return new Result(ResultMessage.PARAM_ERROR);

        giantRelationMapper.deleteById(id);

        return new Result(ResultMessage.OK);
    }
}
