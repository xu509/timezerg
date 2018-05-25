package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 * 结点
 */
public class Node implements Serializable {

    private static final long serialVersionUID = 6921257177384426481L;

    private String id;
    private String title;
    private String content;
    private String cover;
    private Date cdate;
    private String ddate; //显示的日期
    private int AD = 1; // 是否在公元
    private Integer level = 0; //重要程度

    public static final Integer Level_Very_Important = 1;
    public static final Integer Level_Important = 2;
    public static final Integer Level_Normal = 3;
    public static final Integer Level_Detail = 4;
    public static final Integer Level_AD = 1;
    public static final Integer Level_BC = 0;

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

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getAD() {
        return AD;
    }

    public void setAD(int AD) {
        this.AD = AD;
    }
}
