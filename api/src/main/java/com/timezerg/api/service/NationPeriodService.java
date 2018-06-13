package com.timezerg.api.service;

import com.timezerg.api.mapper.*;
import com.timezerg.api.model.CivilizationPeriod;
import com.timezerg.api.model.NationPeriod;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class NationPeriodService {

    @Autowired
    NationPeriodMapper nationPeriodMapper;

    @Autowired
    NationMapper nationMapper;

    @Autowired
    PeriodMapper periodMapper;

    public Object add(NationPeriod nationPeriod){
        if (nationPeriod == null){
            return new Result(ResultMessage.PARAM_ERROR,"国家与时代链接失败");
        }

        if (nationMapper.selectById(nationPeriod.getNid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"国家与时代链接失败，国家ID错误");
        }

        if (periodMapper.selectById(nationPeriod.getPid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"国家与时代链接失败，时代ID错误");
        }

        if (nationPeriodMapper.selectByNidAndPid(nationPeriod) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"国家与时代链接失败");
        }

        nationPeriodMapper.add(nationPeriod);
        return new Result(ResultMessage.OK,nationPeriod);
    }

    public Object delete(String id){
        if (nationPeriodMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            nationPeriodMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
