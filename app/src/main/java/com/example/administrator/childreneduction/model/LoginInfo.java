package com.example.administrator.childreneduction.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/14.
 */

public class LoginInfo implements Serializable{

    private String id;
    private String name;
    private String url;
    private String type;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
