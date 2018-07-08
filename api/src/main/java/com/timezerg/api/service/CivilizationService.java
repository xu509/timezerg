package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.corba.se.impl.oa.toa.TOA;
import com.timezerg.api.config.AppConfig;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.*;
import com.timezerg.api.model.api.TimeApiBean;
import com.timezerg.api.util.DateUtil;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
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

    @Autowired
    CivilizationIndexMapper civilizationIndexMapper;

    @Autowired
    NodeCivilizationMapper nodeCivilizationMapper;

    @Autowired
    NodeCivilizationService nodeCivilizationService;

    @Autowired
    CivilizationPeriodMapper civilizationPeriodMapper;

    @Autowired
    CivilizationPeriodService civilizationPeriodService;

    @Autowired
    PeriodService periodService;

    @Autowired
    NationPeriodService nationPeriodService;

    @Autowired
    NodeNationService nodeNationService;

    @Autowired
    NodeTagService nodeTagService;


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

    /**
     *  保存文明
     */
    public Object add(Civilization civilization){
        if (civilization == null || StringUtils.isBlank(civilization.getTitle())){
            return new Result(ResultMessage.PARAM_ERROR,"文明创建错误");
        }
        //
        if (civilizationMapper.selectByTitle(civilization.getTitle()) != null)
            return new Result(ResultMessage.DUPLICATION_ERROR,"文明创建错误");

        civilizationMapper.add(civilization);
        return new Result(ResultMessage.OK,civilization);
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
        Integer level = params.getInteger("level");

        JSONObject result = new JSONObject();

        List<HashMap> civilizationMaps = civilizationIndexMapper.selectList();

        //整个时间节点
        List<TimeApiBean> timeApiBeanList = new ArrayList<>();
        HashSet<TimeApiBean> timeApiBeenSets = new HashSet<>();

        //包含多个文明的数组
        JSONArray timelines = new JSONArray();

        for (HashMap civilMap : civilizationMaps){
            String cid = (String) civilMap.get("cid");
            String ctitle = (String) civilMap.get("title");

            NodeApiBean apiBean = new NodeApiBean();
            apiBean.setCid(cid);
            apiBean.setCname(ctitle);

//            System.out.println(ctitle + " " + cid);

            List<HashMap> rows = civilizationMapper.selectTimeLine(cid,level,0,50);

            if (cid.equals("938543987843665920")){
                System.out.println("row : " + rows.size());
            }


            List<NodeDetailApiBean> nodeDetailApiBeens = new ArrayList<>();
            for (HashMap row : rows){
//                System.out.println(JSON.toJSONString(row));

                TimeApiBean timeBean = new TimeApiBean();
                timeBean.setCdate((Date) row.get("cdate"));
                timeBean.setAD((Integer) row.get("ad"));
                timeBean.setDdate((String) row.get("ddate"));
                timeApiBeenSets.add(timeBean);

                NodeDetailApiBean nodeDetailApiBean = new NodeDetailApiBean();
                nodeDetailApiBean.setAD((Integer) row.get("ad"));
                nodeDetailApiBean.setCdate((Date) row.get("cdate"));

                Node node = new Node();
                node.setTitle((String) row.get("title"));
                node.setId((String) row.get("id"));
                nodeDetailApiBean.addNode(node);

                nodeDetailApiBeens.add(nodeDetailApiBean);
            }
            Collections.sort(nodeDetailApiBeens);
            apiBean.setBeans(nodeDetailApiBeens);

//            System.out.println(JSON.toJSONString(apiBean));


            timelines.add(apiBean);
        }

        result.put("timelines",JSON.toJSON(timelines));

        timeApiBeanList.addAll(timeApiBeenSets);
        Collections.sort(timeApiBeanList);



        //开始处理成适合WEB输出的格式


        for (int i = 0 ; i < timelines.size() ; i ++){
            NodeApiBean apiBean = (NodeApiBean) timelines.get(i);
            List<NodeDetailApiBean> apiBeans = apiBean.getBeans();
            int apiBeans_x = 0;
            List<NodeDetailApiBean> rBean = new ArrayList<>();

            for (TimeApiBean timeApiBean : timeApiBeanList){

                if (apiBeans_x < apiBeans.size()){
                    NodeDetailApiBean bean = apiBeans.get(apiBeans_x);

//                    System.out.println("比较开始: " + bean.toString() + " | " + timeApiBean.toString());

                    while (Utils.compareTime(timeApiBean.getAD(),timeApiBean.getCdate(),bean.getAD(),bean.getCdate()) == 1){
                        //当时间数组当时间大于事件数组的时间

                        rBean.get(rBean.size()-1).addNode(bean.getNodes());
                        apiBeans_x++;

                        bean = apiBeans.get(apiBeans_x);

                    }



                    if (Utils.compareTime(timeApiBean.getAD(),timeApiBean.getCdate(),bean.getAD(),bean.getCdate()) == 0){
                        //如果时间点存在，直接增加
                        rBean.add(bean);
                        apiBeans_x++;
//                        System.out.println("相等");

                    } else {
                        NodeDetailApiBean nodeDetailApiBean = new NodeDetailApiBean();
                        nodeDetailApiBean.initBlackBean();
                        rBean.add(nodeDetailApiBean);
//                        System.out.println("没有");

                    }
                }else {
                    NodeDetailApiBean nodeDetailApiBean = new NodeDetailApiBean();
                    nodeDetailApiBean.initBlackBean();
                    rBean.add(nodeDetailApiBean);
                }

            }

            apiBean.setBeans(rBean);

        }

        result.put("timelines",JSON.toJSON(timelines));
        result.put("times",JSON.toJSON(timeApiBeanList));

        //所有时间
        return new Result(ResultMessage.OK,result);
    }

    public Object editInitNodes(JSONObject params){
        JSONObject r = new JSONObject();
        String id = params.getString("id");
        Integer level = params.getInteger("level");
        Civilization civilization = civilizationMapper.selectById(id);

        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR);

        r.put("civilization",civilization);

        //nodes
        Integer start = params.getInteger("start");
        Integer size = params.getInteger("size");
        String title = params.getString("title");

        List<HashMap> nodeMaps = nodeCivilizationMapper.selectNodesByCid(id,level,title,start,size);
        for (HashMap nodeMap : nodeMaps){
            Integer levelInt  = (Integer) nodeMap.get("l");
            nodeMap.put("levelstr",Utils.getLevelStr(levelInt));

            //设置tag
            nodeMap.put("tags",nodeTagService.selectTagsFromNode((String) nodeMap.get("id")));

        }

        r.put("nodes",nodeMaps);
        r.put("nodestotal",nodeCivilizationMapper.selectNodesTotalByCid(id,level,title));


        return new Result(ResultMessage.OK,r);
    }


    public Object updateNodeLevel(JSONObject params){
        Integer level = params.getInteger("level");
        String id = params.getString("id");
        return nodeCivilizationService.updateLevel(id,level);
    }


    /* --- 初始化相关 --- */
    public Object editInitRelate(JSONObject params){
        JSONObject r = new JSONObject();
        String id = params.getString("id");
        Civilization civilization = civilizationMapper.selectById(id);
        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR,"ID is null");

        // relate
        List<HashMap> periods = civilizationPeriodMapper.selectPeriodsByCid(id);
        r.put("civilization",civilization);
        r.put("periods",periods);

        return new Result(ResultMessage.OK,r);
    }

    /**
     *  同步索引
     */
    public  Object syncCivilizationPeriod(JSONObject params){
        String id = params.getString("id");
        if (civilizationMapper.selectById(id) == null)
            return new Result(ResultMessage.PARAM_ERROR,"id is wrong");

        List<CivilizationPeriod> civilizationPeriods = civilizationPeriodMapper.selectByCid(id);
        int sort = 0;
        for (CivilizationPeriod civilizationPeriod : civilizationPeriods){
            civilizationPeriod.setSort(sort);
            civilizationPeriodMapper.updateSort(civilizationPeriod);
            sort++;
        }
        return new Result(ResultMessage.OK);
    }

    /**
     *  添加时代
     */
    @Transactional
    public Object savePeriod(JSONObject params){
        String cid = params.getString("id");

        if (civilizationMapper.selectById(cid) == null){
            return new Result(ResultMessage.PARAM_ERROR,"id 错误");
        }

        JSONObject period = params.getJSONObject("period");
        String pid = period.getString("pid");

        Boolean isnew = period.getBoolean("isnew");
        if (isnew != null && isnew){
            // 先添加 period
            pid = Utils.generateId();
            Period period1 = new Period();
            period1.setId(pid);
            period1.setTitle(period.getString("title"));
            Result r = (Result) periodService.add(period1);
            if (!Result.isOk(r)){
                return r;
            }
        }

        // 添加联系
        CivilizationPeriod civilizationPeriod = new CivilizationPeriod();
        civilizationPeriod.setPid(pid);
        civilizationPeriod.setCid(cid);

        return civilizationPeriodService.add(civilizationPeriod);
    }

    /**
     *  删除时代
     */
    @Transactional
    public Object deletePeriod(JSONObject params){
        String id = params.getString("id");
        return civilizationPeriodService.delete(id);
    }

    /**
     *  删除时代
     */
    @Transactional
    public Object exchangePeriod(JSONObject params){
        String oldId = params.getString("oldid");
        String newId = params.getString("newid");

        CivilizationPeriod o = civilizationPeriodMapper.selectById(oldId);
        if (o == null)
            return new Result(ResultMessage.PARAM_ERROR,"old id is wrong");

        CivilizationPeriod n = civilizationPeriodMapper.selectById(newId);
        if (n == null)
            return new Result(ResultMessage.PARAM_ERROR,"new id is wrong");

        Integer oldsort = o.getSort();
        Integer newsort = n.getSort();

        o.setSort(newsort);
        n.setSort(oldsort);

        civilizationPeriodMapper.updateSort(o);
        civilizationPeriodMapper.updateSort(n);

        return new Result(ResultMessage.OK);
    }

    /**
     *  同步节点
     */
    @Transactional
    public Object syncNode(JSONObject params){
        String id = params.getString("id");
        Civilization civilization = civilizationMapper.selectById(id);
        if (civilization == null)
            return new Result(ResultMessage.PARAM_ERROR,"ID 错误");

        //查找文明下的所有时代（包括子时代）
        List<Period> periods = civilizationPeriodService.getAllPeriods(id);

        //查找时代下的所有国家
        List<Nation> nationList = new ArrayList<>();
        for (Period period : periods){
            List<Nation> nations = nationPeriodService.getAllNation(period.getId());
            if (nations != null)
                nationList.addAll(nations);
        }

        HashSet hashSet = new HashSet(nationList);
        nationList.clear();
        nationList.addAll(hashSet);

        //查找国家下的所有节点
        String[] nationids = new String[nationList.size()];
        int index_x = 0;
        for (Nation nation : nationList){
            nationids[index_x] = nation.getId();
            index_x ++;
        }

        List<Node> nodes = nodeNationService.selectNodesByNationIds(nationids);

        int n = 0;
        //文明绑定节点
        for (Node node : nodes){
            NodeCivilization nodeCivilization = new NodeCivilization();
            nodeCivilization.setId(Utils.generateId());
            nodeCivilization.setCid(id);
            nodeCivilization.setNid(node.getId());
            Result r = (Result) nodeCivilizationService.add(nodeCivilization);
            if (Result.isOk(r)){
                n++;
            }
        }

        return new Result(ResultMessage.OK,n);
    }





    /**
     *  API
     */

    @Transactional
    public Object getApiList(JSONObject params){
        Integer start = 0;
        Integer size = 15;

        List<HashMap> civilizationMaps = civilizationMapper.getList(new Object[]{start,size});

        return new Result(ResultMessage.OK,civilizationMaps);
    }


    @Transactional
    public Object getApiTimeLine(JSONObject params){
        String id = params.getString("id");
        if (civilizationMapper.selectById(id) == null)
            return new Result(ResultMessage.PARAM_ERROR);

        //获取 timeline
        Long total = nodeCivilizationMapper.selectNodesTotalByCid(id,AppConfig.KEY_VALUE.Level_Very_Important,null);

        List<HashMap> nodesMap = nodeCivilizationMapper.selectNodesByCid(id,AppConfig.KEY_VALUE.Level_Very_Important,null,0, total.intValue());

        return new Result(ResultMessage.OK,nodesMap);
    }

}
