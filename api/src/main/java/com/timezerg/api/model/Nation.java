package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 * 国家 - 时代
 */
public class Nation implements Serializable {

    private static final long serialVersionUID = 6921257177384426481L;

    private String id;
    private String pid; //国家可延续
    private String title;
    private String content;
    private String cover;
    private Date cdate; //开启的时间
    private Date edate; //结束的时间
    private String ddate; //显示的日期
    private boolean AD = true; // 是否在公元

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

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public boolean isAD() {
        return AD;
    }

    public void setAD(boolean AD) {
        this.AD = AD;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }
}
