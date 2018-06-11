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
 * 国家服务类
 */
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;

    @Autowired
    GiantMapper giantMapper;

    @Autowired
    GiantNationMapper giantNationMapper;

    @Autowired
    NationInstitutionMapper nationInstitutionMapper;

    @Autowired
    InstitutionMapper institutionMapper;


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
            nation.setPid(null);
        else
            nation.setPid(pid);
        nationMapper.add(nation);

        return new Result(ResultMessage.OK, nation);
    }


    public Object getList(JSONObject params) {
        Integer type = params.getInteger("type");
        JSONObject r = new JSONObject();

        if (type == 1){
            //默认
            Object[] p = {params.getInteger("start"), params.getInteger("size")};
            List<HashMap> list = nationMapper.getList(p);
            r.put("data", list);
            r.put("total", nationMapper.getListTotal(p));
        }else if (type == 2){
            //顶级
            Object[] p = {params.getInteger("start"), params.getInteger("size")};
            List<HashMap> list = nationMapper.getTopList(p);
            r.put("data", list);
            r.put("total", nationMapper.getTopListTotal(p));
        }else if (type == 3){
            //未完善
            Object[] p = {params.getInteger("start"), params.getInteger("size")};
            List<HashMap> list = nationMapper.getUncheckList(p);
            r.put("data", list);
            r.put("total", nationMapper.getUncheckListTotal(p));
        }
        return new Result(ResultMessage.OK, r);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Nation> nations = nationMapper.selectLikeByTitle(title);

        JSONArray nationAry = new JSONArray();
        for (Nation nation : nations){
            JSONObject nationObj = (JSONObject) JSON.toJSON(nation);
            nationObj.put("nid",nation.getId());
            nationAry.add(nationObj);
        }

        r.put("exist", nations.size() > 0);
        r.put("data", nationAry);

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


        Nation fnation = nationMapper.selectById(nation.getFid());
        if (fnation != null)
            r.put("fnation", fnation.getTitle());

        //giant
        List<HashMap> giants = giantNationMapper.selectByNid(id);
        for (HashMap giant : giants){
            giant.put("isnew",false);
        }
        r.put("giant",JSONObject.toJSON(giants));

        //institution
        List<HashMap> institution = nationInstitutionMapper.selectByNid(id);
//        for (HashMap giant : institution){
//            giant.put("isnew",false);
//        }
        r.put("institution",JSONObject.toJSON(institution));

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
        nation.setFid(params.getString("fid"));

        nation.setInvent(params.getString("invent"));

        nationMapper.update(nation);


        //重要人物
        JSONArray giants = params.getJSONArray("giant");
        if (giants != null){
            giantNationMapper.deleteByNid(id);

            for (int i = 0 ; i < giants.size(); i++){
                JSONObject giant = giants.getJSONObject(i);
                String name = giant.getString("name");
                String giantId;

                Boolean isNewFlag = giant.getBoolean("isnew");

                if (isNewFlag!= null && isNewFlag){
                    //增加并绑定
                    Giant giant1 = new Giant();
                    giantId = Utils.generateId();

                    giant1.setId(giantId);
                    giant1.setName(name);
                    giantMapper.add(giant1);

                }else {
                    giantId = giant.getString("gid");
                    //绑定
                }

                GiantNation giantNation = new GiantNation();
                giantNation.setId(Utils.generateId());
                giantNation.setGid(giantId);
                giantNation.setNid(id);

                if (giantNationMapper.selectByGidAndNid(giantNation) == null){
                    giantNationMapper.add(giantNation);
                }

            }
        }

        //制度
        JSONArray institution = params.getJSONArray("institution");
        bindInstitution(institution,id);

        return new Result(ResultMessage.OK, nation);
    }


    private void bindInstitution(JSONArray institutionAry,String nid){
        if (institutionAry != null){
            nationInstitutionMapper.deleteByNid(nid);

            for (int i = 0 ; i < institutionAry.size(); i++){
                JSONObject object = institutionAry.getJSONObject(i);
                String iid = object.getString("iid");

                boolean isnew = object.getBoolean("isnew");
                if (isnew){
                    String title = object.getString("title");
                    if (institutionMapper.selectByTitle(title) == null){
                        Institution institution = new Institution();
                        iid = Utils.generateId();
                        institution.setId(iid);
                        institution.setTitle(title);
                        institutionMapper.add(institution);
                    }else {
                        return;
                    }
                }

                NationInstitution nationInstitution = new NationInstitution();
                nationInstitution.setId(Utils.generateId());
                nationInstitution.setNid(nid);
                nationInstitution.setIid(iid);


                if (nationInstitutionMapper.selectByGidAndNid(nationInstitution) == null){
                    nationInstitutionMapper.add(nationInstitution);
                }

            }
        }
    }




}
