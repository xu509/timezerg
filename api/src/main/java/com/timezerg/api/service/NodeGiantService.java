package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.NodeGiant;
import com.timezerg.api.model.PeriodReference;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class NodeGiantService {

    @Autowired
    NodeGiantMapper nodeGiantMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    GiantMapper giantMapper;

    public Object add(NodeGiant nodeGiant){
        if (nodeGiant == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (giantMapper.selectById(nodeGiant.getGid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nodeMapper.selectById(nodeGiant.getNid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nodeGiantMapper.selectByNidAndGid(nodeGiant) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(nodeGiant.getId()))
            nodeGiant.setId(Utils.generateId());

        nodeGiantMapper.add(nodeGiant);
        return new Result(ResultMessage.OK,nodeGiant);
    }

    public Object delete(String id){
        if (nodeGiantMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nodeGiantMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
