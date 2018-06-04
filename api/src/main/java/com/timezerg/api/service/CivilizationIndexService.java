package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.config.AppConfig;
import com.timezerg.api.mapper.CivilizationContinentMapper;
import com.timezerg.api.mapper.CivilizationIndexMapper;
import com.timezerg.api.mapper.CivilizationMapper;
import com.timezerg.api.mapper.NodeMapper;
import com.timezerg.api.model.*;
import com.timezerg.api.util.DateUtil;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class CivilizationIndexService {

    @Autowired
    CivilizationIndexMapper civilizationIndexMapper;


    public Object add(String cid){
        CivilizationIndex bean = civilizationIndexMapper.selectByCid(cid);
        if (bean != null)
            return new Result(ResultMessage.PARAM_ERROR,"已经有了");

        CivilizationIndex civilizationIndex = new CivilizationIndex();
        civilizationIndex.setId(Utils.generateId());
        civilizationIndex.setCid(cid);
        civilizationIndex.setCdate(new Date());
        civilizationIndexMapper.add(civilizationIndex);

        return new Result(ResultMessage.OK,civilizationIndex);
    }

    public Object del(String id){
        CivilizationIndex bean = civilizationIndexMapper.selectById(id);
        if (bean == null)
            return new Result(ResultMessage.PARAM_ERROR);

        civilizationIndexMapper.delete(id);
        return new Result(ResultMessage.OK);
    }



    public Object getList(){
        return new Result(ResultMessage.OK,civilizationIndexMapper.selectList());
    }



}
