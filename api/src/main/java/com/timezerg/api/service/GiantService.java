package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.GiantMapper;
import com.timezerg.api.model.Giant;
import com.timezerg.api.model.Nation;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 * 人物
 */
@Service
public class GiantService {

    @Autowired
    GiantMapper giantMapper;


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
        r.put("exist", giants.size() > 0);
        r.put("data", giants);

        return new Result(ResultMessage.OK, r);
    }



}
