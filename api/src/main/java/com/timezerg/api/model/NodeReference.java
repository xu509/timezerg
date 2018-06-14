package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/4.
 */
public class NodeReference implements Serializable {


    private static final long serialVersionUID = -8318464151962694920L;
    private String id;
    private String nid;
    private String rid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
