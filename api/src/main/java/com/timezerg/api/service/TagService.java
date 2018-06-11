package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.TagMapper;
import com.timezerg.api.model.Tag;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 * 国家服务类
 */
@Service
public class TagService {

    @Autowired
    TagMapper tagMapper;

    @Transactional
    public Object add(JSONObject params) {
        Tag tag = new Tag();
        tag.setId(Utils.generateId());
        tag.setTitle(params.getString("title"));
        tagMapper.add(tag);

        return new Result(ResultMessage.OK, tag);
    }


    @Transactional
    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();
        String title = params.getString("sw");
        List<Tag> tags = tagMapper.selectLikeByTitle(title);

        JSONArray tagAry = new JSONArray();
        for (Tag tag :tags){
            JSONObject tagObj = (JSONObject) JSON.toJSON(tag);
            tagObj.put("tid",tag.getId());
            tagAry.add(tagObj);
        }


        r.put("exist", tags.size() > 0);
        r.put("data", tagAry);

        return new Result(ResultMessage.OK, r);
    }




}
