package com.example.administrator.childreneduction.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;

/**
 * Created by Administrator on 2017/6/12.
 * 购物页面
 */

public class JDShopActivity extends EduBaseActivity {
    private WebView mWbActShop;

    private WebSettings mWebSettings;

    public static Intent createIntent(Context mContext){
        return new Intent(mContext,JDShopActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_jdshop;
    }

    @Override
    public void initView() {
        mWbActShop = (WebView) findViewById(R.id.wb_act_shop);
        initData();
        setListener();
    }

    private void initData() {
        mWebSettings = mWbActShop.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setAppCacheEnabled(false);
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        mWebSettings.setAllowFileAccess(true); //设置可以访问文件
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        mWbActShop.loadUrl(Content.URL_PATH);
        mWbActShop.setWebViewClient(new MyWebviewClient());
    }

    /**
     * 重写MyWebviewClient方法
     *
     * shouldOverrideUrlLoading（） 拦截网页跳转，是之继续在WebView中进行跳转
     * onPageStarted（） 开始加载的时候（显示进度条）
     * onPageFinished（） 夹在结束的时候（隐藏进度条）
     */
    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWbActShop.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }

    private void setListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK && mWbActShop.canGoBack()){
            mWbActShop.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
