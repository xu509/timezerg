package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.*;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 * 人物
 */
@Service
public class GiantService {

    @Autowired
    GiantMapper giantMapper;

    @Autowired
    GiantTagMapper giantTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    GiantNationMapper giantNationMapper;

    @Autowired
    NationMapper nationMapper;


    @Transactional
    public Object add(JSONObject params) {
        Giant giant = new Giant();
        String gid = Utils.generateId();
        giant.setId(gid);

        String name = params.getString("name");
        if (giantMapper.selectByName(name) != null){
            return new Result(ResultMessage.PARAM_ERROR,"已经存在");
        }

        giant.setName(name);
        giant.setContent(params.getString("content"));
        giantMapper.add(giant);

        //tag
        bindTag(params.getJSONArray("tags"),gid);
        //nations
        bindNations(params.getJSONArray("nations"),gid);

        return new Result(ResultMessage.OK, giant);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Giant> giants = giantMapper.selectLikeByTitle(title);

        boolean same = false;

        JSONArray jsonArray = new JSONArray();
        for (Giant giant : giants){
            JSONObject obj = (JSONObject) JSON.toJSON(giant);

            String gid = giant.getId();
            String sname = giant.getName();
            String id = giant.getId();

            if (giant.getName().equals(title))
                same = true;

            if (!StringUtils.isBlank(giant.getPid())){
                String pname = giantMapper.selectById(giant.getPid()).getName();
                obj.put("pname",pname);
                sname = pname + " - " + giant.getName();
                gid = giant.getPid();
                id = gid;
            }
            obj.put("sname",sname);
            obj.put("gid",gid);
            obj.put("id",id);
            jsonArray.add(obj);
        }


        r.put("exist", giants.size() > 0);
        r.put("same",same);
        r.put("data", jsonArray);

        return new Result(ResultMessage.OK, r);
    }


    public Object getList(JSONObject params) {
//        Object[] p = {params.getString("name"),params.getInteger("start"), params.getInteger("size")};
        List<HashMap> list = giantMapper.getList(params.getString("name"),params.getInteger("start"),params.getInteger("size"));

        for (HashMap row : list){
            String gid = (String) row.get("id");
            row.put("nations",giantNationMapper.selectByGid(gid));

            row.put("tags",giantTagMapper.selectTagsByGid(gid));

            long cgnum = giantMapper.selectCountByPid(gid);
            if (cgnum > 0){
                row.put("cgiants",giantMapper.selectByPid(gid));
            }
        }


        JSONObject r = new JSONObject();
        r.put("data", list);
        r.put("total", giantMapper.getListTotal(params.getString("name")));
        return new Result(ResultMessage.OK, r);
    }

    public Object editInit(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject obj = (JSONObject) JSON.toJSON(giant);
        Giant pgiant = giantMapper.selectById(giant.getPid());

        if (pgiant != null){
            obj.put("pgiant",pgiant);
        }

        // tags
//        List<HashMap> tagMaps = giantTagMapper.selectTagsByGid(id);
////        for (HashMap tagMap : tagMaps){
////            tagMap.put("")
////        }
//        obj.put("tags",tagMaps);
//
//        //nations
//        List<HashMap> nationsMap = giantNationMapper.selectByGid(id);
//        obj.put("nations",nationsMap);

        return new Result(ResultMessage.OK,obj);
    }

    @Transactional
    public Object editSave(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        giant.setContent(params.getString("content"));
        String name = params.getString("name");

        if (!(name.equals(giant.getName())) && (giantMapper.selectByName(name) != null)){
            return new Result(ResultMessage.PARAM_ERROR);
        }

        giant.setName(params.getString("name"));

        JSONObject pgiantObj = params.getJSONObject("pgiant");
        Boolean isnew = pgiantObj.getBoolean("isnew");
        String pid = pgiantObj.getString("gid");
        if (isnew != null && isnew){
            Giant pGiant = new Giant();
            pid = Utils.generateId();
            pGiant.setId(pid);
            pGiant.setName(pgiantObj.getString("name"));
            giantMapper.add(pGiant);
        }
        if (!StringUtils.isBlank(pid)){
            while (!StringUtils.isBlank(giantMapper.selectById(pid).getPid())){
                pid = giantMapper.selectById(pid).getPid();
            }
            giant.setPid(pid);
        }
        giantMapper.update(giant);

        return new Result(ResultMessage.OK,giant);
    }


    /**
     *  绑定TAG
     */
    private void bindTag(JSONArray tagsAry,String gid){
        System.out.println(JSON.toJSONString(tagsAry));

        //tags
        if (tagsAry != null){
            giantTagMapper.deleteByGId(gid);

            for (int x = 0 ; x < tagsAry.size();x++){
                JSONObject giantTagObj = tagsAry.getJSONObject(x);
                Boolean isnew = giantTagObj.getBoolean("isnew");
                String tid = giantTagObj.getString("tid");

                if (isnew != null && isnew){
                    Tag tag = new Tag();
                    tid = Utils.generateId();
                    tag.setId(tid);
                    tag.setTitle(giantTagObj.getString("title"));
                    tagMapper.add(tag);
                }

                //绑定
                GiantTag giantTag = new GiantTag();
                giantTag.setId(Utils.generateId());
                giantTag.setGid(gid);
                giantTag.setTid(tid);
                giantTagMapper.add(giantTag);
            }
        }
    }

    /**
     *  绑定Nations
     */
    private void bindNations(JSONArray nationAry,String gid){
        //tags
        if (nationAry != null){
            giantNationMapper.deleteByGid(gid);

            for (int x = 0 ; x < nationAry.size();x++){
                JSONObject nationObj = nationAry.getJSONObject(x);
                Boolean isnew = nationObj.getBoolean("isnew");
                String nid = nationObj.getString("nid");

                if (isnew != null && isnew){
                    Nation nation = new Nation();
                    nid = Utils.generateId();
                    nation.setId(nid);
                    nation.setTitle(nationObj.getString("title"));
                    nationMapper.add(nation);
                }

                //绑定
                GiantNation giantNation = new GiantNation();
                giantNation.setId(Utils.generateId());
                giantNation.setGid(gid);
                giantNation.setNid(nid);
                giantNationMapper.add(giantNation);
            }
        }
    }
}
