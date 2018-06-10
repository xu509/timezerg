package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/4.
 * 名人 - tag
 */
public class GiantTag implements Serializable {


    private String id;
    private String gid;
    private String tid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
