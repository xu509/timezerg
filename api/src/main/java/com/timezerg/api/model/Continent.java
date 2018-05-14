package com.timezerg.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 * 大洲
 */
public class Continent implements Serializable {

    private static final long serialVersionUID = 4216190967327281592L;
    private String id;
    private String title;

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

}
