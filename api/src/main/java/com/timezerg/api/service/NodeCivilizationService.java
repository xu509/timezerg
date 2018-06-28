package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.Node;
import com.timezerg.api.model.NodeCivilization;
import com.timezerg.api.model.NodeGiant;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class NodeCivilizationService {

    @Autowired
    NodeCivilizationMapper nodeCivilizationMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    CivilizationMapper civilizationMapper;

    @Transactional
    public Object updateLevel(String ncid,Integer level){
        NodeCivilization nodeCivilization = new NodeCivilization();
        nodeCivilization.setId(ncid);
        nodeCivilization.setLevel(level);

        if (nodeCivilizationMapper.selectById(ncid) == null){
            return new Result(ResultMessage.PARAM_ERROR,"ncid is wrong");
        }

        if (level == null || level < 0 || level > 5){
            return new Result(ResultMessage.PARAM_ERROR,"LEVEL IS WRONG");
        }

        nodeCivilizationMapper.updateLevel(nodeCivilization);
        return new Result(ResultMessage.OK);
    }

    @Transactional
    public Object add(NodeCivilization nodeCivilization){
        if (nodeCivilization == null)
            return new Result(ResultMessage.PARAM_ERROR , "node civilization error");

        if (nodeMapper.selectById(nodeCivilization.getNid()) == null)
            return new Result(ResultMessage.PARAM_ERROR , "node id wrong");

        if (civilizationMapper.selectById(nodeCivilization.getCid()) == null)
            return new Result(ResultMessage.PARAM_ERROR , "civilization id wrong");

        if (nodeCivilizationMapper.selectByNidAndCid(nodeCivilization) != null)
            return new Result(ResultMessage.DUPLICATION_ERROR,"node civilization is dup");

        if (StringUtils.isBlank(nodeCivilization.getId()))
            nodeCivilization.setId(Utils.generateId());

        nodeCivilizationMapper.add(nodeCivilization);

        return new Result(ResultMessage.OK,nodeCivilization);
    }



}
