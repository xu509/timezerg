package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.DynastyMapper;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.model.Dynasty;
import com.timezerg.api.model.Node;
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
public class DynastyService {

    @Autowired
    DynastyMapper dynastyMapper;


    public Object add(JSONObject params){
        Dynasty dynasty = new Dynasty();
        dynasty.setId(Utils.generateId());
        dynasty.setTitle(params.getString("title"));

        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");

        Integer dyear = params.getInteger("dyear");
        Integer dmonth = params.getInteger("dmonth");
        Integer dday = params.getInteger("dday");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month == null? 0:month);
        calendar.set(Calendar.DAY_OF_MONTH,day == null? 1:day);
        Date cdate = calendar.getTime();
        dynasty.setCdate(cdate);

        calendar.clear();
        calendar.set(Calendar.YEAR,dyear);
        calendar.set(Calendar.MONTH,month == null? 0:dmonth);
        calendar.set(Calendar.DAY_OF_MONTH,day == null? 1:dday);
        Date ddate = calendar.getTime();
        dynasty.setDdate(ddate);

        dynastyMapper.add(dynasty);

        return new Result(ResultMessage.OK,dynasty);
    }
//
//    public Node selectById(String id){
//        return nodeMapper.selectById(id);
//    }
//
//
//    public Object getList(JSONObject params){
//        Object[] p = {params.getInteger("start"),params.getInteger("size")};
//        List<HashMap> list = nodeMapper.getList(p);
//        JSONObject r = new JSONObject();
//        r.put("data",list);
//        r.put("total",nodeMapper.getListTotal(p));
//        return new Result(ResultMessage.OK,r);
//    }



}
