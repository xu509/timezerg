package com.timezerg.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.mapper.*;
import com.timezerg.api.model.*;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import com.timezerg.api.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 * 文献服务类
 */
@Service
public class ReferenceService {

    @Autowired
    ReferenceMapper referenceMapper;

    @Autowired
    GiantReferenceMapper giantReferenceMapper;

    @Autowired
    GiantReferenceService giantReferenceService;

    @Autowired
    NodeReferenceMapper nodeReferenceMapper;

    @Autowired
    PeriodReferenceService periodReferenceService;

    @Autowired
    PeriodReferenceMapper periodReferenceMapper;

    @Autowired
    NodeReferenceService nodeReferenceService;

    @Autowired
    NationReferenceMapper nationReferenceMapper;

    @Autowired
    NationReferenceService nationReferenceService;

    @Autowired
    GiantService giantService;

    @Autowired
    NodeService nodeService;

    @Autowired
    PeriodService periodService;

    @Autowired
    NationService nationService;

    @Autowired
    ReferenceTagMapper referenceTagMapper;

    @Autowired
    ReferenceTagService referenceTagService;

    @Autowired
    TagService tagService;


    @Transactional
    public Object add(Reference reference){
        if (reference == null || StringUtils.isBlank(reference.getTitle())){
            return new Result(ResultMessage.PARAM_ERROR,ReferenceService.class);
        }
        if (referenceMapper.selectByTitle(reference.getTitle()) != null){
            return new Result(ResultMessage.DUPLICATION_ERROR,ReferenceService.class);
        }
        if (StringUtils.isBlank(reference.getId())){
            reference.setId(Utils.generateId());
        }
        referenceMapper.add(reference);

        return new Result(ResultMessage.OK,reference);
    }

    @Transactional
    public Object update(Reference reference){
        if (reference == null)
            return new Result(ResultMessage.PARAM_ERROR,ReferenceService.class);

        Reference o = referenceMapper.selectById(reference.getId());
        if (o == null)
            return new Result(ResultMessage.PARAM_ERROR,Reference.class);

        if (!o.getTitle().equals(reference.getTitle())){
            Reference d = referenceMapper.selectByTitle(reference.getTitle());
            if (d != null)
                return new Result(ResultMessage.DUPLICATION_ERROR,ReferenceService.class);
        }

        referenceMapper.update(reference);
        return new Result(ResultMessage.OK,reference);
    }



    @Transactional
    public Object add(JSONObject params) {
        Reference reference = new Reference();
        String rid = Utils.generateId();
        reference.setId(rid);
        reference.setTitle(params.getString("title"));
        reference.setContent(params.getString("content"));
        reference.setType(params.getInteger("type"));

        JSONObject prefobj = params.getJSONObject("preference");
        if (prefobj != null){
            String pid  = prefobj.getString("rid");
            Boolean isnew = prefobj.getBoolean("isnew");
            if (isnew){
                pid = Utils.generateId();
                Reference preference = new Reference();
                preference.setId(pid);
                preference.setTitle(prefobj.getString("title"));
                Result r2 = (Result) add(preference);
                if (!Result.isOk(r2)){
                    return new Result(ResultMessage.INNER_ERROR,"add");
                }
            }
            reference.setPid(pid);
        }
        return add(reference);
    }


    public Object getList(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("searchtitle");
        Integer start = params.getInteger("start");
        Integer size = params.getInteger("size");

        //默认
        List<HashMap> list = referenceMapper.getList(title, start, size);
        r.put("data", list);
        r.put("total", referenceMapper.getListTotal(title));

        return new Result(ResultMessage.OK, r);
    }


    public Object selectByTitle(JSONObject params) {
        JSONObject r = new JSONObject();

        String title = params.getString("sw");
        List<Reference> references = referenceMapper.selectLikeByTitle(title);

        JSONArray referenceAry = new JSONArray();
        for (Reference reference : references) {
            JSONObject referenceObj = (JSONObject) JSON.toJSON(reference);
            referenceObj.put("rid", reference.getId());
            referenceAry.add(referenceObj);
        }

        r.put("exist", references.size() > 0);
        r.put("data", referenceAry);

        return new Result(ResultMessage.OK, r);
    }

    public Object editInit(JSONObject params) {
        String id = params.getString("id");
        Reference reference = referenceMapper.selectById(id);
        if (reference == null)
            return new Result(ResultMessage.PARAM_ERROR);

        JSONObject r = (JSONObject) JSONObject.toJSON(reference);

        Reference pr = referenceMapper.selectById(reference.getPid());
        if (pr != null)
            r.put("preference",pr);

        return new Result(ResultMessage.OK, r);
    }

    @Transactional
    public Object editSaveBasic(JSONObject params){
        Reference reference = new Reference();
        reference.setId(params.getString("id"));
        reference.setContent(params.getString("content"));
        reference.setType(params.getInteger("type"));
        reference.setTitle(params.getString("title"));


        JSONObject prefobj = params.getJSONObject("preference");
        if (prefobj != null){
            String pid  = prefobj.getString("rid");
            Boolean isnew = prefobj.getBoolean("isnew");
            if (isnew !=null && isnew){
                pid = Utils.generateId();
                Reference preference = new Reference();
                preference.setId(pid);
                preference.setTitle(prefobj.getString("title"));
                Result r2 = (Result) add(preference);
                if (!Result.isOk(r2)){
                    return new Result(ResultMessage.INNER_ERROR,"edit save");
                }
            }
            reference.setPid(pid);
        }

        return update(reference);
    }


    // 初始化相关
    public Object editInitRelate(JSONObject params) {
        JSONObject data = new JSONObject();

        String id = params.getString("id");
        Reference reference = referenceMapper.selectById(id);
        if (reference == null)
            return new Result(ResultMessage.PARAM_ERROR);

        data.put("reference", reference);

        //人物、节点、时代、国家
        data.put("giants", giantReferenceMapper.selectByRid(id));
        data.put("nodes", nodeReferenceMapper.selectByRid(id));
        data.put("periods", periodReferenceMapper.selectByRid(id));
        data.put("nations", nationReferenceMapper.selectByRid(id));

        return new Result(ResultMessage.OK, data);
    }

    // 添加
    @Transactional
    public Object editAddRelateGiant(JSONObject params) {
        String rid = params.getString("rid");
        JSONObject giantObj = params.getJSONObject("giant");

        Boolean isnew = giantObj.getBoolean("isnew");
        String gid = giantObj.getString("gid");

        if (isnew != null && isnew){
            gid = Utils.generateId();
            Giant giant = new Giant();
            giant.setId(gid);
            giant.setName(giantObj.getString("name"));
            Result r2 = (Result) giantService.add(giant);
            if (!Result.isOk(r2)){
                return new Result(ResultMessage.INNER_ERROR,getClass());
            }
        }

        GiantReference giantReference = new GiantReference();
        giantReference.setGid(gid);
        giantReference.setRid(rid);
        return giantReferenceService.add(giantReference);
    }

    // 删除
    @Transactional
    public Object editDeleteRelateGiant(JSONObject params) {
        String id = params.getString("id");
        return giantReferenceService.delete(id);
    }

    // 添加
    @Transactional
    public Object editAddRelateNode(JSONObject params) {
        String rid = params.getString("rid");
        JSONObject nodeObj = params.getJSONObject("node");

        Boolean isnew = nodeObj.getBoolean("isnew");
        String nid = nodeObj.getString("nid");

        if (isnew != null && isnew){
            nid = Utils.generateId();
            Node node = new Node();
            node.setId(nid);
            node.setTitle(nodeObj.getString("title"));
            Result r2 = (Result) nodeService.add(node);
            if (!Result.isOk(r2)){
                return new Result(ResultMessage.INNER_ERROR,getClass());
            }
        }

        NodeReference nodeReference = new NodeReference();
        nodeReference.setNid(nid);
        nodeReference.setRid(rid);
        return nodeReferenceService.add(nodeReference);
    }

    // 删除
    @Transactional
    public Object editDeleteRelateNode(JSONObject params) {
        String id = params.getString("id");
        return nodeReferenceService.delete(id);
    }

    // 添加
    @Transactional
    public Object editAddRelatePeriod(JSONObject params) {
        String rid = params.getString("rid");
        JSONObject periodObj = params.getJSONObject("period");

        Boolean isnew = periodObj.getBoolean("isnew");
        String pid = periodObj.getString("pid");

        if (isnew != null && isnew){
            pid = Utils.generateId();
            Period period = new Period();
            period.setId(pid);
            period.setTitle(periodObj.getString("title"));
            Result r2 = (Result) periodService.add(period);
            if (!Result.isOk(r2)){
                return new Result(ResultMessage.INNER_ERROR,getClass());
            }
        }

        PeriodReference periodReference = new PeriodReference();
        periodReference.setPid(pid);
        periodReference.setRid(rid);
        return periodReferenceService.add(periodReference);
    }

    // 删除
    @Transactional
    public Object editDeleteRelatePeriod(JSONObject params) {
        String id = params.getString("id");
        return periodReferenceService.delete(id);
    }

    // 添加
    @Transactional
    public Object editAddRelateNation(JSONObject params) {
        String rid = params.getString("rid");
        JSONObject nationObj = params.getJSONObject("nation");

        Boolean isnew = nationObj.getBoolean("isnew");
        String nid = nationObj.getString("nid");

        if (isnew != null && isnew){
            nid = Utils.generateId();
            Nation nation = new Nation();
            nation.setId(nid);
            nation.setTitle(nationObj.getString("title"));
            Result r2 = (Result) nationService.add(nation);
            if (!Result.isOk(r2)){
                return new Result(ResultMessage.INNER_ERROR,getClass());
            }
        }

        NationReference nationReference = new NationReference();
        nationReference.setNid(nid);
        nationReference.setRid(rid);
        return nationReferenceService.add(nationReference);
    }

    // 删除
    @Transactional
    public Object editDeleteRelateNation(JSONObject params) {
        String id = params.getString("id");
        return nationReferenceService.delete(id);
    }

    // 初始化Tag
    public Object editInitTag(JSONObject params) {
        JSONObject data = new JSONObject();

        String id = params.getString("id");
        Reference reference = referenceMapper.selectById(id);
        if (reference == null)
            return new Result(ResultMessage.PARAM_ERROR);

        data.put("reference", reference);

        //ref
        data.put("tags", referenceTagMapper.selectTagsByRid(id));

        return new Result(ResultMessage.OK, data);
    }


    // 添加
    @Transactional
    public Object editAddTag(JSONObject params) {
        String rid = params.getString("rid");
        JSONObject tagObj = params.getJSONObject("tag");

        Boolean isnew = tagObj.getBoolean("isnew");
        String tid = tagObj.getString("tid");

        if (isnew != null && isnew){
            tid = Utils.generateId();
            Tag tag = new Tag();
            tag.setId(tid);
            tag.setTitle(tagObj.getString("title"));
            Result r2 = (Result) tagService.add(tag);
            if (!Result.isOk(r2)){
                return new Result(ResultMessage.INNER_ERROR,getClass());
            }
        }

        ReferenceTag tagReference = new ReferenceTag();
        tagReference.setTid(tid);
        tagReference.setRid(rid);
        return referenceTagService.add(tagReference);
    }

    // 删除
    @Transactional
    public Object editDeleteTag(JSONObject params) {
        String id = params.getString("id");
        return referenceTagService.delete(id);
    }

}
