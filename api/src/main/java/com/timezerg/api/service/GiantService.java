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

import java.util.Calendar;
import java.util.Date;
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
    GiantTagService giantTagService;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    GiantNationMapper giantNationMapper;

    @Autowired
    NationMapper nationMapper;

    @Autowired
    NationService nationService;

    @Autowired
    GiantNationService giantNationService;

    @Autowired
    TagService tagService;

    @Autowired
    GiantReferenceService giantReferenceService;

    @Autowired
    GiantReferenceMapper giantReferenceMapper;

    @Autowired
    NodeGiantMapper nodeGiantMapper;

    @Autowired
    NodeGiantService nodeGiantService;

    @Autowired
    ReferenceService referenceService;

    @Autowired
    NodeService nodeService;

    @Autowired
    RelationMapper relationMapper;

    @Autowired
    RelationService relationService;

    @Autowired
    GiantRelationMapper giantRelationMapper;

    @Autowired
    GiantRelationService giantRelationService;



    @Transactional
    public Object add(JSONObject params) {
        Giant giant = new Giant();
        String gid = Utils.generateId();
        giant.setId(gid);

        String name = params.getString("name");
        if (giantMapper.selectByName(name) != null){
            return new Result(ResultMessage.PARAM_ERROR,"已经存在");
        }

        giant.setName(name);
        giant.setContent(params.getString("content"));
        giantMapper.add(giant);

        //tag
        bindTag(params.getJSONArray("tags"),gid);
        //nations
        bindNations(params.getJSONArray("nations"),gid);

        return new Result(ResultMessage.OK, giant);
    }


    @Transactional
    public Object add(Giant giant) {
        if (giant == null || StringUtils.isBlank(giant.getName())){
            return new Result(ResultMessage.PARAM_ERROR,"giant add");
        }

        if (StringUtils.isBlank(giant.getId()))
            giant.setId(Utils.generateId());

        String name = giant.getName();
        if (giantMapper.selectByName(name) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,"giant add");
        }
        giantMapper.add(giant);

        return new Result(ResultMessage.OK, giant);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Giant> giants = giantMapper.selectLikeByTitle(title);

        boolean same = false;

        JSONArray jsonArray = new JSONArray();
        for (Giant giant : giants){
            JSONObject obj = (JSONObject) JSON.toJSON(giant);

            String gid = giant.getId();
            String sname = giant.getName();
            String id = giant.getId();

            if (giant.getName().equals(title))
                same = true;

            if (!StringUtils.isBlank(giant.getPid())){
                String pname = giantMapper.selectById(giant.getPid()).getName();
                obj.put("pname",pname);
                sname = pname + " - " + giant.getName();
                gid = giant.getPid();
                id = gid;
            }
            obj.put("sname",sname);
            obj.put("gid",gid);
            obj.put("id",id);
            jsonArray.add(obj);
        }


        r.put("exist", giants.size() > 0);
        r.put("same",same);
        r.put("data", jsonArray);

//        System.out.println(r.toJSONString());

        return new Result(ResultMessage.OK, r);
    }


    public Object getList(JSONObject params) {
//        Object[] p = {params.getString("name"),params.getInteger("start"), params.getInteger("size")};

        List<HashMap> list;
        Long total;

        JSONArray periods = params.getJSONArray("periods");
        JSONArray nations = params.getJSONArray("nations");
        if (periods != null && periods.size() > 0){

            Object[] pidsAry = new Object[periods.size()];
            for (int i = 0;i<periods.size();i++){
                JSONObject o = periods.getJSONObject(i);
                pidsAry[i] = o.getString("pid");
            }

            list = giantMapper.getListByPeriod(pidsAry,params.getInteger("start"),params.getInteger("size"));
            total = giantMapper.getListTotalByPeriod(pidsAry);
        }else if (nations != null && nations.size() > 0){

            Object[] nidsAry = new Object[nations.size()];
            for (int i = 0;i<nations.size();i++){
                JSONObject o = nations.getJSONObject(i);
                nidsAry[i] = o.getString("nid");
            }

            list = giantMapper.getListByNation(nidsAry,params.getInteger("start"),params.getInteger("size"));
            total = giantMapper.getListTotalByNation(nidsAry);

        }else {

            list = giantMapper.getList(params.getString("name"),params.getInteger("start"),params.getInteger("size"));
            total = giantMapper.getListTotal(params.getString("name"));
        }

        for (HashMap row : list){
            String gid = (String) row.get("id");
            row.put("nations",giantNationMapper.selectByGid(gid));

            row.put("tags",giantTagMapper.selectTagsByGid(gid));

            long cgnum = giantMapper.selectCountByPid(gid);
            if (cgnum > 0){
                row.put("cgiants",giantMapper.selectByPid(gid));
            }
        }


        JSONObject r = new JSONObject();
        r.put("data", list);
        r.put("total", total);
        return new Result(ResultMessage.OK, r);
    }

    @Transactional
    public Object editInit(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject obj = (JSONObject) JSON.toJSON(giant);
        Giant pgiant = giantMapper.selectById(giant.getPid());

        if (pgiant != null){
            obj.put("pgiant",pgiant);
        }

        //年月日
        Date cdate = giant.getCdate();
        int year =  1,month = 0,day = 0;
        if (cdate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cdate);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        obj.put("year", year);
        obj.put("month", month);
        obj.put("day", day);


        Date edate = giant.getEdate();
        int eyear,emonth,eday;
        if (edate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(edate);
            eyear = calendar.get(Calendar.YEAR);
            emonth = calendar.get(Calendar.MONTH);
            eday = calendar.get(Calendar.DAY_OF_MONTH);
            obj.put("eyear", eyear);
            obj.put("emonth", emonth);
            obj.put("eday", eday);
        }

        return new Result(ResultMessage.OK,obj);
    }

    @Transactional
    public Object editInitRelate(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject obj = (JSONObject) JSON.toJSON(giant);
        obj.put("nations",giantNationMapper.selectByGid(id));
        obj.put("references",giantReferenceMapper.selectByGid(id));
        obj.put("nodes",nodeGiantMapper.selectByGid(id));

        return new Result(ResultMessage.OK,obj);
    }

    @Transactional
    public Object editSave(JSONObject params){
        System.out.println(params.toJSONString());
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        giant.setContent(params.getString("content"));
        String name = params.getString("name");

        if (!(name.equals(giant.getName())) && (giantMapper.selectByName(name) != null)){
            return new Result(ResultMessage.PARAM_ERROR);
        }

        giant.setName(params.getString("name"));

        JSONObject pgiantObj = params.getJSONObject("pgiant");
        if (pgiantObj != null){
            Boolean isnew = pgiantObj.getBoolean("isnew");
            String pid = pgiantObj.getString("gid");
            if (isnew != null && isnew){
                Giant pGiant = new Giant();
                pid = Utils.generateId();
                pGiant.setId(pid);
                pGiant.setName(pgiantObj.getString("name"));
                giantMapper.add(pGiant);
            }
            if (!StringUtils.isBlank(pid)){
                while (!StringUtils.isBlank(giantMapper.selectById(pid).getPid())){
                    pid = giantMapper.selectById(pid).getPid();
                }
                giant.setPid(pid);
            }
        }

        giant.setCdate(ControllerUtil.getDate(params.getInteger("year"),params.getInteger("month"),params.getInteger("day")));
        giant.setEdate(ControllerUtil.getDate(params.getInteger("eyear"),params.getInteger("emonth"),params.getInteger("eday")));
        giant.setAD(params.getInteger("AD"));
        giant.seteAD(params.getInteger("eAD"));

        giantMapper.update(giant);

        return new Result(ResultMessage.OK,giant);
    }

    /*
    *   增加nation
    * */
    @Transactional
    public Object editAddNation(JSONObject params){
        String gid = params.getString("gid");
        JSONObject nationObj = params.getJSONObject("nation");
        Giant giant = giantMapper.selectById(gid);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        String nid = nationObj.getString("nid");
        Boolean isnew = nationObj.getBoolean("isnew");
        if (isnew != null && isnew){
            Nation nation = new Nation();
            nid = Utils.generateId();
            nation.setId(nid);
            nation.setTitle(nationObj.getString("title"));
            nationService.add(nation);
        }

        GiantNation giantNation = new GiantNation();
        giantNation.setId(Utils.generateId());
        giantNation.setNid(nid);
        giantNation.setGid(gid);
        giantNationService.add(giantNation);

        return new Result(ResultMessage.OK,giant);
    }

    /*
     *   删除 reference
     * */
    @Transactional
    public Object editDeleteNation(JSONObject params){
        String id = params.getString("id");
        return giantNationService.delete(id);
    }

    /*
     *   增加 reference
     * */
    @Transactional
    public Object editAddReference(JSONObject params){
        String gid = params.getString("gid");
        JSONObject referenceObj = params.getJSONObject("reference");
        Giant giant = giantMapper.selectById(gid);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        String rid = referenceObj.getString("rid");
        Boolean isnew = referenceObj.getBoolean("isnew");
        if (isnew != null && isnew){
            Reference reference = new Reference();
            rid = Utils.generateId();
            reference.setId(rid);
            reference.setTitle(referenceObj.getString("title"));
            referenceService.add(reference);
        }

        GiantReference giantReference = new GiantReference();
        giantReference.setId(Utils.generateId());
        giantReference.setRid(rid);
        giantReference.setGid(gid);
        giantReferenceService.add(giantReference);

        return new Result(ResultMessage.OK,giantReference);
    }

    /*
     *   删除 reference
     * */
    @Transactional
    public Object editDeleteReference(JSONObject params){
        String id = params.getString("id");
        return giantReferenceService.delete(id);
    }


    /*
     *   增加 node
     * */
    @Transactional
    public Object editAddNode(JSONObject params){
        String gid = params.getString("gid");
        JSONObject nodeObj = params.getJSONObject("node");
        Giant giant = giantMapper.selectById(gid);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        String nid = nodeObj.getString("nid");
        Boolean isnew = nodeObj.getBoolean("isnew");
        if (isnew != null && isnew){
            Node node = new Node();
            nid = Utils.generateId();
            node.setId(nid);
            node.setTitle(nodeObj.getString("title"));
            nodeService.add(node);
        }

        NodeGiant nodeGiant = new NodeGiant();
        nodeGiant.setId(Utils.generateId());
        nodeGiant.setNid(nid);
        nodeGiant.setGid(gid);
        nodeGiantService.add(nodeGiant);

        return new Result(ResultMessage.OK,nodeGiant);
    }

    /*
     *   删除 node
     * */
    @Transactional
    public Object editDeleteNode(JSONObject params){
        String id = params.getString("id");
        return nodeGiantService.delete(id);
    }



    /**
     *  初始化贴条
     */
    @Transactional
    public Object editInitTag(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject obj = (JSONObject) JSON.toJSON(giant);
        obj.put("tags",giantTagMapper.selectTagsByGid(id));
        return new Result(ResultMessage.OK,obj);
    }

    /**
     *   增加 tag
     */
    @Transactional
    public Object editAddTag(JSONObject params){
        String gid = params.getString("gid");
        JSONObject tagObj = params.getJSONObject("tag");
        Giant giant = giantMapper.selectById(gid);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        String tid = tagObj.getString("tid");
        Boolean isnew = tagObj.getBoolean("isnew");
        if (isnew != null && isnew){
            Tag tag = new Tag();
            tid = Utils.generateId();
            tag.setId(tid);
            tag.setTitle(tagObj.getString("title"));
            tagService.add(tag);
        }

        GiantTag giantTag = new GiantTag();
        giantTag.setId(Utils.generateId());
        giantTag.setTid(tid);
        giantTag.setGid(gid);
        giantTagService.add(giantTag);

        return new Result(ResultMessage.OK,giant);
    }

    /*
     *   删除tag
     * */
    @Transactional
    public Object editDeleteTag(JSONObject params){
        String id = params.getString("id");
        return giantTagService.delete(id);
    }



    /**
     *  绑定TAG
     */
    private void bindTag(JSONArray tagsAry,String gid){
        System.out.println(JSON.toJSONString(tagsAry));

        //tags
        if (tagsAry != null){
            giantTagMapper.deleteByGId(gid);

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
                giantTag.setGid(gid);
                giantTag.setTid(tid);
                giantTagMapper.add(giantTag);
            }
        }
    }

    /**
     *  绑定Nations
     */
    private void bindNations(JSONArray nationAry,String gid){
        //tags
        if (nationAry != null){
            giantNationMapper.deleteByGid(gid);

            for (int x = 0 ; x < nationAry.size();x++){
                JSONObject nationObj = nationAry.getJSONObject(x);
                Boolean isnew = nationObj.getBoolean("isnew");
                String nid = nationObj.getString("nid");

                if (isnew != null && isnew){
                    Nation nation = new Nation();
                    nid = Utils.generateId();
                    nation.setId(nid);
                    nation.setTitle(nationObj.getString("title"));
                    nationMapper.add(nation);
                }

                //绑定
                GiantNation giantNation = new GiantNation();
                giantNation.setId(Utils.generateId());
                giantNation.setGid(gid);
                giantNation.setNid(nid);
                giantNationMapper.add(giantNation);
            }
        }
    }

    /**
     *  初始化关系
     */
    @Transactional
    public Object editRelationTag(JSONObject params){
        String id = params.getString("id");
        Giant giant = giantMapper.selectById(id);
        if (giant == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject obj = (JSONObject) JSON.toJSON(giant);

        List<HashMap> giantRelations = giantRelationMapper.selectByFId(id);
        for (HashMap giantRelation : giantRelations){
            giantRelation.put("edit",false);
        }

        obj.put("giantrelations",giantRelations);
        obj.put("relations",relationMapper.selectAll());
        return new Result(ResultMessage.OK,obj);
    }

    @Transactional
    public Object saveGiantRelation(JSONObject params){
        System.out.println(params.toJSONString());

        String fid = params.getString("fid");
        JSONObject tgiant = params.getJSONObject("tgiant");
        String relation = params.getString("relation");
        String rid = relation;

        if (relation.length() != 18){
            //增加relation
            Relation r = new Relation();
            rid = Utils.generateId();
            r.setId(rid);
            r.setTitle(relation);
            relationService.add(r);
        }

        Boolean b = tgiant.getBoolean("isnew");
        String tid = tgiant.getString("gid");
        if (b != null && b){
            //增加giant
            Giant tg = new Giant();
            tid = Utils.generateId();
            tg.setId(tid);
            tg.setName(tgiant.getString("name"));
            add(tg);
        }


        GiantRelation giantRelation = new GiantRelation();
        giantRelation.setId(Utils.generateId());
        giantRelation.setFid(fid);
        giantRelation.setTid(tid);
        giantRelation.setRid(rid);
        return giantRelationService.add(giantRelation);
    }

    @Transactional
    public Object deleteGiantRelation(JSONObject params){
        return giantRelationService.deleteById(params.getString("id"));
    }

    @Transactional
    public Object saveGiantRelationDetail(JSONObject params){
        String id = params.getString("id");
        String detail = params.getString("detail");

        return giantRelationService.updateDetail(id,detail);
    }
}
