package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 * 文明
 */
public class Civilization implements Serializable {

    private static final long serialVersionUID = 6921257177384426481L;

    private String id;
    private String pid;
    private String title;
    private String content;
    private String cover;
    private Date cdate;
    private Integer sort = 0; //排序，正序，越小越前面

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


}
