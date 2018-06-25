package com.timezerg.api.service;

import com.timezerg.api.mapper.GiantMapper;
import com.timezerg.api.mapper.NodeCivilizationMapper;
import com.timezerg.api.mapper.NodeGiantMapper;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.model.NodeCivilization;
import com.timezerg.api.model.NodeGiant;
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
public class NodeCivilizationService {

    @Autowired
    NodeCivilizationMapper nodeCivilizationMapper;


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


}
