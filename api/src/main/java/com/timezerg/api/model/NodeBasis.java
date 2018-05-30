package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/30
 * 节点依据
 * .
 */
public class NodeBasis implements Serializable{

    private static final long serialVersionUID = -7504954006383595547L;
    private String id;
    private String nid;
    private String title;
    private Integer type;
    private String link;

    public static final Integer Type_Wiki = 1;
    public static final Integer Type_Book = 2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }
}
