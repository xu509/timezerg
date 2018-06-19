package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 * 名人
 */
public class Giant implements Serializable {

    private static final long serialVersionUID = -6176313969365538749L;
    private String id;
    private String pid; //父名人，（重名）
    private String name;
    private String content;
    private Date cdate; //开启的时间
    private Date edate; //结束的时间
    private int AD = 1; // 开启是否在公元
    private int eAD = 1; // 结束是否在公元

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
