package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/6/1.
 */
public class NodeDetailApiBean implements Serializable,Comparable<NodeDetailApiBean>{

    String nid;
    String ntitle;
    Date cdate;
    Integer AD;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Integer getAD() {
        return AD;
    }

    public void setAD(Integer AD) {
        this.AD = AD;
    }

    @Override
    public int compareTo(NodeDetailApiBean bean) {
        if (bean.getAD() == getAD()){
            if (bean.getAD() == Node.AD_VALUE){
                if (getCdate().getTime() < bean.getCdate().getTime()){
                    return -1;
                }else if (getCdate().getTime() == bean.getCdate().getTime()){
                    return 0;
                }else {
                    return 1;
                }
            }else {
                if (getCdate().getTime() > bean.getCdate().getTime()){
                    return -1;
                }else if (getCdate().getTime() == bean.getCdate().getTime()){
                    return 0;
                }else {
                    return 1;
                }
            }
        }else {
            if (getAD() < bean.getAD()){
                return -1;
            }else {
                return 1;
            }
        }
    }


    @Override
    public String toString() {
        return ntitle + " - [AD: " + AD +" ] " + cdate;
    }
}
