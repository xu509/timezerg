package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.ContinentMapper;
import com.timezerg.api.mapper.GiantMapper;
import com.timezerg.api.mapper.GiantRelationMapper;
import com.timezerg.api.mapper.RelationMapper;
import com.timezerg.api.model.Continent;
import com.timezerg.api.model.GiantRelation;
import com.timezerg.api.model.Relation;
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

        if (giantRelation.getFid().equals(giantRelation.getTid())){
            return new Result(ResultMessage.PARAM_ERROR,"不能给自己添加关系");
        }

        if (giantMapper.selectById(giantRelation.getFid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"fid");
        }

        if (giantMapper.selectById(giantRelation.getTid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"tid");
        }


        Relation r = relationMapper.selectById(giantRelation.getRid());
        if (r == null){
            return new Result(ResultMessage.PARAM_ERROR,"rid");
        }

        if (StringUtils.isBlank(giantRelation.getId()))
            giantRelation.setId(Utils.generateId());

        boolean relationHasTid = false;
        if (!StringUtils.isBlank(r.getTid()))
            relationHasTid = true;

        if (giantRelationMapper.selectByFidTidAndRid(giantRelation) == null)
            giantRelationMapper.add(giantRelation);


        if (relationHasTid){
            GiantRelation tGiantRelation = new GiantRelation();
            tGiantRelation.setId(Utils.generateId());
            tGiantRelation.setFid(giantRelation.getTid());
            tGiantRelation.setTid(giantRelation.getFid());
            tGiantRelation.setRid(r.getTid());

            if (giantRelationMapper.selectByFidTidAndRid(tGiantRelation) == null){
                giantRelationMapper.add(tGiantRelation);
            }
        }

        return new Result(ResultMessage.OK,giantRelation);
    }

    public Object deleteById(String id){

        GiantRelation giantRelation = giantRelationMapper.selectById(id);

        if (giantRelation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        giantRelationMapper.deleteById(id);


        GiantRelation tGiantRelation = new GiantRelation();
        tGiantRelation.setFid(giantRelation.getTid());
        tGiantRelation.setTid(giantRelation.getFid());
        Relation relation = relationMapper.selectById(giantRelation.getRid());
        tGiantRelation.setRid(relation.getTid());

        tGiantRelation =  giantRelationMapper.selectByFidTidAndRid(tGiantRelation);
        if (tGiantRelation != null)
            giantRelationMapper.deleteById(tGiantRelation.getId());

        return new Result(ResultMessage.OK);
    }
}
