package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.*;
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
 * 时代服务类
 */
@Service
public class PeriodService {

    @Autowired
    PeriodMapper periodMapper;

    @Autowired
    NationMapper nationMapper;

    @Autowired
    CivilizationMapper civilizationMapper;

    @Autowired
    NationPeriodMapper nationPeriodMapper;

    @Autowired
    CivilizationPeriodMapper civilizationPeriodMapper;


    @Transactional
    public Object add(JSONObject params) {
        Period period = new Period();
        String pid = Utils.generateId();
        period.setId(pid);
        period.setTitle(params.getString("title"));
        period.setContent(params.getString("content"));
        period.setAD(params.getInteger("AD"));
        period.seteAD(params.getInteger("eAD"));
        period.setDdate(params.getString("ddate"));

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
        period.setCdate(calendar.getTime());

        if (eyear != null){
            calendar.clear();
            calendar.set(Calendar.YEAR, eyear);
            calendar.set(Calendar.MONTH, emonth == null ? 0 : emonth);
            calendar.set(Calendar.DAY_OF_MONTH, eday == null ? 1 : eday);
            period.setEdate(calendar.getTime());
        }

        periodMapper.add(period);

        JSONArray civilizationAry = params.getJSONArray("civilizations");
        bindCivilization(civilizationAry,pid);

        JSONArray nationAry = params.getJSONArray("nations");
        bindNation(nationAry,pid);

        return new Result(ResultMessage.OK);
    }


    public Object getList(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("searchtitle");
        Integer start = params.getInteger("start");
        Integer size = params.getInteger("size");

        //默认
        List<HashMap> list = periodMapper.getList(title,start,size);
        r.put("data", list);
        r.put("total", periodMapper.getListTotal(title));

        return new Result(ResultMessage.OK, r);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Period> periods = periodMapper.selectLikeByTitle(title);

        JSONArray periodAry = new JSONArray();
        for (Period period : periods){
            JSONObject periodObj = (JSONObject) JSON.toJSON(period);
            periodObj.put("pid",period.getId());
            periodAry.add(periodObj);
        }

        r.put("exist", periods.size() > 0);
        r.put("data", periodAry);

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

        //nations
        List<HashMap> nations = nationPeriodMapper.selectNationByPid(id);
        r.put("nations",JSONObject.toJSON(nations));

        //civilizations
        List<HashMap> civilizations = civilizationPeriodMapper.selectCivilizationByPid(id);
        r.put("civilizations",JSONObject.toJSON(civilizations));

        return new Result(ResultMessage.OK, r);
    }


//    public Object editInitTag(JSONObject params){
//        String id = params.getString("id");
//        Nation nation = nationMapper.selectById(id);
//        if (nation == null)
//            return new Result(ResultMessage.PARAM_ERROR);
//        List<HashMap> tagMaps = nationTagMapper.selectTagsByNid(id);
//
////        System.out.println(JSON.toJSONString(tagMaps));
//        JSONObject data = new JSONObject();
//        data.put("nation",nation);
//        data.put("tags",tagMaps);
//        return new Result(ResultMessage.OK,data);
//    }
//
//    @Transactional
//    public Object addTag(JSONObject params){
//        JSONObject tag = params.getJSONObject("tag");
//        String nid = params.getString("nid");
//        String tid = tag.getString("tid");
//
////        System.out.println(params);
//
//        Boolean isNew = tag.getBoolean("isnew");
//        if (isNew != null && isNew){
//            String title = tag.getString("title");
//            Tag t = new Tag();
//            tid = Utils.generateId();
//            t.setId(tid);
//            t.setTitle(title);
//            tagMapper.add(t);
//        }
//
//        NationTag nationTag = new NationTag();
//        nationTag.setId(Utils.generateId());
//        nationTag.setNid(nid);
//        nationTag.setTid(tid);
//
//       if (nationTagMapper.selectByNidAndTid(nationTag) != null){
//           return new Result(ResultMessage.PARAM_ERROR);
//       }
//        nationTagMapper.add(nationTag);
//
//        return new Result(ResultMessage.OK);
//    }
//
//    @Transactional
//    public Object deleteTag(JSONObject params){
//        String id = (String) params.get("id");
//        nationTagMapper.deleteById(id);
//        return new Result(ResultMessage.OK);
//    }


    @Transactional
    public Object edit(JSONObject params) {
        String id = params.getString("id");
        Period period = periodMapper.selectById(id);
        if (period == null)
            return new Result(ResultMessage.PARAM_ERROR);

        period.setTitle(params.getString("title"));
        period.setDdate(params.getString("ddate"));

        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month == null ? 0 : month);
        calendar.set(Calendar.DAY_OF_MONTH, day == null ? 1 : day);
        period.setCdate(calendar.getTime());

        Integer eyear = params.getInteger("eyear");
        Integer emonth = params.getInteger("emonth");
        Integer eday = params.getInteger("eday");

        if (eyear == null)
            period.setEdate(null);
        else {
            calendar.clear();
            calendar.set(Calendar.YEAR, eyear);
            calendar.set(Calendar.MONTH, emonth == null ? 0 : emonth);
            calendar.set(Calendar.DAY_OF_MONTH, eday == null ? 1 : eday);
            period.setEdate(calendar.getTime());
        }

        period.setAD(params.getInteger("AD"));
        period.seteAD(params.getInteger("eAD"));

        periodMapper.update(period);


        JSONArray civilizationAry = params.getJSONArray("civilizations");
        bindCivilization(civilizationAry,id);

        JSONArray nationAry = params.getJSONArray("nations");
        bindNation(nationAry,id);

        return new Result(ResultMessage.OK, period);
    }


    private void bindCivilization(JSONArray civilizationAry,String pid){
        if (civilizationAry != null){
            civilizationPeriodMapper.deleteByPid(pid);

            for (int i = 0 ; i < civilizationAry.size(); i++){
                JSONObject object = civilizationAry.getJSONObject(i);
                String cid = object.getString("cid");

                boolean isnew = object.getBoolean("isnew");
                if (isnew){
                    String title = object.getString("title");
                    if (civilizationMapper.selectByTitle(title) == null){
                        Civilization civilization = new Civilization();
                        cid = Utils.generateId();
                        civilization.setId(cid);
                        civilization.setTitle(title);
                        civilizationMapper.add(civilization);
                    }else {
                        return;
                    }
                }

                CivilizationPeriod civilizationPeriod = new CivilizationPeriod();
                civilizationPeriod.setId(Utils.generateId());
                civilizationPeriod.setCid(cid);
                civilizationPeriod.setPid(pid);

                if (civilizationPeriodMapper.selectByCidAndPid(civilizationPeriod) == null){
                    civilizationPeriodMapper.add(civilizationPeriod);
                }

            }
        }
    }

    private void bindNation(JSONArray nationAry,String pid){
        if (nationAry != null){
            nationPeriodMapper.deleteByPId(pid);

            for (int i = 0 ; i < nationAry.size(); i++){
                JSONObject object = nationAry.getJSONObject(i);
                String nid = object.getString("nid");

                boolean isnew = object.getBoolean("isnew");
                if (isnew){
                    String title = object.getString("title");
                    if (nationMapper.selectLikeByTitle(title) == null){
                        Nation nation = new Nation();
                        nid = Utils.generateId();
                        nation.setId(nid);
                        nation.setTitle(title);
                        nationMapper.add(nation);
                    }else {
                        return;
                    }
                }

                NationPeriod nationPeriod = new NationPeriod();
                nationPeriod.setId(Utils.generateId());
                nationPeriod.setNid(nid);
                nationPeriod.setPid(pid);

                if (nationPeriodMapper.selectByNidAndPid(nationPeriod) == null){
                    nationPeriodMapper.add(nationPeriod);
                }

            }
        }
    }



}
