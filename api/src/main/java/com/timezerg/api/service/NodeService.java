package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.*;
import com.timezerg.api.util.ControllerUtil;
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
 * 时间点服务类
 *
 */
@Service
public class NodeService {

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    NodeCivilizationMapper nodeCivilizationMapper;

    @Autowired
    NodeCivilizationService nodeCivilizationService;

    @Autowired
    NationMapper nationMapper;

    @Autowired
    CivilizationMapper civilizationMapper;

    @Autowired
    CivilizationService civilizationService;

    @Autowired
    GiantMapper giantMapper;

    @Autowired
    ReferenceService referenceService;

    @Autowired
    NodeReferenceMapper nodeReferenceMapper;

    @Autowired
    NodeReferenceService nodeReferenceService;

    @Autowired
    NationService nationService;

    @Autowired
    NodeNationMapper nodeNationMapper;

    @Autowired
    NodeNationService nodeNationService;

    @Autowired
    GiantService giantService;

    @Autowired
    NodeGiantService nodeGiantService;

    @Autowired
    NodeGiantMapper nodeGiantMapper;

    @Autowired
    TagService tagService;

    @Autowired
    NodeTagMapper nodeGiant;

    @Autowired
    NodeTagService nodeTagService;

    @Autowired
    NodeTagMapper nodeTagMapper;

    @Autowired
    NodePeriodMapper nodePeriodMapper;

    @Autowired
    NodePeriodService nodePeriodService;

    @Autowired
    PeriodService periodService;

    @Autowired
    PeriodMapper periodMapper;

    @Autowired
    NationPeriodMapper nationPeriodMapper;

    @Autowired
    CivilizationPeriodMapper civilizationPeriodMapper;


    @Transactional
    public Object add(Node node){
        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR,getClass());

        if (StringUtils.isBlank(node.getTitle())){
            return new Result(ResultMessage.PARAM_ERROR,getClass());
        }

        if (nodeMapper.selectByTitle(node.getTitle()) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,getClass());
        }

        if (StringUtils.isBlank(node.getId()))
            node.setId(Utils.generateId());

        return nodeMapper.add(node);
    }

    @Transactional
    public Object update(Node node){
        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR,getClass());

        if (StringUtils.isBlank(node.getTitle())){
            return new Result(ResultMessage.PARAM_ERROR,getClass());
        }

        Node old = nodeMapper.selectById(node.getId());

        if (old == null){
            return new Result(ResultMessage.PARAM_ERROR);
        }

        if (!old.getTitle().equals(node.getTitle())){
            if (nodeMapper.selectByTitle(node.getTitle()) != null){
                return new Result(ResultMessage.DUPLICATION_ERROR,getClass());
            }
        }

        nodeMapper.update(node);

        return new Result(ResultMessage.OK,node);
    }




    @Transactional
    public Object add(JSONObject params){
        Node node = new Node();
        String nodeId = Utils.generateId();
        node.setId(nodeId);
        node.setTitle(params.getString("title"));
        node.setContent(params.getString("content"));
        node.setAD(params.getInteger("AD"));
        node.setLevel(params.getInteger("level"));
//        node.setCover();
        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Integer hour = params.getInteger("hour");
        Integer minute = params.getInteger("minute");
        Integer second = params.getInteger("second");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month == null? 0:month);
        calendar.set(Calendar.DAY_OF_MONTH,day == null? 1:day);
        calendar.set(Calendar.HOUR_OF_DAY,hour == null? 1:hour);
        calendar.set(Calendar.MINUTE,minute == null? 1:minute);
        calendar.set(Calendar.SECOND,second == null? 1:second);
        Date date = calendar.getTime();

        node.setCdate(date);

        String ddate = params.getString("ddate");
        if (StringUtils.isBlank(ddate))
            ddate = ControllerUtil.getDisplayDateStr(date,params.getInteger("AD"),null,null,false);
        node.setDdate(ddate);

        //相关国家
        nodeMapper.add(node);

        // 修改 node 的相关国家
        JSONArray nations = params.getJSONArray("nations");
        bindNation(nations,nodeId);

        // 修改 node 的相关文明
        JSONArray civilizations = params.getJSONArray("civilizations");
        bindCivilization(civilizations,nodeId);

        // 绑定人物
        JSONArray giants = params.getJSONArray("giants");
        bindGiant(giants,nodeId);

        return new Result(ResultMessage.OK,node);
    }

    /*
    *  edit init
    * */
    public Object init(JSONObject params){
        String id = params.getString("id");
        Node node = nodeMapper.selectById(id);

        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject result = (JSONObject) JSONObject.toJSON(node);
        Date date = node.getCdate();
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            result.put("year",calendar.get(Calendar.YEAR));
            result.put("month",calendar.get(Calendar.MONTH));
            result.put("day",calendar.get(Calendar.DAY_OF_MONTH));
            result.put("hour",calendar.get(Calendar.HOUR_OF_DAY));
            result.put("minute",calendar.get(Calendar.MINUTE));
            result.put("second",calendar.get(Calendar.SECOND));
        }
        result.put("AD",node.getAD());

//        //绑定的相关国家
//        List<HashMap> nationMaps = nodeNationMapper.selectByNodeId(id);
//        for (HashMap nationMap : nationMaps){
//            nationMap.put("id",nationMap.get("nationid"));
//        }
//        result.put("nations",JSON.toJSON(nationMaps));
//
//        //绑定的文明
//        List<HashMap> civilizationMaps = nodeCivilizationMapper.selectByNodeId(id);
//        for (HashMap civilizationMap : civilizationMaps){
//            civilizationMap.put("id",civilizationMap.get("cid"));
//        }
//        result.put("civilizations",JSON.toJSON(civilizationMaps));
//
//
//        //绑定的人物
//        List<HashMap> giantMaps = nodeGiantMapper.selectByNodeId(id);
//        for (HashMap giantMap : giantMaps){
//            giantMap.put("id",giantMap.get("gid"));
//        }
//        result.put("giants",JSON.toJSON(giantMaps));

        return new Result(ResultMessage.OK,result);
    }

    public Node selectById(String id){
        return nodeMapper.selectById(id);
    }


    public Object selectByTitle(JSONObject params){

        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Node> nodes = nodeMapper.selectLikeByTitle(title);

        JSONArray nodeAry = new JSONArray();
        for (Node node : nodes) {
            JSONObject nodeObj = (JSONObject) JSON.toJSON(node);
            nodeObj.put("nid", node.getId());
            nodeAry.add(nodeObj);
        }

        r.put("exist", nodes.size() > 0);
        r.put("data", nodeAry);

        return new Result(ResultMessage.OK, r);

    }


    public Object getList(JSONObject params){
        JSONArray civilizations = params.getJSONArray("cids");
        List<HashMap> list = nodeMapper.getList(civilizations.toArray(),params.getString("searchtitle"),params.getInteger("start"),params.getInteger("size"));
        JSONObject r = new JSONObject();
        for (HashMap row : list){
            String id = (String) row.get("id");

            //相关时代
            row.put("nations",nodeNationMapper.selectByNodeId(id));
        }

        r.put("data",JSON.toJSON(list));
        r.put("total",nodeMapper.getListTotal(civilizations.toArray(),params.getString("searchtitle")));
        return new Result(ResultMessage.OK,r);
    }

    @Transactional
    public Object edit(JSONObject params){
        String id = params.getString("id");
        String title = params.getString("title");
        String ddate = params.getString("ddate");
        String content = params.getString("content");
        Integer level = params.getInteger("level");
        Integer AD = params.getInteger("AD");
        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Integer hour = params.getInteger("hour");
        Integer minute = params.getInteger("minute");
        Integer second = params.getInteger("second");

        Node node = new Node();
        node.setId(id);
        node.setTitle(title);
        node.setContent(content);
        node.setDdate(ddate);
        node.setLevel(level);
        node.setAD(AD);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month == null? 0:month);
        calendar.set(Calendar.DAY_OF_MONTH,day == null? 1:day);
        calendar.set(Calendar.HOUR_OF_DAY,hour == null? 1:hour);
        calendar.set(Calendar.MINUTE,minute == null? 1:minute);
        calendar.set(Calendar.SECOND,second == null? 1:second);
        node.setCdate(calendar.getTime());

        return update(node);
    }

    @Transactional
    public Object detail(JSONObject params){
        String id = params.getString("id");
        Node node = nodeMapper.selectById(id);
        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR);
        JSONObject result = new JSONObject();
        result.put("node",node);
        result.put("civilization",nodeCivilizationMapper.selectCivilizationByNid(id));

        //此时别的文明
        result.put("closenodes",nodeMapper.selectCloseNode(node));

        return new Result(ResultMessage.OK,result);
    }


    /*
    *  绑定国家
    * */
    private void bindNation(JSONArray nationAry,String nid){
        System.out.println(JSON.toJSONString(nationAry));
        nodeNationMapper.deleteByNodeId(nid);
        for (int i = 0 ; i < nationAry.size() ; i++){
            JSONObject nationObj = nationAry.getJSONObject(i);
            Boolean isnew = nationObj.getBoolean("isnew");
            String id = nationObj.getString("id");
            if (isnew != null && isnew){
                Nation nation = new Nation();
                id = Utils.generateId();
                nation.setId(id);
                nation.setTitle(nationObj.getString("title"));
                nationMapper.add(nation);
            }

            NodeNation nodeNation = new NodeNation();
            nodeNation.setId(Utils.generateId());
            nodeNation.setNationid(id);
            nodeNation.setNodeid(nid);
            nodeNationMapper.add(nodeNation);

        }
    }

    /*
     *  绑定文明
     * */
    private void bindCivilization(JSONArray civilizationAry,String nid){
        nodeCivilizationMapper.deleteByNodeId(nid);
        for (int i = 0 ; i < civilizationAry.size() ; i++){
            JSONObject obj = civilizationAry.getJSONObject(i);
            Boolean isnew = obj.getBoolean("isnew");
            String id = obj.getString("id");
            if (isnew != null && isnew){
                Civilization civilization = new Civilization();
                id = Utils.generateId();
                civilization.setId(id);
                civilization.setTitle(obj.getString("title"));
                civilizationMapper.add(civilization);
            }

            NodeCivilization nodeCivilization = new NodeCivilization();
            nodeCivilization.setId(Utils.generateId());
            nodeCivilization.setNid(nid);
            nodeCivilization.setCid(id);
            nodeCivilizationMapper.add(nodeCivilization);

        }
    }

    /*
     *  绑定人物
     * */
    private void bindGiant(JSONArray giantAry,String nid){
        nodeGiantMapper.deleteByNodeId(nid);
        for (int i = 0 ; i < giantAry.size() ; i++){
            JSONObject obj = giantAry.getJSONObject(i);
            Boolean isnew = obj.getBoolean("isnew");
            String id = obj.getString("id");
            if (isnew != null && isnew){
                Giant giant = new Giant();
                id = Utils.generateId();
                giant.setId(id);
                giant.setName(obj.getString("name"));
                giantMapper.add(giant);
            }

            NodeGiant nodeGiant = new NodeGiant();
            nodeGiant.setId(Utils.generateId());
            nodeGiant.setNid(nid);
            nodeGiant.setGid(id);
            nodeGiantMapper.add(nodeGiant);

        }
    }

    /*edit init*/
    public Object initRelate(JSONObject params){
        String id = params.getString("id");
        Node node = nodeMapper.selectById(id);

        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject result = (JSONObject) JSONObject.toJSON(node);

        result.put("nations",nodeNationMapper.selectByNodeId(id));

        // 时代、文明
        result.put("periods",nodePeriodMapper.selectByNodeId(id));
        result.put("civilizations",nodeCivilizationMapper.selectByNodeId(id));

        result.put("giants",nodeGiantMapper.selectByNodeId(id));
        result.put("references",nodeReferenceMapper.selectByNid(id));
        return new Result(ResultMessage.OK,result);
    }

    @Transactional
    public Object addRelateReference(JSONObject params){
        JSONObject referenceObj = params.getJSONObject("reference");
        String nid = params.getString("nid");
        String rid = referenceObj.getString("rid");

        Boolean isNew = referenceObj.getBoolean("isnew");
        if (isNew != null && isNew){
            String title = referenceObj.getString("title");
            Reference r = new Reference();
            rid = Utils.generateId();
            r.setId(rid);
            r.setTitle(title);
            referenceService.add(r);
        }

        NodeReference nodeReference = new NodeReference();
        nodeReference.setId(Utils.generateId());
        nodeReference.setNid(nid);
        nodeReference.setRid(rid);

        return nodeReferenceService.add(nodeReference);
    }

    @Transactional
    public Object deleteRelateReference(JSONObject params){
        return nodeReferenceService.delete(params.getString("id"));
    }

    @Transactional
    public Object addRelateNation(JSONObject params){
        JSONObject nationObj = params.getJSONObject("nation");
        String nid = params.getString("nid");
        String nationdId = nationObj.getString("nid");

        Boolean isNew = nationObj.getBoolean("isnew");
        if (isNew != null && isNew){
            String title = nationObj.getString("title");
            Nation n = new Nation();
            nationdId = Utils.generateId();
            n.setId(nationdId);
            n.setTitle(title);
            nationService.add(n);
        }

        NodeNation nodeNation = new NodeNation();
        nodeNation.setId(Utils.generateId());
        nodeNation.setNationid(nationdId);
        nodeNation.setNodeid(nid);

        return nodeNationService.add(nodeNation);
    }

    @Transactional
    public Object deleteRelateNation(JSONObject params){
        return nodeNationService.delete(params.getString("id"));
    }


    @Transactional
    public Object addRelateCivilization(JSONObject params){
        JSONObject civilizationObj = params.getJSONObject("civilization");
        String nid = params.getString("nid");
        String civilizationId = civilizationObj.getString("id");

        Boolean isNew = civilizationObj.getBoolean("isnew");
        if (isNew != null && isNew){
            String title = civilizationObj.getString("title");
            Civilization c = new Civilization();
            civilizationId = Utils.generateId();
            c.setId(civilizationId);
            c.setTitle(title);
            civilizationService.add(c);
        }

        NodeCivilization nodeCivilization = new NodeCivilization();
        nodeCivilization.setId(Utils.generateId());
        nodeCivilization.setCid(civilizationId);
        nodeCivilization.setNid(nid);

        return nodeCivilizationService.add(nodeCivilization);
    }

    @Transactional
    public Object deleteRelateCivilization(JSONObject params){
        return nodeCivilizationService.delete(params.getString("id"));
    }

    @Transactional
    public Object addRelatePeriod(JSONObject params){
        JSONObject periodObj = params.getJSONObject("period");
        String nid = params.getString("nid");
        String periodId = periodObj.getString("pid");

        Boolean isNew = periodObj.getBoolean("isnew");
        if (isNew != null && isNew){
            String title = periodObj.getString("title");
            Period c = new Period();
            periodId = Utils.generateId();
            c.setId(periodId);
            c.setTitle(title);
            periodService.add(c);
        }

        NodePeriod nodePeriod = new NodePeriod();
        nodePeriod.setId(Utils.generateId());
        nodePeriod.setPid(periodId);
        nodePeriod.setNid(nid);

        return nodePeriodService.add(nodePeriod);
    }

    @Transactional
    public Object deleteRelatePeriod(JSONObject params){
        return nodePeriodService.delete(params.getString("id"));
    }


    @Transactional
    public Object addRelateGiant(JSONObject params){
        JSONObject giantObj = params.getJSONObject("giant");
        String nid = params.getString("nid");
        String giantdId = giantObj.getString("gid");

        Boolean isNew = giantObj.getBoolean("isnew");
        if (isNew != null && isNew){
            String name = giantObj.getString("name");
            Giant g = new Giant();
            giantdId = Utils.generateId();
            g.setId(giantdId);
            g.setName(name);
            giantService.add(g);
        }

        NodeGiant nodeGiant = new NodeGiant();
        nodeGiant.setId(Utils.generateId());
        nodeGiant.setGid(giantdId);
        nodeGiant.setNid(nid);

        return nodeGiantService.add(nodeGiant);
    }

    @Transactional
    public Object deleteRelateGiant(JSONObject params){
        return nodeGiantService.delete(params.getString("id"));
    }

    /*edit init*/
    public Object initTag(JSONObject params){
        String id = params.getString("id");
        Node node = nodeMapper.selectById(id);

        if (node == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject result = (JSONObject) JSONObject.toJSON(node);

        result.put("tags",nodeTagMapper.selectByNid(id));
        return new Result(ResultMessage.OK,result);
    }

    @Transactional
    public Object addTag(JSONObject params){
        JSONObject tagObj = params.getJSONObject("tag");
        String nid = params.getString("nid");
        String tagId = tagObj.getString("tid");

        Boolean isNew = tagObj.getBoolean("isnew");
        if (isNew != null && isNew){
            String title = tagObj.getString("title");
            Tag t = new Tag();
            tagId = Utils.generateId();
            t.setId(tagId);
            t.setTitle(title);
            tagService.add(t);
        }

        NodeTag nodeTag = new NodeTag();
        nodeTag.setId(Utils.generateId());
        nodeTag.setTid(tagId);
        nodeTag.setNid(nid);
        return nodeTagService.add(nodeTag);
    }

    @Transactional
    public Object deleteTag(JSONObject params){
        return nodeTagService.delete(params.getString("id"));
    }

    /**
     *  捕捉最接近的节点
     */
    @Transactional
    public Object getClosetNodes(Date date){
        List<HashMap> nodesMap = nodeMapper.selectCloseNodeByDate(date);
        return new Result(ResultMessage.OK,nodesMap);
    }

    /**
     *  同步
     */
    @Transactional
    public Object sync(String id){
        if (nodeMapper.selectById(id) == null)
            return new Result(ResultMessage.PARAM_ERROR);

        //获取国家
        List<Nation> nations = nodeNationMapper.selectNationsByNodeId(id);
        List<Nation> allNations = new ArrayList<>();
        allNations.addAll(nations);

        for (Nation nation : nations){
            allNations.addAll(nationService.getAllNations(nation.getId()));
        }

        HashSet nationSet = new HashSet(allNations);
        allNations.clear();
        allNations.addAll(nationSet);

        //绑定国家
        List<Period> periods = new ArrayList<>();
        for (Nation nation : allNations){
            NodeNation nodeNation = new NodeNation();
            nodeNation.setId(id);
            nodeNation.setNationid(nation.getId());
            nodeNationService.add(nodeNation);
            periods.addAll(nationPeriodMapper.selectPeriodByNid(nation.getId()));
        }

        //绑定时期
        List<Civilization> civilizations = new ArrayList<>();
        for (Period period : periods){
            NodePeriod nodePeriod = new NodePeriod();
            nodePeriod.setNid(id);
            nodePeriod.setPid(period.getId());
            nodePeriodService.add(nodePeriod);
            civilizations.addAll(civilizationPeriodMapper.selectCivilsByPid(period.getId()));
        }

        //绑定文明
        for (Civilization civilization : civilizations){
            NodeCivilization nodeCivilization = new NodeCivilization();
            nodeCivilization.setNid(id);
            nodeCivilization.setCid(civilization.getId());
            nodeCivilizationService.add(nodeCivilization);
        }

        return new Result(ResultMessage.OK);
    }



}
