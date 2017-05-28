package com.example.administrator.childreneduction.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28.
 * 用户标签页
 */

public class LabelBean implements Serializable {

    private String name;
    private boolean check=false;

    public LabelBean(){

    }

    public LabelBean(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
