package com.timezerg.api.service;

import com.timezerg.api.mapper.ReferenceMapper;
import com.timezerg.api.mapper.ReferenceTagMapper;
import com.timezerg.api.mapper.TagMapper;
import com.timezerg.api.model.ReferenceTag;
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
public class ReferenceTagService {

    @Autowired
    ReferenceTagMapper referenceTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ReferenceMapper referenceMapper;

    public Object add(ReferenceTag referenceTag){
        if (referenceTag == null){
            return new Result(ResultMessage.PARAM_ERROR,"referenceTag");
        }

        if (tagMapper.selectById(referenceTag.getTid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"reference tag tagid");
        }

        if (referenceMapper.selectById(referenceTag.getRid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,getClass());
        }

        if (referenceTagMapper.selectByRidAndTid(referenceTag) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"referenceTag");
        }

        if (StringUtils.isBlank(referenceTag.getId()))
            referenceTag.setId(Utils.generateId());

        referenceTagMapper.add(referenceTag);
        return new Result(ResultMessage.OK,referenceTag);
    }

    public Object delete(String id){
        if (referenceTagMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            referenceTagMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
