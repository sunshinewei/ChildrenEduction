package com.example.administrator.childreneduction.ui.home.activity;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.da.component.APPComponent;
import com.example.administrator.childreneduction.da.component.DaggerActivityComponent;
import com.example.administrator.childreneduction.da.module.ActivityModule;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;

public class HomeActivity extends EduBaseActivity {



    @Override
    public int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initActivityComponent() {
        mActivityComponent= DaggerActivityComponent.builder()
                .aPPComponent((APPComponent) getApplication())
                .activityModule(new ActivityModule(this))
                .build();
        mActivityComponent.inject(this);
    }
}
