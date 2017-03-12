package com.example.administrator.childreneduction.da.component;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.childreneduction.da.module.FragmentModule;
import com.example.administrator.childreneduction.da.scope.ContextLife;
import com.example.administrator.childreneduction.da.scope.PerFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
@PerFragment
@Component(dependencies = APPComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
        @ContextLife("Activity")
        Context getActivityContext();

        @ContextLife("Application")
        Context getApplicationContext();

        Activity getActivity();
}
