package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.Node;
import com.timezerg.api.model.NodeNation;
import com.timezerg.api.model.NodeReference;
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
public class NodeNationService {

    @Autowired
    NodeNationMapper nodeNationMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    NationMapper nationMapper;

    public Object add(NodeNation nodeNation){
        if (nodeNation == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nodeMapper.selectById(nodeNation.getNodeid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nationMapper.selectById(nodeNation.getNationid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nodeNationMapper.selectByNodeIdAndNationId(nodeNation) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(nodeNation.getId()))
            nodeNation.setId(Utils.generateId());

        nodeNationMapper.add(nodeNation);
        return new Result(ResultMessage.OK,nodeNation);
    }

    public Object delete(String id){
        if (nodeNationMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nodeNationMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }

    public List<Node> selectNodesByNationIds(String[] nationIds){
        return nodeNationMapper.selectNodesByNationIds(nationIds);
    }



}
