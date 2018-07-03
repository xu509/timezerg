package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.config.AppConfig;
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
 * 国家服务类
 */
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;

    @Autowired
    GiantMapper giantMapper;

    @Autowired
    GiantService giantService;

    @Autowired
    GiantNationMapper giantNationMapper;

    @Autowired
    GiantNationService giantNationService;

    @Autowired
    NationInstitutionMapper nationInstitutionMapper;

    @Autowired
    NationInstitutionService nationInstitutionService;

    @Autowired
    InstitutionMapper institutionMapper;

    @Autowired
    InstitutionService institutionService;

    @Autowired
    NationTagMapper nationTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ReferenceService referenceService;

    @Autowired
    NationReferenceService nationReferenceService;

    @Autowired
    NationReferenceMapper nationReferenceMapper;

    @Autowired
    PeriodService periodService;

    @Autowired
    NationPeriodService nationPeriodService;

    @Autowired
    NationPeriodMapper nationPeriodMapper;

    @Autowired
    NodeNationMapper nodeNationMapper;

    @Autowired
    NodeNationService nodeNationService;

    @Autowired
    NodePeriodMapper nodePeriodMapper;

    @Transactional
    public Object add(JSONObject params) {
        Nation nation = new Nation();
        nation.setId(Utils.generateId());
        nation.setTitle(params.getString("title"));
        nation.setContent(params.getString("content"));
        nation.setAD(params.getInteger("AD"));
        nation.seteAD(params.getInteger("eAD"));
        nation.setDdate(params.getString("ddate"));
        nation.setPid(params.getString("pid") == null ? "" : params.getString("pid"));
//        node.setCover();
        Integer year = params.getInteger("year");
        Integer month = params.getInteger("month");
        Integer day = params.getInteger("day");
        Integer eyear = params.getInteger("eyear");
        Integer emonth = params.getInteger("emonth");
        Integer eday = params.getInteger("eday");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month == null ? 0 : month);
        calendar.set(Calendar.DAY_OF_MONTH, day == null ? 1 : day);
        nation.setCdate(calendar.getTime());

        if (eyear != null){
            calendar.clear();
            calendar.set(Calendar.YEAR, eyear);
            calendar.set(Calendar.MONTH, emonth == null ? 0 : emonth);
            calendar.set(Calendar.DAY_OF_MONTH, eday == null ? 1 : eday);
            nation.setEdate(calendar.getTime());
        }

        //pid
        String pid = params.getString("pid");
        Nation pnation = nationMapper.selectById(pid);
        if (pnation == null)
            nation.setPid(null);
        else
            nation.setPid(pid);
        nationMapper.add(nation);

        return new Result(ResultMessage.OK, nation);
    }

    @Transactional
    public Object add(Nation nation) {
        if (nation == null || StringUtils.isBlank(nation.getTitle()))
            return new Result(ResultMessage.PARAM_ERROR,"国家添加错误");

        if (nationMapper.selectByTitle(nation.getTitle()) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"国家标题重复");
        }

        nationMapper.add(nation);

        return new Result(ResultMessage.OK, nation);
    }


    public Object getList(JSONObject params) {
        Integer type = params.getInteger("type");
        JSONObject r = new JSONObject();

        String title = params.getString("searchtitle");
        Integer start = params.getInteger("start");
        Integer size = params.getInteger("size");


        List<HashMap> list = new ArrayList<>();

        if (type == 1){
            //默认
           list = nationMapper.getList(title,start,size);
            r.put("total", nationMapper.getListTotal(title));
        }else if (type == 2){
            //顶级
            Object[] p = {start, size};
            list = nationMapper.getTopList(p);
            r.put("total", nationMapper.getTopListTotal(p));
        }else if (type == 3){
            //未完善
            Object[] p = {start, size};
            list = nationMapper.getUncheckList(p);
            r.put("total", nationMapper.getUncheckListTotal(p));
        }

        for (HashMap nationMap : list){
            String id = (String) nationMap.get("id");

            //填写时代
            nationMap.put("periods",nationPeriodMapper.selectByNid(id));
        }

        r.put("data", list);

        return new Result(ResultMessage.OK, r);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Nation> nations = nationMapper.selectLikeByTitle(title);

        JSONArray nationAry = new JSONArray();
        for (Nation nation : nations){
            JSONObject nationObj = (JSONObject) JSON.toJSON(nation);
            nationObj.put("nid",nation.getId());
            nationAry.add(nationObj);
        }

        r.put("exist", nations.size() > 0);
        r.put("data", nationAry);

        return new Result(ResultMessage.OK, r);
    }

    public Object editInit(JSONObject params) {
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);
        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject r = (JSONObject) JSONObject.toJSON(nation);

        Date cdate = nation.getCdate();
        int year =  1,month = 0,day = 0;
        if (cdate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cdate);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        r.put("year", year);
        r.put("month", month);
        r.put("day", day);

        Date edate = nation.getEdate();
        int eyear,emonth,eday;
        if (edate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(edate);
            eyear = calendar.get(Calendar.YEAR);
            emonth = calendar.get(Calendar.MONTH);
            eday = calendar.get(Calendar.DAY_OF_MONTH);
            r.put("eyear", eyear);
            r.put("emonth", emonth);
            r.put("eday", eday);
        }

        //nation
        Nation pnation = nationMapper.selectById(nation.getPid());
        if (pnation != null)
            r.put("pnation", pnation.getTitle());


        Nation fnation = nationMapper.selectById(nation.getFid());
        if (fnation != null)
            r.put("fnation", fnation.getTitle());

//        //giant
//        List<HashMap> giants = giantNationMapper.selectByNid(id);
//        for (HashMap giant : giants){
//            giant.put("isnew",false);
//        }
//        r.put("giant",JSONObject.toJSON(giants));
//
//        //institution
//        List<HashMap> institution = nationInstitutionMapper.selectByNid(id);
////        for (HashMap giant : institution){
////            giant.put("isnew",false);
////        }
//        r.put("institution",JSONObject.toJSON(institution));

        return new Result(ResultMessage.OK, r);
    }

    public Object editInitRelate(JSONObject params) {
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);
        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject r = (JSONObject) JSON.toJSON(nation);

        //名人
        List<HashMap> giants = giantNationMapper.selectByNid(id);
        r.put("giants",giants);

        //制度
        List<HashMap> institution = nationInstitutionMapper.selectByNid(id);
        r.put("institutions",institution);

        //依据
        r.put("references",nationReferenceMapper.selectByNid(id));

        //时期
        r.put("periods",nationPeriodMapper.selectByNid(id));

        return new Result(ResultMessage.OK,r);
    }



    public Object editInitTag(JSONObject params){
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);
        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR);
        List<HashMap> tagMaps = nationTagMapper.selectTagsByNid(id);

//        System.out.println(JSON.toJSONString(tagMaps));
        JSONObject data = new JSONObject();
        data.put("nation",nation);
        data.put("tags",tagMaps);
        return new Result(ResultMessage.OK,data);
    }

    @Transactional
    public Object addTag(JSONObject params){
        JSONObject tag = params.getJSONObject("tag");
        String nid = params.getString("nid");
        String tid = tag.getString("tid");

//        System.out.println(params);

        Boolean isNew = tag.getBoolean("isnew");
        if (isNew != null && isNew){
            String title = tag.getString("title");
            Tag t = new Tag();
            tid = Utils.generateId();
            t.setId(tid);
            t.setTitle(title);
            tagMapper.add(t);
        }

        NationTag nationTag = new NationTag();
        nationTag.setId(Utils.generateId());
        nationTag.setNid(nid);
        nationTag.setTid(tid);

       if (nationTagMapper.selectByNidAndTid(nationTag) != null){
           return new Result(ResultMessage.PARAM_ERROR);
       }
        nationTagMapper.add(nationTag);

        return new Result(ResultMessage.OK);
    }

    @Transactional
    public Object deleteTag(JSONObject params){
        String id = (String) params.get("id");
        nationTagMapper.deleteById(id);
        return new Result(ResultMessage.OK);
    }


    @Transactional
    public Object edit(JSONObject params) {
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);
        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        nation.setTitle(params.getString("title"));
        nation.setPid(params.getString("pid"));

        Date cdate = ControllerUtil.getDate(params.getInteger("year"),params.getInteger("month"),params.getInteger("day"));
        nation.setCdate(cdate);

        Date edate = ControllerUtil.getDate(params.getInteger("eyear"),params.getInteger("emonth"),params.getInteger("eday"));
        nation.setEdate(edate);

        Integer AD = params.getInteger("AD");
        Integer eAD = params.getInteger("eAD");

        nation.setAD(AD);
        nation.seteAD(eAD);

        nation.setPid(params.getString("pid"));
        nation.setFid(params.getString("fid"));


        String ddate = params.getString("ddate");
        if (StringUtils.isBlank(ddate))
            ddate = ControllerUtil.getDisplayDateStr(cdate,AD,edate,eAD,false);
        nation.setDdate(ddate);

        nation.setInvent(params.getString("invent"));

        nationMapper.update(nation);


//        //重要人物
//        JSONArray giants = params.getJSONArray("giant");
//        if (giants != null){
//            giantNationMapper.deleteByNid(id);
//
//            for (int i = 0 ; i < giants.size(); i++){
//                JSONObject giant = giants.getJSONObject(i);
//                String name = giant.getString("name");
//                String giantId;
//
//                Boolean isNewFlag = giant.getBoolean("isnew");
//
//                if (isNewFlag!= null && isNewFlag){
//                    //增加并绑定
//                    Giant giant1 = new Giant();
//                    giantId = Utils.generateId();
//
//                    giant1.setId(giantId);
//                    giant1.setName(name);
//                    giantMapper.add(giant1);
//
//                }else {
//                    giantId = giant.getString("gid");
//                    //绑定
//                }
//
//                GiantNation giantNation = new GiantNation();
//                giantNation.setId(Utils.generateId());
//                giantNation.setGid(giantId);
//                giantNation.setNid(id);
//
//                if (giantNationMapper.selectByGidAndNid(giantNation) == null){
//                    giantNationMapper.add(giantNation);
//                }
//
//            }
//        }

//        //制度
//        JSONArray institution = params.getJSONArray("institution");
//        bindInstitution(institution,id);

        return new Result(ResultMessage.OK, nation);
    }


    private void bindInstitution(JSONArray institutionAry,String nid){
        if (institutionAry != null){
            nationInstitutionMapper.deleteByNid(nid);

            for (int i = 0 ; i < institutionAry.size(); i++){
                JSONObject object = institutionAry.getJSONObject(i);
                String iid = object.getString("iid");

                boolean isnew = object.getBoolean("isnew");
                if (isnew){
                    String title = object.getString("title");
                    if (institutionMapper.selectByTitle(title) == null){
                        Institution institution = new Institution();
                        iid = Utils.generateId();
                        institution.setId(iid);
                        institution.setTitle(title);
                        institutionMapper.add(institution);
                    }else {
                        return;
                    }
                }

                NationInstitution nationInstitution = new NationInstitution();
                nationInstitution.setId(Utils.generateId());
                nationInstitution.setNid(nid);
                nationInstitution.setIid(iid);


                if (nationInstitutionMapper.selectByIidAndNid(nationInstitution) == null){
                    nationInstitutionMapper.add(nationInstitution);
                }

            }
        }
    }


    @Transactional
    public Object addRelateGiant(JSONObject params){
        JSONObject giant = params.getJSONObject("giant");
        String nid = params.getString("nid");
        String gid = giant.getString("gid");

        Boolean isNew = giant.getBoolean("isnew");
        if (isNew != null && isNew){
            String name = giant.getString("name");
            Giant g = new Giant();
            gid = Utils.generateId();
            g.setId(gid);
            g.setName(name);
            giantService.add(g);
        }

        GiantNation giantNation = new GiantNation();
        giantNation.setId(Utils.generateId());
        giantNation.setNid(nid);
        giantNation.setGid(gid);

        return giantNationService.add(giantNation);
    }

    @Transactional
    public Object deleteRelateGiant(JSONObject params){
        return giantNationService.delete(params.getString("id"));
    }

    @Transactional
    public Object addRelateInstitution(JSONObject params){
        JSONObject institution = params.getJSONObject("institution");
        String nid = params.getString("nid");
        String iid = institution.getString("iid");

        Boolean isNew = institution.getBoolean("isnew");
        if (isNew != null && isNew){
            String name = institution.getString("title");
            Institution i = new Institution();
            iid = Utils.generateId();
            i.setId(iid);
            i.setTitle(name);
            institutionService.add(i);
        }

        NationInstitution nationInstitution = new NationInstitution();
        nationInstitution.setId(Utils.generateId());
        nationInstitution.setNid(nid);
        nationInstitution.setIid(iid);

        return nationInstitutionService.add(nationInstitution);
    }

    @Transactional
    public Object deleteRelateInstitution(JSONObject params){
        return nationInstitutionService.delete(params.getString("id"));
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

        NationReference nationReference = new NationReference();
        nationReference.setId(Utils.generateId());
        nationReference.setNid(nid);
        nationReference.setRid(rid);

        return nationReferenceService.add(nationReference);
    }

    @Transactional
    public Object deleteRelateReference(JSONObject params){
        return nationReferenceService.delete(params.getString("id"));
    }

    @Transactional
    public Object addRelatePeriod(JSONObject params){
        JSONObject periodObj = params.getJSONObject("period");
        String nid = params.getString("nid");
        String pid = periodObj.getString("pid");

        Boolean isNew = periodObj.getBoolean("isnew");
        if (isNew != null && isNew){
            String title = periodObj.getString("title");
            Period r = new Period();
            pid = Utils.generateId();
            r.setId(pid);
            r.setTitle(title);
            periodService.add(r);
        }

        NationPeriod nationPeriod = new NationPeriod();
        nationPeriod.setId(Utils.generateId());
        nationPeriod.setNid(nid);
        nationPeriod.setPid(pid);

        return nationPeriodService.add(nationPeriod);
    }

    @Transactional
    public Object deleteRelatePeriod(JSONObject params){
        return nationPeriodService.delete(params.getString("id"));
    }

    public Object editInitNodes(JSONObject params){
        JSONObject r = new JSONObject();
        String id = params.getString("id");
        Integer level = params.getInteger("level");
        Nation nation = nationMapper.selectById(id);

        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR);

        r.put("nation",nation);

        //nodes
        Integer start = params.getInteger("start");
        Integer size = params.getInteger("size");
        String title = params.getString("title");

        List<HashMap> nodeMaps = nodeNationMapper.selectNodesByNid(id,level,title,start,size);
        for (HashMap nodeMap : nodeMaps){
            Integer levelInt  = (Integer) nodeMap.get("l");
            nodeMap.put("levelstr",Utils.getLevelStr(levelInt));
        }

        r.put("nodes",nodeMaps);
        r.put("nodestotal",nodeNationMapper.selectNodesTotalByNid(id,level,title));

        return new Result(ResultMessage.OK,r);
    }

    /**
     *  同步节点
     */
    @Transactional
    public Object syncNode(JSONObject params){
        String id = params.getString("id");
        Nation nation = nationMapper.selectById(id);
        if (nation == null)
            return new Result(ResultMessage.PARAM_ERROR,"ID 错误");

        List<Nation> nations = getAllNations(id);

        String[] nationids = new String[nations.size()];
        for (int x = 0 ; x < nations.size() ; x++){
            nationids[x] = nations.get(x).getId();
        }


        List<Node> nodes = nodeNationService.selectNodesByNationIds(nationids);

        int n = 0;

        //时代绑定节点
        for (Node node : nodes){

            NodeNation nodeNation = new NodeNation();
            nodeNation.setId(Utils.generateId());
            nodeNation.setNationid(id);
            nodeNation.setNodeid(node.getId());


            NodePeriod nodePeriod = new NodePeriod();
            nodePeriod.setNid(node.getId());
            nodePeriod.setLevel(AppConfig.KEY_VALUE.Level_Very_Important);

            if (nodePeriodMapper.selectCountByNidAndLevel(nodePeriod) > 0){
                nodeNation.setLevel(AppConfig.KEY_VALUE.Level_Very_Important);
            }

            Result r = (Result) nodeNationService.checkAndAdd(nodeNation);
            if (Result.isOk(r)){
                n++;
            }
        }

        return new Result(ResultMessage.OK,n);
    }


    public Object updateNodeLevel(JSONObject params){
        Integer level = params.getInteger("level");
        String id = params.getString("id");
        return nodeNationService.updateLevel(id,level);
    }

    @Transactional
    public Object deleteNode(JSONObject params){
        String id = params.getString("id");
        return nodeNationService.delete(id);
    }


    /**
     *  获取某国家下的所有子国家
     */
    public List<Nation> getAllNations(String nid){
        Nation nation = nationMapper.selectById(nid);
        if (nation == null)
            return null;

        List<Nation> nations = new ArrayList<>();
        nations.add(nation);

        Object[] nids = new Object[]{nation.getId()};
        List<Nation> childNations =  nationMapper.selectListByFids(nids);
        while (childNations.size() > 0){
            nations.addAll(childNations);

            Object[] pids = new Object[childNations.size()];
            for (int x = 0 ; x < childNations.size() ; x++){
                pids[x] = childNations.get(x).getId();
            }

            childNations = nationMapper.selectListByFids(pids);
        }

        HashSet nationsSet = new HashSet(nations);
        nations.clear();
        nations.addAll(nationsSet);
        return nations;
    }

}
