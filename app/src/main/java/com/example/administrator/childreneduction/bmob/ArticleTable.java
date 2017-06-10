package com.example.administrator.childreneduction.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/7.
 * 文章信息表
 */

public class ArticleTable extends BmobObject implements Serializable {
    private String a_id;
    private String u_id;
    private String u_name;
    private String u_url;
    private String a_title;
    private String a_content;
    private String a_url;
    private String a_type;
    private String a_label;
    private int coll;
    private int share;

    public String getU_url() {
        return u_url;
    }

    public void setU_url(String u_url) {
        this.u_url = u_url;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public int getColl() {
        return coll;
    }

    public void setColl(int coll) {
        this.coll = coll;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    private String a_date;
    private String a_subscribe;
    private String a_laud;  //点赞
    private String a_coll;
    private String a_comm;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getA_title() {
        return a_title;
    }

    public void setA_title(String a_title) {
        this.a_title = a_title;
    }

    public String getA_content() {
        return a_content;
    }

    public void setA_content(String a_content) {
        this.a_content = a_content;
    }

    public String getA_url() {
        return a_url;
    }

    public void setA_url(String a_url) {
        this.a_url = a_url;
    }

    public String getA_type() {
        return a_type;
    }

    public void setA_type(String a_type) {
        this.a_type = a_type;
    }

    public String getA_label() {
        return a_label;
    }

    public void setA_label(String a_label) {
        this.a_label = a_label;
    }

    public String getA_date() {
        return a_date;
    }

    public void setA_date(String a_date) {
        this.a_date = a_date;
    }

    public String getA_subscribe() {
        return a_subscribe;
    }

    public void setA_subscribe(String a_subscribe) {
        this.a_subscribe = a_subscribe;
    }

    public String getA_laud() {
        return a_laud;
    }

    public void setA_laud(String a_laud) {
        this.a_laud = a_laud;
    }

    public String getA_coll() {
        return a_coll;
    }

    public void setA_coll(String a_coll) {
        this.a_coll = a_coll;
    }

    public String getA_comm() {
        return a_comm;
    }

    public void setA_comm(String a_comm) {
        this.a_comm = a_comm;
    }
}
