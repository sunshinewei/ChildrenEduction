package com.example.administrator.childreneduction.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

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
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
