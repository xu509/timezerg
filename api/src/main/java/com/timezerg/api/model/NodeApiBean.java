package com.timezerg.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xnx on 2018/6/1.
 */
public class NodeApiBean implements Serializable{

    private String cid;
    private String cname;
    private List<NodeDetailApiBean> beans;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<NodeDetailApiBean> getBeans() {
        return beans;
    }

    public void setBeans(List<NodeDetailApiBean> beans) {
        this.beans = beans;
    }
}
