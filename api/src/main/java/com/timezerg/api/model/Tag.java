package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/4.
 * 结点
 */
public class Tag implements Serializable {

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
