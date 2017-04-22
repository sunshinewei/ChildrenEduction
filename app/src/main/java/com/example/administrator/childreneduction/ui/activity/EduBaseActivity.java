package com.example.administrator.childreneduction.ui.activity;

import android.os.Bundle;

import com.example.administrator.childreneduction.App;
import com.example.administrator.childreneduction.da.component.ActivityComponent;
import com.example.administrator.childreneduction.da.component.DaggerActivityComponent;
import com.example.administrator.childreneduction.da.module.ActivityModule;
import com.example.baselibrary.base.BaseActivity;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/3/7 0007.
 */

public abstract class EduBaseActivity extends BaseActivity {

    public ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
    }

    /**
     * 初始化dagger2中的componnent
     */
    public void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .aPPComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        mActivityComponent.inject(this);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    /**
     * 停止推送服务
     */
    public void stopJpush() {
        JPushInterface.stopPush(App.getAppComponent().getConext());
    }

    /**
     * 恢复极光推送
     */
    public void resumePush(){
        if (JPushInterface.isPushStopped(App.getAppComponent().getConext())){//判断当前极光推送是否工作
            JPushInterface.resumePush(App.getAppComponent().getConext());
        }
    }


}
