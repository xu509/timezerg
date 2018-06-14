package com.timezerg.api.service;

import com.timezerg.api.mapper.NationMapper;
import com.timezerg.api.mapper.NationReferenceMapper;
import com.timezerg.api.mapper.ReferenceMapper;
import com.timezerg.api.model.NationReference;
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
public class NationReferenceService {

    @Autowired
    NationReferenceMapper nationReferenceMapper;

    @Autowired
    NationMapper nationMapper;

    @Autowired
    ReferenceMapper referenceMapper;

    public Object add(NationReference nationReference){
        if (nationReference == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nationMapper.selectById(nationReference.getNid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (referenceMapper.selectById(nationReference.getRid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,this.getClass());
        }

        if (nationReferenceMapper.selectByNidAndRid(nationReference) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,this.getClass());
        }

        if (StringUtils.isBlank(nationReference.getId()))
            nationReference.setId(Utils.generateId());

        nationReferenceMapper.add(nationReference);
        return new Result(ResultMessage.OK,nationReference);
    }

    public Object delete(String id){
        if (nationReferenceMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nationReferenceMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
