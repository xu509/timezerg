package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/4.
 */
public class NodeNation implements Serializable {


    private static final long serialVersionUID = -4139036438436765539L;

    private String id;
    private String nodeid;
    private String nationid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getNationid() {
        return nationid;
    }

    public void setNationid(String nationid) {
        this.nationid = nationid;
    }
}
