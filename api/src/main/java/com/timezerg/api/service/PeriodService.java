package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.*;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
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
    PeriodTagMapper periodTagMapper;

    @Autowired
    CivilizationPeriodMapper civilizationPeriodMapper;

    @Autowired
    CivilizationService civilizationService;

    @Autowired
    CivilizationPeriodService civilizationPeriodService;

    @Autowired
    NationPeriodService nationPeriodService;

    @Autowired
    NationService nationService;

    @Autowired
    PeriodTagService periodTagService;

    @Transactional
    public Object add(Period period){
        if (period == null)
            return new Result(ResultMessage.PARAM_ERROR,getClass());

        if (StringUtils.isBlank(period.getTitle())){
            return new Result(ResultMessage.PARAM_ERROR,getClass());
        }

        if (periodMapper.selectByTitle(period.getTitle()) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,getClass());
        }

        if (StringUtils.isBlank(period.getId()))
            period.setId(Utils.generateId());

        return periodMapper.add(period);
    }




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

        if (eyear != null) {
            calendar.clear();
            calendar.set(Calendar.YEAR, eyear);
            calendar.set(Calendar.MONTH, emonth == null ? 0 : emonth);
            calendar.set(Calendar.DAY_OF_MONTH, eday == null ? 1 : eday);
            period.setEdate(calendar.getTime());
        }

        periodMapper.add(period);

//        JSONArray civilizationAry = params.getJSONArray("civilizations");
//        bindCivilization(civilizationAry, pid);
//
//        JSONArray nationAry = params.getJSONArray("nations");
//        bindNation(nationAry, pid);

        return new Result(ResultMessage.OK);
    }


    public Object getList(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("searchtitle");
        Integer start = params.getInteger("start");
        Integer size = params.getInteger("size");

        //默认
        List<HashMap> list = periodMapper.getList(title, start, size);
        r.put("data", list);
        r.put("total", periodMapper.getListTotal(title));

        return new Result(ResultMessage.OK, r);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Period> periods = periodMapper.selectLikeByTitle(title);

        JSONArray periodAry = new JSONArray();
        for (Period period : periods) {
            JSONObject periodObj = (JSONObject) JSON.toJSON(period);
            periodObj.put("pid", period.getId());
            periodAry.add(periodObj);
        }

        r.put("exist", periods.size() > 0);
        r.put("data", periodAry);

        return new Result(ResultMessage.OK, r);
    }

    public Object editInit(JSONObject params) {
        String id = params.getString("id");
        Period period = periodMapper.selectById(id);
        if (period == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject r = (JSONObject) JSONObject.toJSON(period);

        Date cdate = period.getCdate();
        int year = 1, month = 0, day = 0;
        if (cdate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cdate);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        r.put("year", year);
        r.put("month", month);
        r.put("day", day);

        Date edate = period.getEdate();
        int eyear, emonth, eday;
        if (edate != null) {
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
        r.put("nations", JSONObject.toJSON(nations));

        //civilizations
        List<HashMap> civilizations = civilizationPeriodMapper.selectCivilizationByPid(id);
        r.put("civilizations", JSONObject.toJSON(civilizations));

        return new Result(ResultMessage.OK, r);
    }


    // 初始化相关
    public Object editInitRelate(JSONObject params) {
        JSONObject data = new JSONObject();

        String id = params.getString("id");
        Period period = periodMapper.selectById(id);
        if (period == null)
            return new Result(ResultMessage.PARAM_ERROR);

        data.put("period", period);
        data.put("civilizations", civilizationPeriodMapper.selectCivilizationByPid(id));
        data.put("nations", nationPeriodMapper.selectNationByPid(id));

        return new Result(ResultMessage.OK, data);
    }

    // 添加文明
    public Object editAddRelateCivilization(JSONObject params) {
        String pid = params.getString("pid");
        JSONObject civilization = params.getJSONObject("civilization");

        return bindCivilization(civilization,pid);
    }

    // 删除文明
    public Object editDeleteRelateCivilization(JSONObject params) {
        String id = params.getString("id");
        return civilizationPeriodService.delete(id);
    }

    // 添加国家
    public Object editAddRelateNation(JSONObject params) {
        String pid = params.getString("pid");
        JSONObject nation = params.getJSONObject("nation");

        return bindNation(nation,pid);
    }

    // 删除国家
    public Object editDeleteRelateNation(JSONObject params) {
        String id = params.getString("id");
        return nationPeriodService.delete(id);
    }



    // 初始化贴条
    public Object editInitTags(JSONObject params) {
        JSONObject data = new JSONObject();

        String id = params.getString("id");
        Period period = periodMapper.selectById(id);
        if (period == null)
            return new Result(ResultMessage.PARAM_ERROR);

        data.put("period", period);
        data.put("tags", periodTagMapper.selectListByPid(id));

        return new Result(ResultMessage.OK, data);
    }

    // 添加贴条
    public Object editAddTag(JSONObject params) {
        String pid = params.getString("pid");
        JSONObject tag = params.getJSONObject("tag");
        return periodTagService.add(tag,pid);
    }

    // 删除贴条
    public Object editDeleteTag(JSONObject params) {
        String id = params.getString("id");
        return periodTagService.delete(id);
    }

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


        period.setContent(params.getString("content"));

        periodMapper.update(period);

        return new Result(ResultMessage.OK, period);
    }


    private void bindCivilization(JSONArray civilizationAry, String pid) {
        if (civilizationAry != null) {
            civilizationPeriodMapper.deleteByPid(pid);

            for (int i = 0; i < civilizationAry.size(); i++) {
                JSONObject object = civilizationAry.getJSONObject(i);
                String cid = object.getString("cid");

                Boolean isnew = object.getBoolean("isnew");
                if (isnew != null && isnew) {
                    String title = object.getString("title");
                    if (civilizationMapper.selectByTitle(title) == null) {
                        Civilization civilization = new Civilization();
                        cid = Utils.generateId();
                        civilization.setId(cid);
                        civilization.setTitle(title);
                        civilizationMapper.add(civilization);
                    } else {
                        return;
                    }
                }

                CivilizationPeriod civilizationPeriod = new CivilizationPeriod();
                civilizationPeriod.setId(Utils.generateId());
                civilizationPeriod.setCid(cid);
                civilizationPeriod.setPid(pid);

                if (civilizationPeriodMapper.selectByCidAndPid(civilizationPeriod) == null) {
                    civilizationPeriodMapper.add(civilizationPeriod);
                }

            }
        }
    }

    private Object bindCivilization(JSONObject civilizationObj, String pid) {
        Period period = periodMapper.selectById(pid);
        if (period == null)
            return new Result(ResultMessage.PARAM_ERROR,"时代ID错误");

        String cid = civilizationObj.getString("id");
        Boolean isnew = civilizationObj.getBoolean("isnew");
        if (isnew != null && isnew) {
            String title = civilizationObj.getString("title");
            Civilization civilization = new Civilization();
            cid = Utils.generateId();
            civilization.setId(cid);
            civilization.setTitle(title);
            Result r = (Result) civilizationService.add(civilization);
            if (!Result.isOk(r))
                return r;
        }

        CivilizationPeriod civilizationPeriod = new CivilizationPeriod();
        civilizationPeriod.setId(Utils.generateId());
        civilizationPeriod.setCid(cid);
        civilizationPeriod.setPid(pid);
        return civilizationPeriodService.add(civilizationPeriod);
    }

    private Object bindNation(JSONObject nationObj, String pid) {
        Period period = periodMapper.selectById(pid);
        if (period == null)
            return new Result(ResultMessage.PARAM_ERROR,"时代ID错误");

        String nid = nationObj.getString("id");
        Boolean isnew = nationObj.getBoolean("isnew");
        if (isnew != null && isnew) {
            String title = nationObj.getString("title");
            Nation nation = new Nation();
            nid = Utils.generateId();
            nation.setId(nid);
            nation.setTitle(title);
            Result r = (Result) nationService.add(nation);
            if (!Result.isOk(r))
                return r;
        }

        NationPeriod nationPeriod = new NationPeriod();
        nationPeriod.setId(Utils.generateId());
        nationPeriod.setNid(nid);
        nationPeriod.setPid(pid);
        return nationPeriodService.add(nationPeriod);
    }

    private void bindNation(JSONArray nationAry, String pid) {
        if (nationAry != null) {
            nationPeriodMapper.deleteByPId(pid);

            for (int i = 0; i < nationAry.size(); i++) {
                JSONObject object = nationAry.getJSONObject(i);
                String nid = object.getString("nid");

                boolean isnew = object.getBoolean("isnew");
                if (isnew) {
                    String title = object.getString("title");
                    if (nationMapper.selectLikeByTitle(title) == null) {
                        Nation nation = new Nation();
                        nid = Utils.generateId();
                        nation.setId(nid);
                        nation.setTitle(title);
                        nationMapper.add(nation);
                    } else {
                        return;
                    }
                }

                NationPeriod nationPeriod = new NationPeriod();
                nationPeriod.setId(Utils.generateId());
                nationPeriod.setNid(nid);
                nationPeriod.setPid(pid);

                if (nationPeriodMapper.selectByNidAndPid(nationPeriod) == null) {
                    nationPeriodMapper.add(nationPeriod);
                }

            }
        }
    }


}
