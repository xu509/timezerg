package com.timezerg.api.service;

import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.mapper.NodeTagMapper;
import com.timezerg.api.mapper.TagMapper;
import com.timezerg.api.model.NodeTag;
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
public class NodeTagService {

    @Autowired
    NodeTagMapper nodeTagMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    TagMapper tagMapper;

    public Object add(NodeTag nodeTag){
        if (nodeTag == null){
            return new Result(ResultMessage.PARAM_ERROR,"nodeTag null");
        }

        if (tagMapper.selectById(nodeTag.getTid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"nodeTag tid");
        }

        if (nodeMapper.selectById(nodeTag.getNid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"nodeTag nid");
        }

        if (nodeTagMapper.selectByNidAndTid(nodeTag) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(nodeTag.getId()))
            nodeTag.setId(Utils.generateId());

        nodeTagMapper.add(nodeTag);
        return new Result(ResultMessage.OK,nodeTag);
    }

    public Object delete(String id){
        if (nodeTagMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nodeTagMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
