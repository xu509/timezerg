package com.timezerg.api.service;

import com.timezerg.api.config.AppConfig;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.mapper.NodePeriodMapper;
import com.timezerg.api.mapper.PeriodMapper;
import com.timezerg.api.model.NodePeriod;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class NodePeriodService {

    @Autowired
    NodePeriodMapper nodePeriodMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    PeriodMapper periodMapper;

    @Transactional
    public Object updateLevel(String npid,Integer level){
        NodePeriod nodePeriod = new NodePeriod();
        nodePeriod.setId(npid);
        nodePeriod.setLevel(level);

        if (nodePeriodMapper.selectById(npid) == null){
            return new Result(ResultMessage.PARAM_ERROR,"npid is wrong");
        }

        if (level == null || level < 0 || level > 5){
            return new Result(ResultMessage.PARAM_ERROR,"LEVEL IS WRONG");
        }

        nodePeriodMapper.updateLevel(nodePeriod);
        return new Result(ResultMessage.OK);
    }

    @Transactional
    public Object add(NodePeriod nodePeriod){
        if (nodePeriod == null)
            return new Result(ResultMessage.PARAM_ERROR , "node period error");

        if (nodeMapper.selectById(nodePeriod.getNid()) == null)
            return new Result(ResultMessage.PARAM_ERROR , "node id wrong");

        if (periodMapper.selectById(nodePeriod.getPid()) == null)
            return new Result(ResultMessage.PARAM_ERROR , "period id wrong");

        if (nodePeriodMapper.selectByNidAndPid(nodePeriod) != null)
            return new Result(ResultMessage.DUPLICATION_ERROR,"node period is dup");

        if (StringUtils.isBlank(nodePeriod.getId()))
            nodePeriod.setId(Utils.generateId());

        nodePeriodMapper.add(nodePeriod);

        return new Result(ResultMessage.OK,nodePeriod);
    }

    /**
     *  判断是否已经存在，存在则更新，不存在则添加
     */
    @Transactional
    public Object checkAndAdd(NodePeriod nodePeriod){
        if (nodePeriod == null)
            return new Result(ResultMessage.PARAM_ERROR , "node period error");


        NodePeriod oldNodePeriod = nodePeriodMapper.selectByNidAndPid(nodePeriod);

        if (oldNodePeriod == null){
            return add(nodePeriod);
        }else {
            if (oldNodePeriod.getLevel() == null && nodePeriod.getLevel() != null){
                return updateLevel(oldNodePeriod.getId(),nodePeriod.getLevel());
            }
            else {
                return new Result(ResultMessage.DUPLICATION_ERROR);
            }
        }
    }


    /**
     *  修改 LEVEL
     */
    public Object changeLevel(String id){
        NodePeriod nodePeriod = nodePeriodMapper.selectById(id);
        if (nodePeriod == null)
            return new Result(ResultMessage.PARAM_ERROR);

        if (nodePeriod.getLevel() == null || (!nodePeriod.getLevel().equals(AppConfig.KEY_VALUE.Level_Very_Important))){
            return updateLevel(id,AppConfig.KEY_VALUE.Level_Very_Important);
        }else {
            return updateLevel(id,AppConfig.KEY_VALUE.Level_Normal);
        }
    }


    public Object delete(String id){
        if (nodePeriodMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nodePeriodMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }
}
