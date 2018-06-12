package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 * 时期
 */
public class Period implements Serializable {

    private static final long serialVersionUID = 5036166082138215739L;
    private String id;
    private String title;
    private String content;
    private String cover;
    private Date cdate; //开启的时间
    private Date edate; //结束的时间
    private String ddate; //显示的日期
    private int AD = 1; // 开启是否在公元
    private int eAD = 1; // 结束是否在公元

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

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public int getAD() {
        return AD;
    }

    public void setAD(int AD) {
        this.AD = AD;
    }

    public int geteAD() {
        return eAD;
    }

    public void seteAD(int eAD) {
        this.eAD = eAD;
    }
}
