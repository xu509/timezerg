package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.config.AppConfig;
import com.timezerg.api.mapper.CivilizationContinentMapper;
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
public class CivilizationService {

    @Autowired
    CivilizationMapper civilizationMapper;

    @Autowired
    CivilizationContinentMapper civilizationContinentMapper;

    @Autowired
    NodeMapper nodeMapper;


    public Object add(JSONObject params){
        Civilization civil = new Civilization();
        civil.setId(Utils.generateId());
        civil.setCdate(new Date());
        civil.setPid(params.getString("pid"));
        civil.setCover(null);
        civil.setContent(null);
        civil.setTitle(params.getString("title"));

        civilizationMapper.add(civil);

        return new Result(ResultMessage.OK,civil);
    }


    public Object getList(JSONObject params){
        Object[] p = {params.getInteger("start"),params.getInteger("size")};
        List<HashMap> list = civilizationMapper.getList(p);
        for(HashMap row : list){
            String id = (String) row.get("id");

            row.put("cdate", DateUtil.format((Date) row.get("cdate"),DateUtil.YEAR_MONTH_DAY_TIME));

            String cover = (String) row.get("cover");
            if (StringUtils.isBlank(cover)){
                cover = AppConfig.DEFAULT.CIVILIZATION_COVER;
            }else {
                cover = AppConfig.Image_Domain + cover;
            }
            row.put("cover",cover);


            //相关大洲
            row.put("continents",civilizationContinentMapper.selectByCivilizationId(id));
        }

        JSONObject r = new JSONObject();
        r.put("data",list);
        r.put("total",civilizationMapper.getListTotal(p));
        return new Result(ResultMessage.OK,r);
    }

    public Object selectByTitle(JSONObject params){
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Civilization> nations = civilizationMapper.selectLikeByTitle(title);
        r.put("exist",nations.size() > 0);
        r.put("data",nations);

        return new Result(ResultMessage.OK,r);
    }

    public Object editInit(JSONObject params){
        JSONObject r;
        String id = params.getString("id");

        Civilization civilization = civilizationMapper.selectById(id);

        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR);

        r = (JSONObject) JSONObject.toJSON(civilization);

        Civilization p = civilizationMapper.selectById(civilization.getPid());
        if (p != null){
            r.put("pid",p.getId());
            r.put("pname",p.getTitle());
        }


        //获取绑定大洲
        r.put("continents",civilizationContinentMapper.selectByCivilizationId(id));

        return new Result(ResultMessage.OK,r);
    }

    @Transactional
    public Object edit(JSONObject params){
        String id = params.getString("id");
        String title = params.getString("title");
        String cover = params.getString("cover");
        String pid = params.getString("pid");

        Civilization civilization = civilizationMapper.selectById(id);
        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR);

        civilization.setTitle(title);
        civilization.setCover(cover);

        if (!StringUtils.isBlank(pid))
            civilization.setPid(pid);

        civilizationMapper.update(civilization);

        //修改绑定的大洲
        civilizationContinentMapper.deleteByCivilizationId(id);
        JSONArray continents = params.getJSONArray("continents");
        for (int i = 0 ; i < continents.size() ; i ++){
            JSONObject continent = continents.getJSONObject(i);
            CivilizationContinent civilizationContinent = new CivilizationContinent();
            civilizationContinent.setId(Utils.generateId());
            civilizationContinent.setCivilizationid(id);
            civilizationContinent.setContinentid(continent.getString("continentid"));
            civilizationContinentMapper.add(civilizationContinent);
        }

        return new Result(ResultMessage.OK,civilization);
    }

    private static Integer Time_List_Size = 15;

    @Transactional
    public Object timeLine(JSONObject params){
        JSONObject resultObj = new JSONObject();

        String id = params.getString("id");
        Integer level = params.getInteger("level");
        Integer p = params.getInteger("p");

        //P从0开始
        Integer start = p * 15;

        List<HashMap> datas = civilizationMapper.selectTimeLine(id,level,start,Time_List_Size);
        Civilization civilization = civilizationMapper.selectById(id);

        resultObj.put("civilization",civilization);

        for (HashMap data : datas){
            Date date = (Date) data.get("cdate");
            Integer ad = (Integer) data.get("ad");

            StringBuffer showDate = new StringBuffer();
            if (ad == Node.BC_VALUE){
                showDate.append("前 ");
            }

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            Integer year = c.get(Calendar.YEAR);
            showDate.append(year);

            data.put("showDate",showDate.toString());
        }

        resultObj.put("timeline",datas);

//        System.out.println(JSON.toJSON(datas));

        return new Result(ResultMessage.OK,resultObj);
    }


    @Transactional
    public Object setFirstSort(JSONObject params){
        String id = params.getString("id");
        Civilization civilization = civilizationMapper.selectById(id);

        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR);

        Integer min = civilizationMapper.selectMinSort();
        civilizationMapper.updateSortById(new Object[]{min - 1 , id});
        return new Result(ResultMessage.OK);
    }


    @Transactional
    public Object setDownSort(JSONObject params){
        String id = params.getString("id");
        String tid = params.getString("tid");
        Civilization civilization = civilizationMapper.selectById(id);
        Civilization tCivilization = civilizationMapper.selectById(tid);

        if (civilization == null || tCivilization == null)
            return new Result(ResultMessage.PARAM_ERROR);


        Integer sort = civilization.getSort();
        Integer tsort = tCivilization.getSort();

        civilizationMapper.updateSortById(new Object[]{sort , tCivilization.getId()});
        civilizationMapper.updateSortById(new Object[]{tsort , civilization.getId()});
        return new Result(ResultMessage.OK);
    }


    @Transactional
    public Object index(JSONObject params){
        JSONObject result = new JSONObject();

        List<HashMap> civilizationsMaps = civilizationMapper.getList(new Object[]{0,2});

        Set<NodeApiBean> nodeApiBeanSet = new HashSet<>();

        for (HashMap civilMap : civilizationsMaps){
            JSONObject civilObj = (JSONObject) JSON.toJSON(civilMap);
            //获取node
            List<HashMap> nodesMap = nodeMapper.getList(new Object[]{civilMap.get("id")},0,50);

            for(HashMap node : nodesMap){
                NodeApiBean nodeApiBean = new NodeApiBean();
                nodeApiBean.setCdate((Date) node.get("cdate"));
                nodeApiBean.setDdate((String) node.get("ddate"));

                System.out.println("node api bean :" + nodeApiBean);
                System.out.println("node:" + node);

                nodeApiBean.setAD((Integer) node.get("ad"));

                NodeDetailApiBean detail = new NodeDetailApiBean();
                detail.setCid(civilObj.getString("id"));
                detail.setCname(civilObj.getString("title"));
                detail.setNid((String) node.get("id"));
                detail.setNname((String) node.get("title"));

                nodeApiBean.addDetail(detail);
                nodeApiBeanSet.add(nodeApiBean);
            }
        }

        List<NodeApiBean> list = new ArrayList<>();
        list.addAll(nodeApiBeanSet);


        Collections.sort(list);

        result.put("data",JSON.toJSON(list));
        result.put("civilizations",JSON.toJSON(civilizationsMaps));
//        result.put("times",timeAry);

        System.out.println(JSON.toJSON(list));


        //所有时间

        return new Result(ResultMessage.OK,result);
    }







}
