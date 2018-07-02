package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.CivilizationPeriod;
import com.timezerg.api.model.Nation;
import com.timezerg.api.model.NationPeriod;
import com.timezerg.api.model.Period;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    /* 获取所有国家 */
    public List<Nation> getAllNation(String pid){
        List<Period>  periods = new ArrayList<>();
        List<Period> childPeriods = new ArrayList<>();

        Period p = periodMapper.selectById(pid);
        if (p != null){
            childPeriods.add(p);
        }

        while (childPeriods.size() > 0){
            System.out.println("childPeriods : " + JSON.toJSON(childPeriods).toString());

            periods.addAll(childPeriods);

            Object[] pids = new Object[childPeriods.size()];
            int i = 0;
            for (Period period : childPeriods){
                pids[i] = period.getId();
                i++;
            }

            childPeriods = periodMapper.selectListByPid(pids);
        }

        HashSet periodSets = new HashSet(periods);
        periods.clear();
        periods.addAll(periodSets);

        Object[] periodIds = new Object[periods.size()];
        for (int i = 0;i < periods.size();i++){
            periodIds[i] = periods.get(i).getId();
            i++;
        }

        List<Nation> nations = nationPeriodMapper.selectNationBeanByPid(periodIds);

        if (nations.size() == 0)
            return null;


        Object[] nids = new Object[nations.size()];

        int index = 0;
        for (Nation nation : nations){

            nids[index] = nation.getId();
            index++;
        }

        List<Nation> childNation = nationMapper.selectListByFids(nids);

        while (childNation.size() > 0){
            nations.addAll(childNation);

            Object[] cids = new Object[childNation.size()];
            int i = 0;
            for (Nation nation : childNation){
                cids[i] = nation.getId();
                i++;
            }

            childNation = nationMapper.selectListByFids(cids);
        }

        HashSet h = new HashSet(nations);
        nations.clear();
        nations.addAll(h);
        return nations;
    }

}
