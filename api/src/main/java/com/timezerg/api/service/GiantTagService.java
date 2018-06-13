package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.GiantNation;
import com.timezerg.api.model.GiantTag;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class GiantTagService {

    @Autowired
    GiantTagMapper giantTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    GiantMapper giantMapper;

    public Object add(GiantTag giantTag){
        if (giantTag == null){
            return new Result(ResultMessage.PARAM_ERROR,"giantnation链接失败");
        }

        if (tagMapper.selectById(giantTag.getTid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"giant tag tagid");
        }

        if (giantMapper.selectById(giantTag.getGid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"giant tag gid");
        }

        if (giantTagMapper.selectByGidAndTid(giantTag) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"giantTag");
        }

        giantTagMapper.add(giantTag);
        return new Result(ResultMessage.OK,giantTag);
    }

    public Object delete(String id){
        if (giantTagMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            giantTagMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
