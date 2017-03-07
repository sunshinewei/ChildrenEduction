package com.example.administrator.childreneduction.da.module;

import android.content.Context;

import com.example.administrator.childreneduction.App;
import com.example.administrator.childreneduction.da.scope.ContextLife;
import com.example.administrator.childreneduction.da.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/6 0006.
 */
@Module
public class APPModule {
    private App mApplication;

    public APPModule(App application) {
        this.mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideAPPContext() {
        return mApplication.getApplicationContext();
    }
}
