package com.timezerg.api.service;

import com.timezerg.api.mapper.PeriodMapper;
import com.timezerg.api.mapper.PeriodReferenceMapper;
import com.timezerg.api.mapper.ReferenceMapper;
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
public class PeriodReferenceService {

    @Autowired
    PeriodReferenceMapper periodReferenceMapper;

    @Autowired
    PeriodMapper periodMapper;

    @Autowired
    ReferenceMapper referenceMapper;

    public Object add(PeriodReference periodReference){
        if (periodReference == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (periodMapper.selectById(periodReference.getPid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (referenceMapper.selectById(periodReference.getRid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (periodReferenceMapper.selectByPidAndRid(periodReference) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(periodReference.getId()))
            periodReference.setId(Utils.generateId());

        periodReferenceMapper.add(periodReference);
        return new Result(ResultMessage.OK,periodReference);
    }

    public Object delete(String id){
        if (periodReferenceMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            periodReferenceMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
