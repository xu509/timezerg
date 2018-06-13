package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.GiantNation;
import com.timezerg.api.model.NationPeriod;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class GiantNationService {

    @Autowired
    GiantNationMapper giantNationMapper;

    @Autowired
    NationMapper nationMapper;

    @Autowired
    GiantMapper giantMapper;

    public Object add(GiantNation giantNation){
        if (giantNation == null){
            return new Result(ResultMessage.PARAM_ERROR,"giantnation链接失败");
        }

        if (nationMapper.selectById(giantNation.getNid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"giantnation链接失败，国家ID错误");
        }

        if (giantMapper.selectById(giantNation.getGid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"giantnation链接失败，giantID错误");
        }

        if (giantNationMapper.selectByGidAndNid(giantNation) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"giantnation链接失败");
        }

        giantNationMapper.add(giantNation);
        return new Result(ResultMessage.OK,giantNation);
    }

    public Object delete(String id){
        if (giantNationMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            giantNationMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
