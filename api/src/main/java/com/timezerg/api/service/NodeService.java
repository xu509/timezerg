package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.CivilizationMapper;
import com.timezerg.api.mapper.NodeCivilizationMapper;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.mapper.NodeNationMapper;
import com.timezerg.api.model.Node;
import com.timezerg.api.model.NodeCivilization;
import com.timezerg.api.model.NodeNation;
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

    @Autowired
    NodeCivilizationMapper nodeCivilizationMapper;


    public Object add(JSONObject params){
        Node node = new Node();
        node.setId(Utils.generateId());
        node.setTitle(params.getString("title"));
        node.setContent(params.getString("content"));
        node.setAD(params.getInteger("AD"));
        node.setDdate(params.getString("ddate"));
        node.setLevel(params.getInteger("level"));
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

        //相关国家
        nodeMapper.add(node);

        // 修改 nation 的相关国家
        JSONArray nations = params.getJSONArray("nations");
        for (int i = 0 ; i < nations.size() ; i++){
            JSONObject nation = nations.getJSONObject(i);
            String nationId = nation.getString("nationid");
            NodeNation nodeNation = new NodeNation();
            nodeNation.setId(Utils.generateId());
            nodeNation.setNationid(nationId);
            nodeNation.setNodeid(node.getId());
            nodeNationMapper.add(nodeNation);
        }

        // 修改 nation 的相关文明
        JSONArray civilizations = params.getJSONArray("civilizations");
        for (int i = 0 ; i < civilizations.size();i++){
            JSONObject civilization = civilizations.getJSONObject(i);
            String cid = civilization.getString("cid");
            NodeCivilization nodeCivilization = new NodeCivilization();
            nodeCivilization.setId(Utils.generateId());
            nodeCivilization.setCid(cid);
            nodeCivilization.setNid(node.getId());
            nodeCivilizationMapper.add(nodeCivilization);
        }

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
        result.put("AD",node.getAD());

        //绑定的相关国家
        List<HashMap> nationMaps = nodeNationMapper.selectByNodeId(id);
        result.put("nations",JSON.toJSON(nationMaps));

        //绑定的文明
        List<HashMap> civilizationMaps = nodeCivilizationMapper.selectByNodeId(id);
        result.put("civilizations",JSON.toJSON(civilizationMaps));


        return new Result(ResultMessage.OK,result);
    }

    public Node selectById(String id){
        return nodeMapper.selectById(id);
    }

    public Object getList(JSONObject params){
        Object[] p = {params.getInteger("start"),params.getInteger("size")};
        List<HashMap> list = nodeMapper.getList(p);
        JSONObject r = new JSONObject();
        for (HashMap row : list){
            String id = (String) row.get("id");

            //相关时代
            row.put("nations",nodeNationMapper.selectByNodeId(id));
        }

        r.put("data",JSON.toJSON(list));
        r.put("total",nodeMapper.getListTotal(p));
        return new Result(ResultMessage.OK,r);
    }

    @Transactional
    public Object edit(JSONObject params){
        String id = params.getString("id");
        String title = params.getString("title");
        String ddate = params.getString("ddate");
        String content = params.getString("content");
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
        node.setContent(content);
        node.setDdate(ddate);
        node.setLevel(level);
        node.setAD(AD);

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
            String nationId = nation.getString("nationid");
            NodeNation nodeNation = new NodeNation();
            nodeNation.setId(Utils.generateId());
            nodeNation.setNationid(nationId);
            nodeNation.setNodeid(id);
            nodeNationMapper.add(nodeNation);
        }

        // 修改 nation 的相关文明
        JSONArray civilizations = params.getJSONArray("civilizations");
        nodeCivilizationMapper.deleteByNodeId(id);
        for (int i = 0 ; i < civilizations.size();i++){
            JSONObject civilization = civilizations.getJSONObject(i);
            String cid = civilization.getString("cid");
            NodeCivilization nodeCivilization = new NodeCivilization();
            nodeCivilization.setId(Utils.generateId());
            nodeCivilization.setCid(cid);
            nodeCivilization.setNid(id);
            nodeCivilizationMapper.add(nodeCivilization);
        }

        return new Result(ResultMessage.OK,node);
    }

    @Transactional
    public Object detail(JSONObject params){
        String id = params.getString("id");
        Node node = nodeMapper.selectById(id);
        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR);
        JSONObject result = new JSONObject();
        result.put("node",node);
        result.put("civilization",nodeCivilizationMapper.selectCivilizationByNid(id));

        //此时别的文明
        result.put("closenodes",nodeMapper.selectCloseNode(node));

        return new Result(ResultMessage.OK,result);
    }


}
