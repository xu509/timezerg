package com.timezerg.api.service;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.CivilizationMapper;
import com.timezerg.api.mapper.ContinentMapper;
import com.timezerg.api.model.Civilization;
import com.timezerg.api.model.Continent;
import com.timezerg.api.util.DateUtil;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Service
public class ContinentService {

    @Autowired
    ContinentMapper continentMapper;


    public Object add(JSONObject params){
        Continent continent = new Continent();
        continent.setId(Utils.generateId());
        continent.setTitle(params.getString("title"));

        continentMapper.add(continent);

        return new Result(ResultMessage.OK,continent);
    }


}
