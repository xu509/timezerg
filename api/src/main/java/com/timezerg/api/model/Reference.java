package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 * 参照
 */
public class Reference implements Serializable {


    private static final long serialVersionUID = 4381784129388557613L;
    private String id;
    private String pid;
    private String title;
    private String content;
    private Integer type = 0 ; //0 文献,1 文物

    public static final Integer Type_Literature = 0;
    public static final Integer Relic = 1;


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
