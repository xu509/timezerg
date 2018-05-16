package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
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
 */
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;


    @Transactional
    public Object add(JSONObject params) {
        Nation nation = new Nation();
        nation.setId(Utils.generateId());
        nation.setTitle(params.getString("title"));
        nation.setContent(params.getString("content"));
        nation.setAD(params.getInteger("AD"));
        nation.seteAD(params.getInteger("eAD"));
        nation.setDdate(params.getString("ddate"));
        nation.setPid(params.getString("pid") == null ? "" : params.getString("pid"));
//        node.setCover();
        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Integer eyear = params.getInteger("eyear");
        Integer emonth = params.getInteger("emonth");
        Integer eday = params.getInteger("eday");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month == null ? 0 : month);
        calendar.set(Calendar.DAY_OF_MONTH, day == null ? 1 : day);
        nation.setCdate(calendar.getTime());

        if (eyear != null){
            calendar.clear();
            calendar.set(Calendar.YEAR, eyear);
            calendar.set(Calendar.MONTH, emonth == null ? 0 : emonth);
            calendar.set(Calendar.DAY_OF_MONTH, eday == null ? 1 : eday);
            nation.setEdate(calendar.getTime());
        }

        //pid
        String pid = params.getString("pid");
        Nation pnation = nationMapper.selectById(pid);
        if (pnation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        nation.setPid(pid);
        nationMapper.add(nation);

        return new Result(ResultMessage.OK, nation);
    }


    public Object getList(JSONObject params) {
        Object[] p = {params.getInteger("start"), params.getInteger("size")};
        List<HashMap> list = nationMapper.getList(p);
        JSONObject r = new JSONObject();
        r.put("data", list);
        r.put("total", nationMapper.getListTotal(p));
        return new Result(ResultMessage.OK, r);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Nation> nations = nationMapper.selectLikeByTitle(title);
        r.put("exist", nations.size() > 0);
        r.put("data", nations);

        return new Result(ResultMessage.OK, r);
    }

    public Object editInit(JSONObject params) {
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);
        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject r = (JSONObject) JSONObject.toJSON(nation);

        Date cdate = nation.getCdate();
        int year =  1,month = 0,day = 0;
        if (cdate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cdate);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        r.put("year", year);
        r.put("month", month);
        r.put("day", day);

        Date edate = nation.getEdate();
        int eyear,emonth,eday;
        if (edate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(edate);
            eyear = calendar.get(Calendar.YEAR);
            emonth = calendar.get(Calendar.MONTH);
            eday = calendar.get(Calendar.DAY_OF_MONTH);
            r.put("eyear", eyear);
            r.put("emonth", emonth);
            r.put("eday", eday);
        }

        //nation
        Nation pnation = nationMapper.selectById(nation.getPid());
        if (pnation != null)
            r.put("pnation", pnation.getTitle());

        return new Result(ResultMessage.OK, r);
    }

    @Transactional
    public Object edit(JSONObject params) {
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);
        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        nation.setTitle(params.getString("title"));
        nation.setDdate(params.getString("ddate"));
        nation.setPid(params.getString("pid"));

        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month == null ? 0 : month);
        calendar.set(Calendar.DAY_OF_MONTH, day == null ? 1 : day);
        nation.setCdate(calendar.getTime());

        Integer eyear = params.getInteger("eyear");
        Integer emonth = params.getInteger("emonth");
        Integer eday = params.getInteger("eday");

        if (eyear == null)
            nation.setEdate(null);
        else {
            calendar.clear();
            calendar.set(Calendar.YEAR, eyear);
            calendar.set(Calendar.MONTH, emonth == null ? 0 : emonth);
            calendar.set(Calendar.DAY_OF_MONTH, eday == null ? 1 : eday);
            nation.setEdate(calendar.getTime());
        }

        nation.setAD(params.getInteger("AD"));
        nation.seteAD(params.getInteger("eAD"));

        nation.setPid(params.getString("pid"));

        System.out.println(JSON.toJSONString(nation));
        nationMapper.update(nation);

        return new Result(ResultMessage.OK, nation);
    }


}
