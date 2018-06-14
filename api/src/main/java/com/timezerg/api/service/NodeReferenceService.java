package com.timezerg.api.service;

import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.mapper.NodeReferenceMapper;
import com.timezerg.api.mapper.ReferenceMapper;
import com.timezerg.api.model.NodeReference;
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
public class NodeReferenceService {

    @Autowired
    NodeReferenceMapper nodeReferenceMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    ReferenceMapper referenceMapper;

    public Object add(NodeReference nodeReference){
        if (nodeReference == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nodeMapper.selectById(nodeReference.getNid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (referenceMapper.selectById(nodeReference.getRid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nodeReferenceMapper.selectByNidAndRid(nodeReference) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(nodeReference.getId()))
            nodeReference.setId(Utils.generateId());

        nodeReferenceMapper.add(nodeReference);
        return new Result(ResultMessage.OK,nodeReference);
    }

    public Object delete(String id){
        if (nodeReferenceMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nodeReferenceMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
