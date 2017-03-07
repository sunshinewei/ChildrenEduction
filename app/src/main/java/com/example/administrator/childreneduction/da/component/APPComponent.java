package com.example.administrator.childreneduction.da.component;

import android.content.Context;

import com.example.administrator.childreneduction.da.module.APPModule;
import com.example.administrator.childreneduction.da.scope.ContextLife;
import com.example.administrator.childreneduction.da.scope.PerApp;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
@PerApp
@Component(modules = APPModule.class)
public interface APPComponent {
    @ContextLife("Application")
    Context getConext();
}
