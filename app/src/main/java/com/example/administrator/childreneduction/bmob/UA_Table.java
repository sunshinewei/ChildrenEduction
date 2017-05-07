package com.example.administrator.childreneduction.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/7.
 */

public class UA_Table extends BmobObject {

    private String u_id;
    private String a_id;
    private String ua_comm;
    private String ua_coll;
    private String ua_laud;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getUa_comm() {
        return ua_comm;
    }

    public void setUa_comm(String ua_comm) {
        this.ua_comm = ua_comm;
    }

    public String getUa_coll() {
        return ua_coll;
    }

    public void setUa_coll(String ua_coll) {
        this.ua_coll = ua_coll;
    }

    public String getUa_laud() {
        return ua_laud;
    }

    public void setUa_laud(String ua_laud) {
        this.ua_laud = ua_laud;
    }
}
