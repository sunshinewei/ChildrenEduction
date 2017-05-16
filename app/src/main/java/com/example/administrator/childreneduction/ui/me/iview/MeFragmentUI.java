package com.example.administrator.childreneduction.ui.me.iview;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/14.
 */

public interface MeFragmentUI {

    /**
     * 登录成功
     */
    void login_ok(Map<String, String> map);

    /**
     * 登录失败
     */
    void login_fail();
}
