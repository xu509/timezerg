package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.CivilizationMapper;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.model.Civilization;
import com.timezerg.api.model.Node;
import com.timezerg.api.util.DateUtil;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class CivilizationService {

    @Autowired
    CivilizationMapper civilizationMapper;


    public Object add(JSONObject params){
        Civilization civil = new Civilization();
        civil.setId(Utils.generateId());
        civil.setCdate(new Date());
        civil.setPid(params.getString("pid"));
        civil.setCover(null);
        civil.setContent(null);
        civil.setTitle(params.getString("title"));

        civilizationMapper.add(civil);

        return new Result(ResultMessage.OK,civil);
    }


    public Object getList(JSONObject params){
        Object[] p = {params.getInteger("start"),params.getInteger("size")};
        List<HashMap> list = civilizationMapper.getList(p);
        for(HashMap row : list){
            row.put("cdate", DateUtil.format((Date) row.get("cdate"),DateUtil.YEAR_MONTH_DAY_TIME));
        }

        JSONObject r = new JSONObject();
        r.put("data",list);
        r.put("total",civilizationMapper.getListTotal(p));
        return new Result(ResultMessage.OK,r);
    }


}
