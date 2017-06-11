package com.example.administrator.childreneduction.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.childreneduction.App;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.utils.DataCleanManager;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/6/12.
 * 系统设置
 */

public class SettingActivity extends EduBaseActivity {
    private TextView mTvActivityArticle;
    private TextView mTvFragMeCache;
    private TextView mTvFragMeSize;
    private TextView mTvFragMeLogin;

    private Gson mGson;
    private SharePrefernceUtils mPrefernceUtils;
    private LoginInfo mLoginInfo;

    public static Intent createIntent(Context mContext){
        return new Intent(mContext,SettingActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        mTvActivityArticle = (TextView) findViewById(R.id.tv_activity_article);
        mTvFragMeCache = (TextView) findViewById(R.id.tv_frag_me_cache);
        mTvFragMeSize = (TextView) findViewById(R.id.tv_frag_me_size);
        mTvFragMeLogin = (TextView) findViewById(R.id.tv_frag_me_login);

        mPrefernceUtils=new SharePrefernceUtils(this, Content.SP_NAME);
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        mGson=new Gson();
        mLoginInfo=mGson.fromJson(string,LoginInfo.class);

        initData();
        setListener();
    }

    private void initData(){
        try {
            mTvFragMeSize.setText(DataCleanManager.getTotalCacheSize(App.getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置监听
     */
    private void setListener(){
        //清除缓存
        mTvFragMeCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTvFragMeSize.setText(DataCleanManager.getTotalCacheSize(App.getContext()));
                    DataCleanManager.clearAllCache(App.getContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //退出登录
        mTvFragMeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginInfo!=null){
                    mPrefernceUtils.clear();
                    Toast.makeText(SettingActivity.this,"用户已退出！",Toast.LENGTH_LONG);
                }
            }
        });
    }

}
