package com.timezerg.api.model.api;

import com.timezerg.api.model.Node;
import com.timezerg.api.model.NodeDetailApiBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xnx on 2018/6/1.
 */
public class TimeApiBean implements Serializable,Comparable<TimeApiBean>{

    private String ddate;
    private Date cdate;
    private Integer AD;

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeApiBean that = (TimeApiBean) o;

        if (cdate != null ? !cdate.equals(that.cdate) : that.cdate != null) return false;
        return AD != null ? AD.equals(that.AD) : that.AD == null;

    }

    @Override
    public int hashCode() {
        int result = cdate != null ? cdate.hashCode() : 0;
        result = 31 * result + (AD != null ? AD.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(TimeApiBean bean) {
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
        return "[AD: " + AD +" ] " + cdate;

    }
}
