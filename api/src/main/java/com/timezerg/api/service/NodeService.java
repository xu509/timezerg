package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.mapper.NodeNationMapper;
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
 * 时间点服务类
 *
 */
@Service
public class NodeService {

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    NodeNationMapper nodeNationMapper;


    public Object add(JSONObject params){
        Node node = new Node();
        node.setId(Utils.generateId());
        node.setTitle(params.getString("title"));
        node.setContent(params.getString("content"));
        node.setAD(params.getBoolean("AD"));
        node.setDdate(params.getString("ddate"));
//        node.setCover();
        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Integer hour = params.getInteger("hour");
        Integer minute = params.getInteger("minute");
        Integer second = params.getInteger("second");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month == null? 0:month);
        calendar.set(Calendar.DAY_OF_MONTH,day == null? 1:day);
        calendar.set(Calendar.HOUR_OF_DAY,hour == null? 1:hour);
        calendar.set(Calendar.MINUTE,minute == null? 1:minute);
        calendar.set(Calendar.SECOND,second == null? 1:second);
        Date date = calendar.getTime();

        node.setCdate(date);
        nodeMapper.add(node);


        return new Result(ResultMessage.OK,node);
    }

    public Object init(JSONObject params){
        String id = params.getString("id");
        Node node = nodeMapper.selectById(id);

        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject result = (JSONObject) JSONObject.toJSON(node);
        Date date = node.getCdate();
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result.put("year",calendar.get(Calendar.YEAR));
            result.put("month",calendar.get(Calendar.MONTH));
            result.put("day",calendar.get(Calendar.DAY_OF_MONTH));
            result.put("hour",calendar.get(Calendar.HOUR_OF_DAY));
            result.put("minute",calendar.get(Calendar.MINUTE));
            result.put("second",calendar.get(Calendar.SECOND));
        }
        result.put("AD",Utils.transferToJsonStr(node.isAD()));

        return new Result(ResultMessage.OK,result);
    }


    public Node selectById(String id){
        return nodeMapper.selectById(id);
    }


    public Object getList(JSONObject params){
        Object[] p = {params.getInteger("start"),params.getInteger("size")};
        List<HashMap> list = nodeMapper.getList(p);
        JSONObject r = new JSONObject();
        r.put("data",list);
        r.put("total",nodeMapper.getListTotal(p));
        return new Result(ResultMessage.OK,r);
    }

    @Transactional
    public Object edit(JSONObject params){
        String id = params.getString("id");
        String title = params.getString("title");
        String ddate = params.getString("ddate");
        Integer level = params.getInteger("level");
        Integer AD = params.getInteger("AD");
        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Integer hour = params.getInteger("hour");
        Integer minute = params.getInteger("minute");
        Integer second = params.getInteger("second");

        Node node = new Node();
        node.setId(id);
        node.setTitle(title);
        node.setDdate(ddate);
        node.setLevel(level);
        node.setAD((AD == null || AD == 0 ) ? false:true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month == null? 0:month);
        calendar.set(Calendar.DAY_OF_MONTH,day == null? 1:day);
        calendar.set(Calendar.HOUR_OF_DAY,hour == null? 1:hour);
        calendar.set(Calendar.MINUTE,minute == null? 1:minute);
        calendar.set(Calendar.SECOND,second == null? 1:second);
        node.setCdate(calendar.getTime());
        nodeMapper.update(node);

        // 修改 nation 的相关国家
        JSONArray nations = params.getJSONArray("nations");
        nodeNationMapper.deleteByNodeId(id);
        for (int i = 0 ; i < nations.size() ; i++){
            JSONObject nation = nations.getJSONObject(i);
            //TODO 完成国家绑定
        }






        return new Result(ResultMessage.OK,node);
    }


}
