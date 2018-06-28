package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.config.AppConfig;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.*;
import com.timezerg.api.model.api.TimeApiBean;
import com.timezerg.api.util.DateUtil;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class CivilizationPeriodService {

    @Autowired
    CivilizationPeriodMapper civilizationPeriodMapper;

    @Autowired
    CivilizationMapper civilizationMapper;

    @Autowired
    PeriodMapper periodMapper;

    public Object add(CivilizationPeriod civilizationPeriod){
        if (civilizationPeriod == null){
            return new Result(ResultMessage.PARAM_ERROR,"文明与时代链接失败");
        }

        if (civilizationMapper.selectById(civilizationPeriod.getCid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"文明与时代链接失败，文明ID错误");
        }

        if (periodMapper.selectById(civilizationPeriod.getPid()) == null){
            return new Result(ResultMessage.PARAM_ERROR,"文明与时代链接失败，时代ID错误");
        }

        if (civilizationPeriodMapper.selectByCidAndPid(civilizationPeriod) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"文明与时代链接失败");
        }

        if (StringUtils.isBlank(civilizationPeriod.getId()))
            civilizationPeriod.setId(Utils.generateId());

        civilizationPeriodMapper.add(civilizationPeriod);
        return new Result(ResultMessage.OK,civilizationPeriod);
    }

    public Object delete(String id){
        if (civilizationPeriodMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            civilizationPeriodMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }

    /* 获取所有时代 */
    public List<Period> getAllPeriods(String cid){

        List<Period> periods = civilizationPeriodMapper.selectPeriodByCid(cid);

        Object[] pids = new Object[periods.size()];
        int index = 0;
        for (Period period : periods){
            pids[index] = period.getId();
            index++;
        }

        List<Period> childPeriods = periodMapper.selectListByPid(pids);

        while (childPeriods.size() > 0){
            periods.addAll(childPeriods);

            Object[] cids = new Object[childPeriods.size()];
            int i = 0;
            for (Period period : childPeriods){
                pids[i] = period.getId();
                i++;
            }

            childPeriods = periodMapper.selectListByPid(cids);
        }

        HashSet h = new HashSet(periods);
        periods.clear();
        periods.addAll(h);
        return periods;
    }



}
