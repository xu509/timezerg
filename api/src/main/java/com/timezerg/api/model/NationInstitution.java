package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/4.
 * 国家 - 制度
 */
public class NationInstitution implements Serializable {


    private String id;
    private String iid;
    private String Nid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
