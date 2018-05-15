package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/4.
 */
public class CivilizationContinent implements Serializable {

    private static final long serialVersionUID = 2392388239241178550L;
    private String id;
    private String civilizationid;
    private String continentid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCivilizationid() {
        return civilizationid;
    }

    public void setCivilizationid(String civilizationid) {
        this.civilizationid = civilizationid;
    }

    public String getContinentid() {
        return continentid;
    }

    public void setContinentid(String continentid) {
        this.continentid = continentid;
    }
}
