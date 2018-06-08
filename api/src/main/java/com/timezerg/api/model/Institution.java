package com.timezerg.api.model;

import java.io.Serializable;

/**
 * Created by xnx on 2018/5/4.
 * 制度
 */
public class Institution implements Serializable {

    private static final long serialVersionUID = -7713228184990865111L;
    private String id;
    private String title;
    private String content;
    private Integer type = 0;

    public static final Integer Type_Economy = 1; //经济
    public static final Integer Type_Culture = 2; //文化
    public static final Integer Type_Political = 3; //政治
    public static final Integer Type_Society = 4; //社会

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
