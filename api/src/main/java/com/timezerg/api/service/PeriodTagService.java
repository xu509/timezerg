
package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.NationPeriod;
import com.timezerg.api.model.PeriodTag;
import com.timezerg.api.model.Tag;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class PeriodTagService {

    @Autowired
    PeriodTagMapper periodTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    PeriodMapper periodMapper;

    public Object add(JSONObject tag, String pid){
        if (periodMapper.selectById(pid) == null){
            return new Result(ResultMessage.PARAM_ERROR,"时代贴条，时代ID错误");
        }

        Boolean isnew = tag.getBoolean("isnew");
        String tid = tag.getString("tid");
        if (isnew != null && isnew){
            tid = Utils.generateId();
            //新的，先添加tag
            Tag t = new Tag();
            t.setId(tid);
            t.setTitle(tag.getString("title"));
            tagMapper.add(t);
        }

        PeriodTag periodTag = new PeriodTag();
        periodTag.setId(Utils.generateId());
        periodTag.setPid(pid);
        periodTag.setTid(tid);
        if (periodTagMapper.selectByPidAndTid(periodTag) != null)
            return new Result(ResultMessage.DUPLICATION_ERROR,"时代贴条");

        periodTagMapper.add(periodTag);
        return new Result(ResultMessage.OK,periodTag);
    }

    public Object delete(String id){
        if (periodTagMapper.selectById(id) == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }else {
            periodTagMapper.deleteById(id);
            return new Result(ResultMessage.OK);
        }
    }



}
