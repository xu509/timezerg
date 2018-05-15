package com.timezerg.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.CivilizationContinentMapper;
import com.timezerg.api.mapper.CivilizationMapper;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.model.Civilization;
import com.timezerg.api.model.CivilizationContinent;
import com.timezerg.api.model.Node;
import com.timezerg.api.util.DateUtil;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    CivilizationContinentMapper civilizationContinentMapper;


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
            String id = (String) row.get("id");

            row.put("cdate", DateUtil.format((Date) row.get("cdate"),DateUtil.YEAR_MONTH_DAY_TIME));

            //相关大洲
            row.put("continents",civilizationContinentMapper.selectByCivilizationId(id));

        }


        JSONObject r = new JSONObject();
        r.put("data",list);
        r.put("total",civilizationMapper.getListTotal(p));
        return new Result(ResultMessage.OK,r);
    }

    public Object selectByTitle(JSONObject params){
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Civilization> nations = civilizationMapper.selectLikeByTitle(title);
        r.put("exist",nations.size() > 0);
        r.put("data",nations);

        return new Result(ResultMessage.OK,r);
    }

    public Object editInit(JSONObject params){
        JSONObject r;
        String id = params.getString("id");

        Civilization civilization = civilizationMapper.selectById(id);

        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR);

        r = (JSONObject) JSONObject.toJSON(civilization);

        //获取绑定大洲
        r.put("continents",civilizationContinentMapper.selectByCivilizationId(id));

        return new Result(ResultMessage.OK,r);
    }

    @Transactional
    public Object edit(JSONObject params){
        String id = params.getString("id");
        String title = params.getString("title");

        Civilization civilization = civilizationMapper.selectById(id);
        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR);

        civilization.setTitle(title);
        civilizationMapper.update(civilization);

        //修改绑定的大洲
        civilizationContinentMapper.deleteByCivilizationId(id);
        JSONArray continents = params.getJSONArray("continents");
        for (int i = 0 ; i < continents.size() ; i ++){
            JSONObject continent = continents.getJSONObject(i);
            CivilizationContinent civilizationContinent = new CivilizationContinent();
            civilizationContinent.setId(Utils.generateId());
            civilizationContinent.setCivilizationid(id);
            civilizationContinent.setContinentid(continent.getString("continentid"));
            civilizationContinentMapper.add(civilizationContinent);
        }

        return new Result(ResultMessage.OK,civilization);
    }


}
