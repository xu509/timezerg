package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/6/1.
 */
public class NodeDetailApiBean implements Serializable{

    private String cid;
    private String cname;
    private String nname;
    private String nid;

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

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Override
    public int hashCode() {
        int result = cid != null ? cid.hashCode() : 0;
        result = 31 * result + (nid != null ? nid.hashCode() : 0);
        return result;
    }
}
