package com.example.administrator.childreneduction.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/7.
 *用户表
 */

public class UserTable extends BmobObject{
    private String u_id;
    private String u_name;
    private String u_age;
    private String u_sex;
    private String u_address;
    private String u_type;
    private String u_intro;
    private String u_img;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_age() {
        return u_age;
    }

    public void setU_age(String u_age) {
        this.u_age = u_age;
    }

    public String getU_sex() {
        return u_sex;
    }

    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }

    public String getU_intro() {
        return u_intro;
    }

    public void setU_intro(String u_intro) {
        this.u_intro = u_intro;
    }

    public String getU_img() {
        return u_img;
    }

    public void setU_img(String u_img) {
        this.u_img = u_img;
    }
}
