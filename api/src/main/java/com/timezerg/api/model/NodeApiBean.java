package com.timezerg.api.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by xnx on 2018/6/1.
 */
public class NodeApiBean implements Serializable,Comparable<NodeApiBean>{

    private Date cdate;
    private String ddate;
    private int AD;
    private HashSet<NodeDetailApiBean> detail = new HashSet<>();

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

    public int getAD() {
        return AD;
    }

    public void setAD(int AD) {
        this.AD = AD;
    }

    public HashSet<NodeDetailApiBean> getDetail() {
        return detail;
    }

    public void setDetail(HashSet<NodeDetailApiBean> detail) {
        this.detail = detail;
    }

    @Override
    public int hashCode() {
        int result = cdate != null ? cdate.hashCode() : 0;
        result = 31 * result + (ddate != null ? ddate.hashCode() : 0);
        result = 31 * result + AD;
        return result;
    }

    public void addDetail(NodeDetailApiBean nodeDetailApiBean){
        detail.add(nodeDetailApiBean);
    }


    @Override
    public int compareTo(NodeApiBean bean) {
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
            if (getAD() > bean.getAD()){
                return 1;
            }else {
                return -1;
            }
        }
    }
}
