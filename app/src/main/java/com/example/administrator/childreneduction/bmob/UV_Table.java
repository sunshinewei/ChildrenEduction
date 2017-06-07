package com.example.administrator.childreneduction.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/7.
 * 用户视频表
 */

public class UV_Table extends BmobObject {
    private String u_id;
    private String v_id;
    private String u_url;
    private String vu_name;
    private String vu_title;
    private String uv_comm;
    private String uv_url;
    private String uv_coll;
    private String uv_laud;

    public String getU_url() {
        return u_url;
    }

    public void setU_url(String u_url) {
        this.u_url = u_url;
    }

    public String getVu_name() {
        return vu_name;
    }

    public void setVu_name(String vu_name) {
        this.vu_name = vu_name;
    }

    public String getVu_title() {
        return vu_title;
    }

    public void setVu_title(String vu_title) {
        this.vu_title = vu_title;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public String getUv_comm() {
        return uv_comm;
    }

    public void setUv_comm(String uv_comm) {
        this.uv_comm = uv_comm;
    }

    public String getUv_coll() {
        return uv_coll;
    }

    public void setUv_coll(String uv_coll) {
        this.uv_coll = uv_coll;
    }

    public String getUv_laud() {
        return uv_laud;
    }

    public void setUv_laud(String uv_laud) {
        this.uv_laud = uv_laud;
    }

    public String getUv_url() {
        return uv_url;
    }

    public void setUv_url(String uv_url) {
        this.uv_url = uv_url;
    }
}
