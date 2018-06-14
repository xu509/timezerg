package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.GiantReference;
import com.timezerg.api.model.NationPeriod;
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
public class GiantReferenceService {

    @Autowired
    GiantReferenceMapper giantReferenceMapper;

    @Autowired
    GiantMapper giantMapper;

    @Autowired
    ReferenceMapper referenceMapper;

    public Object add(GiantReference giantReference){
        if (giantReference == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (giantMapper.selectById(giantReference.getGid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (referenceMapper.selectById(giantReference.getRid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (giantReferenceMapper.selectByGidAndRid(giantReference) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(giantReference.getId()))
            giantReference.setId(Utils.generateId());

        giantReferenceMapper.add(giantReference);
        return new Result(ResultMessage.OK,giantReference);
    }

    public Object delete(String id){
        if (giantReferenceMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            giantReferenceMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
