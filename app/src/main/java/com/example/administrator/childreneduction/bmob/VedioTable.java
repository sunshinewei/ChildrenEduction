package com.example.administrator.childreneduction.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/7.
 * 视频信息表
 */

public class VedioTable extends BmobObject implements Serializable{
    private String v_id;
    private String u_id;
    private String u_name;
    private String u_url;
    private String v_title;
    private String v_content;
    private String v_label;
    private String v_type;
    private String v_laud;
    private String v_url;
    private String v_coll;
    private String v_comm;

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

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    private String v_date;
    private String v_subscribe;

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getV_title() {
        return v_title;
    }

    public void setV_title(String v_title) {
        this.v_title = v_title;
    }

    public String getV_content() {
        return v_content;
    }

    public void setV_content(String v_content) {
        this.v_content = v_content;
    }

    public String getV_label() {
        return v_label;
    }

    public void setV_label(String v_label) {
        this.v_label = v_label;
    }

    public String getV_type() {
        return v_type;
    }

    public void setV_type(String v_type) {
        this.v_type = v_type;
    }

    public String getV_laud() {
        return v_laud;
    }

    public void setV_laud(String v_laud) {
        this.v_laud = v_laud;
    }

    public String getV_url() {
        return v_url;
    }

    public void setV_url(String v_url) {
        this.v_url = v_url;
    }

    public String getV_coll() {
        return v_coll;
    }

    public void setV_coll(String v_coll) {
        this.v_coll = v_coll;
    }

    public String getV_comm() {
        return v_comm;
    }

    public void setV_comm(String v_comm) {
        this.v_comm = v_comm;
    }

    public String getV_date() {
        return v_date;
    }

    public void setV_date(String v_date) {
        this.v_date = v_date;
    }

    public String getV_subscribe() {
        return v_subscribe;
    }

    public void setV_subscribe(String v_subscribe) {
        this.v_subscribe = v_subscribe;
    }
}
