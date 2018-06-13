package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.GiantNation;
import com.timezerg.api.model.Institution;
import com.timezerg.api.model.NationInstitution;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class NationInstitutionService {

    @Autowired
    NationInstitutionMapper nationInstitutionMapper;

    @Autowired
    NationMapper nationMapper;

    @Autowired
    InstitutionMapper institutionMapper;

    public Object add(NationInstitution nationInstitution){
        if (nationInstitution == null){
            return new Result(ResultMessage.PARAM_ERROR,"nationInstitution链接失败");
        }

        if (nationMapper.selectById(nationInstitution.getNid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"nation institution 链接失败，国家ID错误");
        }

        if (institutionMapper.selectById(nationInstitution.getIid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"nation institution 链接失败，institution id");
        }

        if (nationInstitutionMapper.selectByIidAndNid(nationInstitution) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"nation institution 链接失败");
        }

        nationInstitutionMapper.add(nationInstitution);
        return new Result(ResultMessage.OK,nationInstitution);
    }

    public Object delete(String id){
        if (nationInstitutionMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nationInstitutionMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
