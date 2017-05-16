package com.example.administrator.childreneduction.ui.home.activity;

import android.webkit.WebView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;

/**
 * Created by Administrator on 2017/5/16.
 * 查看文章
 */

public class LookArticleActivity extends EduBaseActivity {
    private WebView mWbActivityWebviewShow;
    @Override
    public void initActivityComponent() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_lookarticle;
    }

    @Override
    public void initView() {
        mWbActivityWebviewShow = (WebView) findViewById(R.id.wb_activity_webview_show);
    }
}
