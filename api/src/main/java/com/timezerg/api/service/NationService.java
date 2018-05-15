package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.NationMapper;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.model.Nation;
import com.timezerg.api.model.Node;
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
 * 国家服务类
 *
 */
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;


    @Transactional
    public Object add(JSONObject params){
        Nation nation = new Nation();
        nation.setId(Utils.generateId());
        nation.setTitle(params.getString("title"));
        nation.setContent(params.getString("content"));
        nation.setAD(params.getBoolean("AD"));
        nation.setDdate(params.getString("ddate"));
        nation.setPid(params.getString("pid") == null?"":params.getString("pid"));
//        node.setCover();
        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Integer eyear = params.getInteger("eyear");
        Integer emonth = params.getInteger("emonth");
        Integer eday = params.getInteger("eday");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month == null? 0:month);
        calendar.set(Calendar.DAY_OF_MONTH,day == null? 1:day);
        nation.setCdate(calendar.getTime());

        calendar.clear();
        calendar.set(Calendar.YEAR,eyear);
        calendar.set(Calendar.MONTH,emonth == null? 0:emonth);
        calendar.set(Calendar.DAY_OF_MONTH,eday == null? 1:eday);
        nation.setEdate(calendar.getTime());

        //pid
        String pid = params.getString("pid");
        Nation pnation = nationMapper.selectById(pid);
        if (pnation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        nation.setPid(pid);
        nationMapper.add(nation);

        return new Result(ResultMessage.OK,nation);
    }


    public Object getList(JSONObject params){
        Object[] p = {params.getInteger("start"),params.getInteger("size")};
        List<HashMap> list = nationMapper.getList(p);
        JSONObject r = new JSONObject();
        r.put("data",list);
        r.put("total",nationMapper.getListTotal(p));
        return new Result(ResultMessage.OK,r);
    }


    public Object selectByTitle(JSONObject params){
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Nation> nations = nationMapper.selectLikeByTitle(title);
        r.put("exist",nations.size() > 0);
        r.put("data",nations);

        return new Result(ResultMessage.OK,r);
    }

    public Object editInit(JSONObject params){
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);

//        Nation pnation = nation.getPid();



        return new Result(ResultMessage.OK);
    }


}
