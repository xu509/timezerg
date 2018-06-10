package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.GiantMapper;
import com.timezerg.api.mapper.GiantTagMapper;
import com.timezerg.api.mapper.TagMapper;
import com.timezerg.api.model.Giant;
import com.timezerg.api.model.GiantTag;
import com.timezerg.api.model.Nation;
import com.timezerg.api.model.Tag;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
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


    @Transactional
    public Object add(JSONObject params) {
        Giant giant = new Giant();
        giant.setId(Utils.generateId());
        giant.setName(params.getString("name"));
        giant.setContent(params.getString("content"));

        giantMapper.add(giant);

        return new Result(ResultMessage.OK, giant);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Giant> giants = giantMapper.selectLikeByTitle(title);

        JSONArray jsonArray = new JSONArray();
        for (Giant giant : giants){
            JSONObject obj = (JSONObject) JSON.toJSON(giant);
            obj.put("gid",giant.getId());
            jsonArray.add(obj);
        }


        r.put("exist", giants.size() > 0);
        r.put("data", jsonArray);

        return new Result(ResultMessage.OK, r);
    }


    public Object getList(JSONObject params) {
        Object[] p = {params.getInteger("start"), params.getInteger("size")};
        List<HashMap> list = giantMapper.getList(p);
        JSONObject r = new JSONObject();
        r.put("data", list);
        r.put("total", giantMapper.getListTotal(p));
        return new Result(ResultMessage.OK, r);
    }

    public Object editInit(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject obj = (JSONObject) JSON.toJSON(giant);
        // tags
        List<HashMap> tagMaps = giantTagMapper.selectTagsByGid(id);
//        for (HashMap tagMap : tagMaps){
//            tagMap.put("")
//        }
        obj.put("tags",tagMaps);
        return new Result(ResultMessage.OK,obj);
    }

    @Transactional
    public Object editSave(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        giant.setContent(params.getString("content"));
//        giantMapper.update(giant);

        //tags
        JSONArray tagsAry = params.getJSONArray("tags");
        if (tagsAry != null){
            giantTagMapper.deleteByGId(id);

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
                giantTag.setGid(id);
                giantTag.setTid(tid);
                giantTagMapper.add(giantTag);

            }


        }

        return new Result(ResultMessage.OK,giant);
    }

}
