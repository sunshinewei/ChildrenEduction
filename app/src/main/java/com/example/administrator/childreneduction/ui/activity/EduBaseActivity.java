package com.example.administrator.childreneduction.ui.activity;

import android.os.Bundle;

import com.example.administrator.childreneduction.App;
import com.example.administrator.childreneduction.da.component.ActivityComponent;
import com.example.administrator.childreneduction.da.component.DaggerActivityComponent;
import com.example.administrator.childreneduction.da.module.ActivityModule;
import com.example.baselibrary.base.BaseActivity;


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
    public void initActivityComponent(){
        mActivityComponent= DaggerActivityComponent.builder()
                .aPPComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public ActivityComponent getActivityComponent(){
        return mActivityComponent;
    }

}
