package com.example.administrator.childreneduction.da.component;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.childreneduction.da.module.ActivityModule;
import com.example.administrator.childreneduction.da.scope.ContextLife;
import com.example.administrator.childreneduction.da.scope.PerActivity;
import com.example.administrator.childreneduction.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

@PerActivity
@Component(dependencies = APPComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(MainActivity mActivity);
}
